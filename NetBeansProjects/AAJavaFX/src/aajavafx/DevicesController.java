/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import aajavafx.entities.Company;
import aajavafx.entities.Customers;
import aajavafx.entities.Visitors;
import com.google.gson.Gson;
import entitiesproperty.CustomerProperty;
import entitiesproperty.DevicesCustomerProperty;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author lokeshdhakal
 */
public class DevicesController implements Initializable {

   
    
    private static String DevicesCustomerRootURL = "http://localhost:8080/MainServerREST/api/devicescustomers/";
    
    @FXML
    private TableView<DevicesCustomerProperty> tableCustomer;
    @FXML
    private TableColumn<DevicesCustomerProperty, Integer> idCustomer;
    @FXML
    private TableColumn<DevicesCustomerProperty, String> firstNameColumn;
    @FXML
    private TableColumn<DevicesCustomerProperty, String> lastNameColumn;
    @FXML
    private TableColumn<DevicesCustomerProperty, String> addressColumn;
    @FXML
    private TableColumn<DevicesCustomerProperty, String> birthdateColumn;
    @FXML
    private TableColumn<DevicesCustomerProperty, String> persunnumerColumn;
    @FXML
    private TableColumn<DevicesCustomerProperty, String> devIdColumn;
    @FXML
    private TableColumn<DevicesCustomerProperty, String> devNameColumn;
    @FXML
    private ComboBox customerBox;
    @FXML
    private Button saveButton;
    @FXML 
    private Button removeButton;
    @FXML
    private TextField DevName;
    @FXML
    private TextField deviceID;
    
    Customers boundCustomer = new Customers();
    ArrayList<Customers> customers = new ArrayList<>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize columns
        idCustomer.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        birthdateColumn.setCellValueFactory(cellData -> cellData.getValue().birthdateProperty());
        persunnumerColumn.setCellValueFactory(cellData -> cellData.getValue().personnumerProperty());
        devIdColumn.setCellValueFactory(cellData -> cellData.getValue().cuDevIdProperty());
        devNameColumn.setCellValueFactory(cellData -> cellData.getValue().cuDevNameProperty());
        
        DevName.setEditable(false);
        deviceID.setEditable(false);
        customerBox.setEditable(false);
        customerBox.setDisable(true);
        
