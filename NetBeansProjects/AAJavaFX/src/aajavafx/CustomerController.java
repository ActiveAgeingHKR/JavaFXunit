/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lokeshdhakal
 */
public class CustomerController implements Initializable {

    @FXML
    private TableView<Customer> tableCustomers = new TableView<Customer>();
    
    @FXML
    private TableView<Customer> tableCustomer;
    @FXML
    private TableColumn<Customer, Integer> idCustomer;
     @FXML
    private TableColumn<Customer, String> firstNameColumn;
    @FXML
    private TableColumn<Customer, String> lastNameColumn;
      @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> birthdateColumn;
      @FXML
    private TableColumn<Customer, String> persunnumerColumn;
   
 
    
    @FXML
    private TextField customerID;
    @FXML
    private TextField firstNameID;
    @FXML
    private TextField lastNameID;
    @FXML
    private TextField addressID;
    @FXML
    private TextField birthdateID;
    @FXML
    private TextField persunnumerID;
    @FXML
    private Button buttonRegister;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonRegister.setVisible(false);
        customerID.setVisible(false);
        firstNameID.setVisible(false);
        lastNameID.setVisible(false);
        addressID.setVisible(false);
        birthdateID.setVisible(false);
        persunnumerID.setVisible(false);
        
        //initialize columns
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        birthdateColumn.setCellValueFactory(cellData -> cellData.getValue().birthdateProperty());
        persunnumerColumn.setCellValueFactory(cellData -> cellData.getValue().personnumerProperty());
        
        idCustomer.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty().asObject());
        //Populate table 
        tableCustomer.setItems(getCustomer());
    }    
    @FXML
    private void handleGoBack(ActionEvent event) {
        //labelError.setText(null);
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
        buttonRegister.setVisible(true);
        customerID.setVisible(true);
        firstNameID.setVisible(true);
        lastNameID.setVisible(true);
        addressID.setVisible(true);
        birthdateID.setVisible(true);
        persunnumerID.setVisible(true);
        
    }
    
    @FXML
    private void handleRegisterButton(ActionEvent event) {
        //labelError.setText(null);
       
            try {
                String customerNumber = customerID.getText();
                customerID.clear();
            
                String lastName = lastNameID.getText();
                lastNameID.clear();
                String firstName = firstNameID.getText();
                firstNameID.clear();
                String address = addressID.getText();
                addressID.clear();
                
                String birthdate = birthdateID.getText();
                birthdateID.clear();
                String persunnumer = persunnumerID.getText();
                persunnumerID.clear();
                
                
              //  dbConnection.setNewId(initials);
               // int controller = dbConnection.setDataEmployee(lastName, firstName, adress, email, phone, initials, salary);
                
                   // tableEmployees.setItems(dbConnection.getDataEmployees());
                   
            } catch (Exception ex) {
               // labelError.setText("Salary or phone field does not have a integer!");
            }
        
    }

    @FXML
    private void handlePrintButton(ActionEvent event) {
      //  labelError.setText(null);
     
            buttonRegister.setVisible(false);
           
            customerID.setVisible(true);
            firstNameID.setVisible(true);
            lastNameID.setVisible(true);
            addressID.setVisible(true);
            birthdateID.setVisible(true);
            persunnumerID.setVisible(true);
        
    }

    @FXML
    private void handleChangeButton(ActionEvent event) { //I think we don't need this for customer
      //  labelError.setText(null);
     
            try {
                String lastName = lastNameID.getText();
                lastNameID.clear();
                String firstName = firstNameID.getText();
                firstNameID.clear();
                String adress = addressID.getText();
                addressID.clear();
                String birthdate = birthdateID.getText();
                birthdateID.clear();
                
               
            } catch (Exception ex) {
                System.out.println("Something went wrong");
               
            }
    
}
     
    public ObservableList<Customer> getCustomer() {
        
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        customers.add(new Customer(1, "Johny", "Walker", "London", "1972-07-01", "7207012222"));
        
        
        return customers;
}
}
