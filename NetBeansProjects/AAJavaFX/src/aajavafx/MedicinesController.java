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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getmedId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedName()));
        volumeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getVolume()));
        measurementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedMeasurementUnit()));
        
        idField.setEditable(false);
        nameField.setEditable(false);
        volumeField.setEditable(false);
        measurementField.setEditable(false);
        
        try{
        //Populate table 
        tableMedicines.setItems(getMedicines());
        
    } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableMedicines.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(""+newSelection.getmedId());
                nameField.setText(newSelection.getMedName());
                volumeField.setText(newSelection.getVolume().toString());
                measurementField.setText(newSelection.getMedMeasurementUnit());
            }
        });
    }    
    
    @FXML
    private void handleSaveButton(ActionEvent event) {
        
    }
    
    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
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
    
    public ObservableList<Medicines> getMedicines() throws IOException, JSONException{

        ObservableList<Medicines> medicines = FXCollections.observableArrayList();
        //Managers manager = new Managers();
        Gson gson = new Gson();
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(MedicineRootURL), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            Medicines medicine = gson.fromJson(jo.toString(), Medicines.class);
            System.out.println("JSON OBJECT #"+i+" "+jo);
            medicines.add(medicine);

        
    }
        return medicines;
    }
    
}