        try{
        //Populate table 
        tableCustomer.setItems(getDevicesCustomer());
        customerBox.setItems(getCustomer());
        customerBox.getItems().add("Add Customer");
    } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                DevName.setText(newSelection.getCuDevName());
                deviceID.setText(newSelection.getCuDevId());
                customerBox.setValue(newSelection.getCustomerId()+". "+newSelection.getFirstName() + " " + newSelection.lastNameProperty().getValue() + " " + newSelection.getPersonnumer());
                boundCustomer.setCuID(newSelection.customerIdProperty().getValue());
                boundCustomer.setCuFirstname(newSelection.getFirstName());
                boundCustomer.setCuLastname(newSelection.lastNameProperty().getValue());
                boundCustomer.setCuBirthdate(newSelection.getBirthdate());
                boundCustomer.setCuAdress(newSelection.getAdress());
                boundCustomer.setCuPersonnummer(newSelection.getPersonnumer());
            }
        });
    }
    
    @FXML
    private void handleSaveButton(ActionEvent event) {
        //labelError.setText(null);
        try {

            String devName = DevName.getText();
            DevName.clear();
            String devID = deviceID.getText();
            deviceID.clear();
            System.out.println(customerBox.getValue());
            String string = (String)customerBox.getValue();
            System.out.println("STRING VALUE: "+string);
            int customerId = Integer.parseInt(""+string.charAt(0));
            System.out.println("CUSTOMER ID VALUE:"+customerId);
            Customers customer = getCustomerByID(customerId);
            
            Gson gson = new Gson();
            
            //......for ssl handshake....
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
            provider.setCredentials(AuthScope.ANY, credentials);
            //........
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpEntityEnclosingRequestBase HttpEntity = null; //this is the superclass for post, put, get, etc
            if(deviceID.isEditable()) { //then we are posting a new record
                HttpEntity = new HttpPost(DevicesCustomerRootURL); //so make a http post object
            } else { //we are editing a record 
                HttpEntity = new HttpPut(DevicesCustomerRootURL+devID); //so make a http put object
            }
            DevicesCustomers devCust = new DevicesCustomers(devID, devName, customer);
            
            String jsonString = new String(gson.toJson(devCust));
            System.out.println("json string: " + jsonString);
            StringEntity postString = new StringEntity(jsonString);
            
            HttpEntity.setEntity(postString);
            HttpEntity.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(HttpEntity);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 204) {
                System.out.println("Device posted successfully");
            } else{
                System.out.println("Server error: "+response.getStatusLine());
            }
            DevName.setEditable(false);
            deviceID.setEditable(false);
            customerBox.setDisable(true);
            

        } catch (Exception ex) {
            System.out.println("Error: "+ex);
        }
        try {
            //refresh table
            tableCustomer.setItems(getDevicesCustomer());
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
            DevName.setEditable(true);
        customerBox.setDisable(false);
            
        } catch (Exception ex) {
        

            System.out.println("ERROR! "+ex);
        }
    }
    
     @FXML
    private void handleNewButton(ActionEvent event) {
        try {
            DevName.setEditable(true);
        deviceID.setEditable(true);
        customerBox.setDisable(false);
        DevName.clear();
            deviceID.clear();
            
        } catch (Exception ex) {
        

            System.out.println("ERROR! "+ex);
        }
    }
    
     @FXML
    private void handleRemoveButton(ActionEvent event) {
        //remove is annotated with @DELETE on server so we use a HttpDelete object
        try {
            //......for ssl handshake....
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
            provider.setCredentials(AuthScope.ANY, credentials);
            //........
            HttpClient httpClient = HttpClientBuilder.create().build();
            String idToDelete = deviceID.getText();
            //add the id to the end of the URL so this will call the method at MainServerREST/api/visitors/id
            HttpDelete delete = new HttpDelete(DevicesCustomerRootURL+idToDelete);
            HttpResponse response = httpClient.execute(delete);
            System.out.println("response from server " + response.getStatusLine());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            //refresh table
            tableCustomer.setItems(getDevicesCustomer());
        } catch (IOException ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ObservableList<String> getCustomer() throws IOException, JSONException{

        ObservableList<String> customerStrings = FXCollections.observableArrayList();
        //customers.add(new CustomerProperty(1, "Johny", "Walker", "London", "1972-07-01", "7207012222"));
        Customers myCustomer = new Customers();
        //Managers manager = new Managers();
        Gson gson = new Gson();
        ObservableList<CustomerProperty> customersProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();
        
        // SSL update .......
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/customers");
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        
        //JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL("http://localhost:8080/MainServerREST/api/customers"), Charset.forName("UTF-8")));
        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        // ...........
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            myCustomer = gson.fromJson(jo.toString(), Customers.class);
            customers.add(myCustomer);
            System.out.println(myCustomer.getCuId());
            String s = myCustomer.getCuId()+". "+myCustomer.getCuFirstname() + " " + myCustomer.getCuLastname() + " " + myCustomer.getCuPersonnummer();
            customerStrings.add(s);
            customersProperty.add(new CustomerProperty(myCustomer.getCuId(), myCustomer.getCuFirstname(),
                    myCustomer.getCuLastname(), myCustomer.getCuBirthdate(), myCustomer.getCuAddress(),
                    myCustomer.getCuPersonnummer()));

        
    }
        return customerStrings;
    }
    
    public ObservableList<DevicesCustomerProperty> getDevicesCustomer() throws IOException, JSONException{

        ObservableList<DevicesCustomerProperty> devicesCustomers = FXCollections.observableArrayList();
        
        Gson gson = new Gson();
        JSONObject jo = new JSONObject();   
        // SSL update .......
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/devicescustomers");
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        
        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
         // ...........
        //JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(DevicesCustomerRootURL), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            JSONObject jObj = (JSONObject) jo.get("customersCuId");
            Customers customer = gson.fromJson(jObj.toString(), Customers.class);
            System.out.println("JSON OBJECT #"+i+" "+jo);
            String cuDevId = jo.getString("devId");
            String cuDevName = jo.getString("devName");        
            devicesCustomers.add(new DevicesCustomerProperty(customer.getCuId(),
                    customer.getCuFirstname(), customer.getCuLastname(), customer.getCuBirthdate(), customer.getCuAddress(),
                    customer.getCuPersonnummer(), cuDevId, cuDevName));

        
    }
        return devicesCustomers;
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
    
}
