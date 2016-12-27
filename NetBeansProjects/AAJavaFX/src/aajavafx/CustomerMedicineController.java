/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import aajavafx.entities.Company;
import aajavafx.entities.Customers;
import aajavafx.entities.CustomersTakesMedicines;
import aajavafx.entities.CustomersTakesMedicinesPK;
import aajavafx.entities.Medicines;
import aajavafx.entities.Visitors;
import com.google.gson.Gson;
import entitiesproperty.CustTakesMedProperty;
import entitiesproperty.CustomerProperty;
import entitiesproperty.DevicesCustomerProperty;
import entitiesproperty.MedicinesProperty;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Suraj
 */
public class CustomerMedicineController implements Initializable {

   private static String MedicineRootURL = "http://localhost:8080/MainServerREST/api/medicines/";
   
   private static String CustomersRootURL = "http://localhost:8080/MainServerREST/api/customers/";
    
    private static String MedicineCustomerRootURL = "http://localhost:8080/MainServerREST/api/customersmedicines/";
    
    @FXML
    private TableView<CustomersTakesMedicines> tableCustomer;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> idCustomer;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> firstNameColumn;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> lastNameColumn;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> doseColumn;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> startColumn;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> schedColumn;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> medIdColumn;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> medNameColumn;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> volumeColumn;
    @FXML
    private TableColumn<CustomersTakesMedicines, String> medMeasurementUnitColumn;
    @FXML
    private ComboBox customerBox;
    @FXML
    private ComboBox medicinesBox;
    @FXML
    private TextField dose;
    @FXML
    private TextField startDate;
    @FXML
    private TextField schedule;
    
    
    
