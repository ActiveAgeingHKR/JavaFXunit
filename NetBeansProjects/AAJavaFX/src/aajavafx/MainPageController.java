/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aajavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lokeshdhakal
 */
public class MainPageController extends ControllerClass {

    @FXML
    private Label TitleID;
    @FXML
    private Label welcomemessageID;
    
    
    
    @FXML
    public void handleButtonEmployeeAction (ActionEvent event) {

        try {
            
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Employee.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            System.out.println("You clicked Employees!");
        } catch (Exception ex) {
        

        System.out.println("ERROR!");
        }
    }
    
    @FXML
    public void handleButtonCustomerAction (ActionEvent event) {

        try {
            
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Customers.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            System.out.println("You clicked Customers!");
        } catch (Exception ex) {
        

        System.out.println("ERROR!"+ex);
        }
    }
    
    @FXML
    public void handleButtonVisitorAction (ActionEvent event) {

        try {
            
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Visitors.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            System.out.println("You clicked Visitors!");
        } catch (Exception ex) {
        

        System.out.println("ERROR!");
        }
    }
    
    @FXML
    public void handleButtonScheduleAction (ActionEvent event) {

        try {
            
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Schedule1.fxml"));
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
    public void handleButtonSignOutAction (ActionEvent event) {

        try {
            
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            System.out.println("You clicked to Log Out!");
        } catch (Exception ex) {
        

        System.out.println("ERROR!");
        }
    }
    
     
    
}
