/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import aajavafx.entities.Customers;
import aajavafx.entities.VisitorSchedule;
import aajavafx.entities.VisitorSchedulePK;
import aajavafx.entities.Visitors;
import com.google.gson.Gson;
import entitiesproperty.CustomerProperty;
import entitiesproperty.VisitorScheduleProperty;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Rolandas
 */
public class VisitorScheduleController implements Initializable {
    
    @FXML
    private TextField schIdField, hashField, startHours, startMins, startSecs, endHours, endMins, endSecs;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox repeatingBox, customerBox, visitorBox;
    @FXML
    private TableView<VisitorScheduleProperty> tableVisitorSchedule;
    @FXML
    private TableColumn<VisitorScheduleProperty, Integer> schIdColumn;
    @FXML
    private TableColumn<VisitorScheduleProperty, Integer> custIdColumn;
    @FXML
    private TableColumn<VisitorScheduleProperty, String> visitorIdColumn;
    @FXML
    private TableColumn<VisitorScheduleProperty, String> dateColumn;
    @FXML
    private TableColumn<VisitorScheduleProperty, String> startTimeColumn;
    @FXML
    private TableColumn<VisitorScheduleProperty, String> endTimeColumn;
    @FXML
    private TableColumn<VisitorScheduleProperty, String> repeatingColumn;
    @FXML
    private TableColumn<VisitorScheduleProperty, String> hashColumn;
    
    private static String VisitorScheduleRootURL = "http://localhost:8080/MainServerREST/api/visitorschedule/";
    
    //this will store the list of customers for use in the combo box
    ArrayList<Customers> customers = new ArrayList<>();
    //this will store the list of visitors for use in the combo box
    ArrayList<Visitors> visitors = new ArrayList<>();
    ObservableList<String> custStrings = FXCollections.observableArrayList();
    ObservableList<String> visStrings = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //instead of invisible, make the text fields locked for editing at first
        schIdField.setEditable(false);
        startHours.setEditable(false);
        startMins.setEditable(false);
        startSecs.setEditable(false);
        endHours.setEditable(false);
        endHours.setEditable(false);
        endHours.setEditable(false);
        hashField.setEditable(false);
        customerBox.setDisable(true);
        visitorBox.setDisable(true);
        datePicker.setDisable(true);
        repeatingBox.setDisable(true);
        
        schIdColumn.setCellValueFactory(cellData -> cellData.getValue().visSchIdProperty().asObject());
        custIdColumn.setCellValueFactory(cellData -> cellData.getValue().customersCuIdProperty().asObject());
        visitorIdColumn.setCellValueFactory(cellData -> cellData.getValue().visitorsVisIdProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().visitStartDateProperty());
        startTimeColumn.setCellValueFactory(cellData -> cellData.getValue().visitStartTimeProperty());
        endTimeColumn.setCellValueFactory(cellData -> cellData.getValue().visitEndTimeProperty());
        repeatingColumn.setCellValueFactory(cellData -> cellData.getValue().visRepetitionCircleProperty());
        hashColumn.setCellValueFactory(cellData -> cellData.getValue().visitHashProperty());
        
