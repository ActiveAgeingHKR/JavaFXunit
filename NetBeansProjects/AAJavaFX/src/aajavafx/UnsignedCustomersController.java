/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import entitiesproperty.CustomerProperty;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Iuliu
 */
public class UnsignedCustomersController implements Initializable {

    @FXML
    private TableView<CustomerProperty> tableCustomer;
    @FXML
    private TableColumn<CustomerProperty, Integer> cuIdColumn;
    @FXML
    private TableColumn<CustomerProperty, String> cuFirstNameColumn;
    @FXML
    private TableColumn<CustomerProperty, String> cuLastNameColumn;
    @FXML
    private TableColumn<CustomerProperty, String> cuPersonnumerColumn;
    Singleton singleton = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        singleton = Singleton.getInstance();
        cuIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        cuFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        cuLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        cuPersonnumerColumn.setCellValueFactory(cellData -> cellData.getValue().personnumerProperty());
        tableCustomer.setItems(singleton.getList());
    }

}
