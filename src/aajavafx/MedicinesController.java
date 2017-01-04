/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import aajavafx.entities.Customers;
import aajavafx.entities.Medicines;
import com.google.gson.Gson;
import entitiesproperty.CustTakesMedProperty;
import entitiesproperty.MedicinesProperty;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.panos.SSLConnection;

/**
 * FXML Controller class
 *
 * @author Suraj
 */
public class MedicinesController implements Initializable {

    private static String MedicineRootURL = "http://localhost:8080/MainServerREST/api/medicines/";

    @FXML
    private TableView<Medicines> tableMedicines;
    @FXML
    private TableColumn<Medicines, String> idColumn;
    @FXML
    private TableColumn<Medicines, String> nameColumn;
    @FXML
    private TableColumn<Medicines, String> volumeColumn;
    @FXML
    private TableColumn<Medicines, String> measurementColumn;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField volumeField;
    @FXML
    private TextField measurementField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().getmedId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedName()));
        volumeColumn.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().getVolume()));
        measurementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedMeasurementUnit()));
         
       
        idField.setEditable(false);
        nameField.setEditable(false);
        volumeField.setEditable(false);
        measurementField.setEditable(false);

        try {
            //Populate table 
            tableMedicines.setItems(getMedicines());

        } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MedicinesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableMedicines.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText("" + newSelection.getmedId());
                nameField.setText(newSelection.getMedName());
                volumeField.setText(newSelection.getVolume().toString());
                measurementField.setText(newSelection.getMedMeasurementUnit());
            }
        });
    }

    @FXML
    private void handleSaveButton(ActionEvent event) throws UnsupportedEncodingException {
        try {

            String medName = nameField.getText();
            nameField.clear();
            String medID = idField.getText();
            idField.clear();
            String volume = volumeField.getText();
            volumeField.clear();
            String medMeasurementUnit = measurementField.getText();
            measurementField.clear();
            //// System.out.println(customerBox.getValue());
            ////String string = (String)customerBox.getValue();
            ////System.out.println("STRING VALUE: "+string);
            ////int customerId = Integer.parseInt(""+string.charAt(0));
            ////System.out.println("CUSTOMER ID VALUE:"+customerId);
            ////Customers customer = getCustomerByID(customerId);

            Gson gson = new Gson();
            
            Medicines medicine = new Medicines(1, medName, volume, medMeasurementUnit);

            String jsonString = new String(gson.toJson(medicine));
            //StringEntity postString = new StringEntity(jsonString);
            String restFullServerAddress = "https://localhost:8181/MainServerREST/api/";
                SSLConnection sSLConnection = new SSLConnection(restFullServerAddress);
                String restfulService = "medicines";
                String statusCode;

            //......for ssl handshake....
            //CredentialsProvider provider = new BasicCredentialsProvider();
            //UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
            //provider.setCredentials(AuthScope.ANY, credentials);
            //........
            //HttpClient httpClient = HttpClientBuilder.create().build();
            //HttpEntityEnclosingRequestBase HttpEntity = null; //this is the superclass for post, put, get, etc
            if (idField.isEditable()) { //then we are posting a new record
                //HttpEntity = new HttpPost(MedicineRootURL); //so make a http post object
                statusCode = sSLConnection.doPost(restfulService, jsonString, 
                    SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.TEXT, 
                    SSLConnection.USER_MODE.ADMIN);
            } else { //we are editing a record 
                statusCode = sSLConnection.doPut(restfulService, jsonString, 
                    SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.TEXT, 
                    SSLConnection.USER_MODE.ADMIN);
                //HttpEntity = new HttpPut(MedicineRootURL + medID); //so make a http put object
            }
            //Medicines medicine = new Medicines(1, medName, volume, medMeasurementUnit);

            //String jsonString = new String(gson.toJson(medicine));
            System.out.println("json string: " + jsonString);
            StringEntity postString = new StringEntity(jsonString);
            
            if (Integer.parseInt(statusCode) == 204) {
                    System.out.println("Medicine added successfully");
                } else {
                    //System.out.println("Server error: "+response.getStatusLine());
                    System.out.println("Server error ");
                }
            //HttpEntity.setEntity(postString);
            //HttpEntity.setHeader("Content-type", "application/json");
            //HttpResponse response = httpClient.execute(HttpEntity);
            //int statusCode = response.getStatusLine().getStatusCode();
            //if (statusCode == 204) {
               //// System.out.println("Device posted successfully");
           // } else {
              //  System.out.println("Server error: " + response.getStatusLine());
            
            nameField.setEditable(false);
            idField.setEditable(false);
            volumeField.setEditable(false);
            measurementField.setEditable(false);

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        try {
            //refresh table
            tableMedicines.setItems(getMedicines());
        } catch (IOException ex) {
            Logger.getLogger(MedicinesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MedicinesController.class.getName()).log(Level.SEVERE, null, ex);
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

            System.out.println("ERROR! " + ex);
        }

    }

    @FXML
    private void handleNewButton(ActionEvent event) {
        idField.setEditable(true);
        nameField.setEditable(true);
        volumeField.setEditable(true);
        measurementField.setEditable(true);
        idField.clear();
        nameField.clear();
        volumeField.clear();
        measurementField.clear();

    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        nameField.setEditable(true);
        volumeField.setEditable(true);
        measurementField.setEditable(true);
    }

    public ObservableList<Medicines> getMedicines() throws IOException, JSONException, Exception {

        ObservableList<Medicines> medicines = FXCollections.observableArrayList();
        //Managers manager = new Managers();
        Medicines medicine = new Medicines();
        Gson gson = new Gson();
        JSONObject jo = new JSONObject();
        
         SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
        String response = sslc.doGet("medicines", "", SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.ADMIN);
        JSONArray jsonArray = new JSONArray(response);

        // SSL update .......
        //CredentialsProvider provider = new BasicCredentialsProvider();
        //UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        //provider.setCredentials(AuthScope.ANY, credentials);
        //HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        //HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/medicines");
        //HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        //JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        // ...........
        //JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(MedicineRootURL), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            //Medicines medicine = gson.fromJson(jo.toString(), Medicines.class);
            medicine = gson.fromJson(jo.toString(), Medicines.class);
            System.out.println("JSON OBJECT #" + i + " " + jo);
            medicines.add(medicine);
           
        }
        return medicines;
    }

}