        try{
            //Populate table
            
            tableVisitorSchedule.setItems(getVisitorSchedules());
            ObservableList<String> repetition = FXCollections.observableArrayList();
            repetition.add("NONE");
            repetition.add("DAILY");
            repetition.add("WEEKLY");
            repetition.add("MONTHLY");
            repeatingBox.setItems(repetition);
            customerBox.setItems(getCustomer());
            customerBox.getItems().add("Add customer");
            visitorBox.setItems(getVisitor());
            visitorBox.getItems().add("Add visitor");
        } catch (IOException ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableVisitorSchedule.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                schIdField.setText(""+newSelection.getVisSchId());
                String custId = newSelection.customersCuIdProperty().getValue()+".";
                customerBox.setValue(getCustomerStringFromID(custId));
                String visId = newSelection.getVisitorsVisId()+".";
                visitorBox.setValue(getVisitorStringFromID(visId));
                String[] parts = newSelection.getVisitStartDate().split("-");
                LocalDate ld = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                datePicker.setValue((ld));
                String[] startParts = newSelection.getVisitStartTime().split(":");
                startHours.setText(startParts[0]);
                startMins.setText(startParts[1]);
                startSecs.setText(startParts[2]);
                String[] endParts = newSelection.getVisitEndTime().split(":");
                endHours.setText(endParts[0]);
                endMins.setText(endParts[1]);
                endSecs.setText(endParts[2]);
                repeatingBox.setValue(newSelection.getVisRepetitionCircle());
                hashField.setText(newSelection.getVisitHash());
            }
        });
    }    
    
    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 879, 599);
            stage.setScene(scene);
            
            stage.setTitle("Main menu");
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    @FXML
    private void handleNewButton(ActionEvent event) {
        schIdField.setEditable(true);
        startHours.setEditable(true);
        startMins.setEditable(true);
        startSecs.setEditable(true);
        endHours.setEditable(true);
        endMins.setEditable(true);
        endSecs.setEditable(true);
        hashField.setEditable(true);
        customerBox.setDisable(false);
        visitorBox.setDisable(false);
        datePicker.setDisable(false);
        repeatingBox.setDisable(false);     
    }
    
    @FXML
    private void handleEditButton(ActionEvent event) {
        startHours.setEditable(true);
        startMins.setEditable(true);
        startSecs.setEditable(true);
        endHours.setEditable(true);
        endMins.setEditable(true);
        endSecs.setEditable(true);
        hashField.setEditable(true);
        customerBox.setDisable(false);
        visitorBox.setDisable(false);
        datePicker.setDisable(false);
        repeatingBox.setDisable(false);
    }
    
    @FXML
    private void handleSaveButton(ActionEvent event) {
        int schId = Integer.parseInt(schIdField.getText());
        String string = (String)customerBox.getValue();
        System.out.println("STRING VALUE: "+string);
        int custId = Integer.parseInt(""+string.charAt(0));
        String string2 = (String)visitorBox.getValue();
        System.out.println("STRING VALUE: "+string);
        String visitorId  = string2.split("\\.")[0]; 
        String date = datePicker.getValue().toString();
        String startTime = startHours.getText()+":"+startMins.getText()+":"+startSecs.getText();
        String endTime = endHours.getText()+":"+endMins.getText()+":"+endSecs.getText();
        String repeating = (String)repeatingBox.getValue();
        
        // Later on fix this with normal hash with unique visit data.
        String hash = hashField.getText();
        VisitorSchedulePK vsPK = new VisitorSchedulePK(schId, visitorId, custId);
        VisitorSchedule vs = new VisitorSchedule(vsPK, date, startTime, endTime, repeating, hash);
              
        try {
            Gson gson = new Gson();
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpEntityEnclosingRequestBase HttpEntity = null; //this is the superclass for post, put, get, etc
            if(schIdField.isEditable()) { //then we are posting a new record
                HttpEntity = new HttpPost(VisitorScheduleRootURL); //so make a http post object
            } else { //we are editing a record 
                HttpEntity = new HttpPut(VisitorScheduleRootURL); //so make a http put object
            }
            
            String jsonString = new String(gson.toJson(vs));
            System.out.println("json string: " + jsonString);
            StringEntity postString = new StringEntity(jsonString);
            
            HttpEntity.setEntity(postString);
            HttpEntity.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(HttpEntity);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 204) {
                System.out.println("New visitor posted successfully");
                // Later on generate qr code and send mail to visitor with the qr code.
            } else{
                System.out.println("Server error: "+response.getStatusLine());
            }
            schIdField.setEditable(false);
            customerBox.setDisable(true);
            visitorBox.setDisable(true);
            hashField.setEditable(false);
            datePicker.setDisable(true);
            startHours.setEditable(false);
            startMins.setEditable(false);
            startSecs.setEditable(false);
            endHours.setEditable(false);
            endMins.setEditable(false);
            endSecs.setEditable(false);
            repeatingBox.setDisable(true);
            schIdField.clear();
            customerBox.getSelectionModel().clearSelection();
            visitorBox.getSelectionModel().clearSelection();
            hashField.clear();
            startHours.clear();
            startMins.clear();
            startSecs.clear();
            endHours.clear();
            endMins.clear();
            endSecs.clear();
            repeatingBox.getSelectionModel().clearSelection();
            
            //tableVisitorSchedule.setItems(getVisitors());
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    @FXML
    private void handleRemoveButton(ActionEvent event) {
        //remove is annotated with @DELETE on server so we use a HttpDelete object
        
    }
    
     public ObservableList<VisitorScheduleProperty> getVisitorSchedules() throws IOException, JSONException {
        VisitorSchedule vs = new VisitorSchedule();
        VisitorSchedulePK VisitorSchedulePK = new VisitorSchedulePK();
        Gson gson = new Gson();
        ObservableList<VisitorScheduleProperty> vsProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(VisitorScheduleRootURL), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            vs = gson.fromJson(jo.toString(), VisitorSchedule.class);
            System.out.println("VIS ID: "+vs.getVisitorSchedulePK().getVisitorsVisId());
            vsProperty.add(new VisitorScheduleProperty(
                    vs.getVisitorSchedulePK().getVisSchId(),
                    vs.getVisitorSchedulePK().getVisitorsVisId(),
                    vs.getVisitorSchedulePK().getCustomersCuId(),
                    vs.getVisitStartDate(),
                    vs.getVisitStartTime(),
                    vs.getVisitEndTime(),
                    vs.getVisRepetitionCircle(),
                    vs.getVisitHash()));
        }
        return vsProperty;
    }
     
     public ObservableList<String> getCustomer() throws IOException, JSONException{
        ObservableList<String> customerStrings = FXCollections.observableArrayList();
        Customers myCustomer = new Customers();

        Gson gson = new Gson();
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL("http://localhost:8080/MainServerREST/api/customers"), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            myCustomer = gson.fromJson(jo.toString(), Customers.class);
            customers.add(myCustomer);
            System.out.println(myCustomer.getCuId());
            String s = myCustomer.getCuId()+". "+myCustomer.getCuFirstname() + " " + myCustomer.getCuLastname();
            customerStrings.add(s);
    }
        custStrings = customerStrings;
        return customerStrings;
    }
     
     public ObservableList<String> getVisitor() throws IOException, JSONException{
        ObservableList<String> visitorStrings = FXCollections.observableArrayList();
        Visitors visitor = new Visitors();

        Gson gson = new Gson();
        ObservableList<CustomerProperty> customersProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL("http://localhost:8080/MainServerREST/api/visitors"), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            visitor = gson.fromJson(jo.toString(), Visitors.class);
            visitors.add(visitor);
            System.out.println(visitor.getVisId());
            String s = visitor.getVisId()+". "+visitor.getVisFirstname() + " " + visitor.getVisLastname();
            visitorStrings.add(s);
    }
        visStrings = visitorStrings;
        return visitorStrings;
    }
     
    public String getCustomerStringFromID(String ID) {
        for(String s: custStrings) {
            System.out.println("customer string: "+s);
            System.out.println("id: "+ID);
            if(s.startsWith(ID)) {
                return s;
            }
        }
        return new String();
    } 
    
    public String getVisitorStringFromID(String ID) {
        for(String s : visStrings) {
            if(s.startsWith(ID)) {
                return s;
            }
        }
        return new String();
    }
}
