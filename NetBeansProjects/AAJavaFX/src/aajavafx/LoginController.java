/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author lokeshdhakal
 */
public class LoginController extends ControllerClass {
    
    @FXML
    private Label outputmessageID;
    
    @FXML
    private TextField userID;
    @FXML
    private PasswordField passwordID;
    
   private final String Username_Admin = "admin";  // hard coded username to login
   private final String Password_Admin = "admin123"; //hard coded password to login
   
   
    
   
    @FXML
    private void handleButtonLoginAction(ActionEvent event) {
        try {
            // An alternative to getting the Stage from a Node declared in the scene is
            // to ask the button pressed what scene it belongs to.
            //if (userID.equals(Username_Admin)){
              //  if(passwordID.equals(Password_Admin)) {
            //}
            //}
            Node node = (Node) event.getSource();       
            Stage stage = (Stage) node.getScene().getWindow(); 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));  
            Parent root = loader.load();

            Scene scene = new Scene(root);      
            stage.setScene(scene);              
            stage.show();                       

            System.out.println("Taking you to next page!");
        }
        
       
        
        
        catch (Exception ex) {
            System.out.println("Something went wrong!");
        }
      
       
    
    
        System.out.println("You clicked me!");
        outputmessageID.setText("Hello World!");
    }
    
   
    
}