    Customers boundCustomer = new Customers();
    ArrayList<Customers> customers = new ArrayList<>();
    ObservableList<CustomersTakesMedicines> custTakeMedicines;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize columns
        idCustomer.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getCustomers().getCuId()));
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomers().getCuFirstname()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomers().getCuLastname()));
        doseColumn.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue().getMedDosage()));
        startColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedStartDate()));
        schedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getMedicationintakeschedule()));
        medIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getMedicines().getmedId()));
        medNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getMedicines().getMedName()));
        volumeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getMedicines().getVolume()));
        medMeasurementUnitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getMedicines().getMedMeasurementUnit()));
        
        dose.setEditable(false);
        startDate.setEditable(false);
        customerBox.setEditable(false);
        customerBox.setDisable(true);
        schedule.setEditable(false);
        
        try{
        //Populate table 
        custTakeMedicines = getCustomersTakesMedicines();
        tableCustomer.setItems(custTakeMedicines);
        customerBox.setItems(getCustomer());
        customerBox.getItems().add("Add Customer");
        medicinesBox.setItems(getMedicines());
        medicinesBox.getItems().add("Add Medicine");
    } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                dose.setText(newSelection.getMedDosage());
                startDate.setText(newSelection.getMedStartDate());
                schedule.setText(""+newSelection.getMedicationintakeschedule());
                customerBox.setValue(newSelection.getCustomers());
                medicinesBox.setValue(newSelection.getMedicines());
            }
        });
    }
    
    @FXML
    private void handleSaveButton(ActionEvent event) {
        //labelError.setText(null);
        try {

            String dosage = dose.getText();
            dose.clear();
            String stDate = startDate.getText();
            startDate.clear();
            String sched = schedule.getText();
            schedule.clear();
            Customers customer = (Customers) customerBox.getSelectionModel().getSelectedItem();
            Medicines medicine = (Medicines) medicinesBox.getSelectionModel().getSelectedItem();
            CustomersTakesMedicinesPK ctmPK = new CustomersTakesMedicinesPK(customer.getCuId(), medicine.getmedId());
            CustomersTakesMedicines ctm = new CustomersTakesMedicines(ctmPK, dosage, stDate, Double.parseDouble(sched));
            ctm.setCustomers(customer);
            ctm.setMedicines(medicine);
            //System.out.println(customerBox.getValue());
            //String string = (String)customerBox.getValue();
            //System.out.println("STRING VALUE: "+string);
            //int customerId = Integer.parseInt(""+string.charAt(0));
            //System.out.println("CUSTOMER ID VALUE:"+customerId);
            //Customers customer = getCustomerByID(customerId);
            
            Gson gson = new Gson();
            //......for ssl handshake....
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
            provider.setCredentials(AuthScope.ANY, credentials);
            //........
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpEntityEnclosingRequestBase HttpEntity = null; //this is the superclass for post, put, get, etc
            if(startDate.isEditable()) { //then we are posting a new record
                HttpEntity = new HttpPost(MedicineCustomerRootURL); //so make a http post object
            } else { //we are editing a record 
                HttpEntity = new HttpPut(MedicineCustomerRootURL+startDate); //so make a http put object
            }
            
            String jsonString = new String(gson.toJson(ctm));
            System.out.println("json string: " + jsonString);
            StringEntity postString = new StringEntity(jsonString);
            
            HttpEntity.setEntity(postString);
            HttpEntity.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(HttpEntity);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 204) {
                System.out.println("Customer binded to medicine successfully");
            } else{
                System.out.println("Server error: "+response.getStatusLine());
            }
            dose.setEditable(false);
            startDate.setEditable(false);
            schedule.setEditable(false);
            customerBox.setDisable(true);
            

        } catch (Exception ex) {
            System.out.println("Error: "+ex);
        }
        try {
            //refresh table
            tableCustomer.setItems(getCustomersTakesMedicines());
        } catch (IOException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    private void handleBackButton(ActionEvent event) {
 try {
            
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageTab.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            System.out.println("You clicked Schedule!");
        } catch (Exception ex) {
        

        System.out.println("ERROR! "+ex);
        }
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        try {
            dose.setEditable(true);
        customerBox.setDisable(false);
            
        } catch (Exception ex) {
        

            System.out.println("ERROR! "+ex);
        }
    }
    
     @FXML
    private void handleNewButton(ActionEvent event) {
        try {
            dose.setEditable(true);
        startDate.setEditable(true);
        schedule.setEditable(true);
        customerBox.setDisable(false);
        dose.clear();
            startDate.clear();
            
        } catch (Exception ex) {
        

            System.out.println("ERROR! "+ex);
        }
    }
    
     @FXML
    private void handleRemoveButton(ActionEvent event) {
        //remove is annotated with @DELETE on server so we use a HttpDelete object
        try {
            //.......SSL Update....
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
            provider.setCredentials(AuthScope.ANY, credentials);
            //......
            
            HttpClient httpClient = HttpClientBuilder.create().build();
            String idToDelete = startDate.getText();
            //add the id to the end of the URL so this will call the method at MainServerREST/api/visitors/id
            HttpDelete delete = new HttpDelete(MedicineCustomerRootURL+idToDelete);
            HttpResponse response = httpClient.execute(delete);
            System.out.println("response from server " + response.getStatusLine());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            //refresh table
            tableCustomer.setItems(getCustomersTakesMedicines());
        } catch (IOException ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ObservableList<Customers> getCustomer() throws IOException, JSONException{

        ObservableList<Customers> customers = FXCollections.observableArrayList();
        Customers myCustomer = new Customers();
        Gson gson = new Gson();    
        JSONObject jo = new JSONObject();
        // SSL update..........
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/customers");

        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        //................
        
        //JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(CustomersRootURL), Charset.forName("UTF-8")));
        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            myCustomer = gson.fromJson(jo.toString(), Customers.class);
            customers.add(myCustomer);
            
            
    }
        return customers;
    }
    
    public ObservableList<Medicines> getMedicines() throws IOException, JSONException{

        ObservableList<Medicines> medicines = FXCollections.observableArrayList();
        //Managers manager = new Managers();
        Gson gson = new Gson();
        JSONObject jo = new JSONObject();
        
        //........SSL Update
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/medicines");

        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        //...................
       // JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(MedicineRootURL), Charset.forName("UTF-8")));
       JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            Medicines medicine = gson.fromJson(jo.toString(), Medicines.class);
            System.out.println("JSON OBJECT #"+i+" "+jo);
            medicines.add(medicine);

        
    }
        return medicines;
    }
    
    public ObservableList<CustomersTakesMedicines> getCustomersTakesMedicines() throws IOException, JSONException{

        ObservableList<CustomersTakesMedicines> ctMeds = FXCollections.observableArrayList();
        //Managers manager = new Managers();
        Gson gson = new Gson();
        JSONObject jo = new JSONObject();
        //........SSL Update
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/customersmedicines/");

        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        //...................
       // JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(MedicineRootURL), Charset.forName("UTF-8")));
       JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        
        //JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(MedicineCustomerRootURL), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            //get the main json object, which contains customer object, pk object, dosage, start date, schedule and medicine object
            jo = (JSONObject) jsonArray.getJSONObject(i);
            System.out.println("JSON OBJECT #"+i+" "+jo);
            //get the customer json sub-string
            JSONObject custObj = (JSONObject) jo.get("customers");
            Customers customer = gson.fromJson(custObj.toString(), Customers.class);
            //get the primary key json sub-string
            JSONObject pkObj = (JSONObject) jo.get("customersTakesMedicinesPK");
            CustomersTakesMedicinesPK ctmPK = gson.fromJson(pkObj.toString(), CustomersTakesMedicinesPK.class);
            //get the medicine json sub-string
            JSONObject medObj = (JSONObject) jo.get("medicines");
            Medicines medicine = gson.fromJson(medObj.toString(), Medicines.class);
            //get the individual strings
            String dose = jo.getString("medDosage");
            String startDate = jo.getString("medStartDate"); 
            double schedule = jo.getDouble("medicationintakeschedule");
            CustomersTakesMedicines cuTaMe = new CustomersTakesMedicines(ctmPK, dose, startDate, schedule);
            cuTaMe.setCustomers(customer);
            cuTaMe.setMedicines(medicine);
            ctMeds.add(cuTaMe);
    }
        return ctMeds;
    }
    
    public Customers getCustomerByID(int CustomerID) {
        for(Customers c : customers) {
            System.out.println("customer id: "+c.getCuId());
            if(c.getCuId() == CustomerID) {
                return c;
            }
        }
        return new Customers();
    }
    
    public ObservableList<String> objectToStrings(ObservableList list) {
        ObservableList<String> strings = FXCollections.observableArrayList();
        for(Object obj : list) {
            strings.add(obj.toString());
        }
        return strings;
    }
    
}
