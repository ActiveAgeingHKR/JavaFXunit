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

    private final String USERNAME = "a";  // hard coded username to login
    private final String PASSWORD = "1"; //hard coded password to login

    private String username_var;
    private String password_var;
    private boolean matchedPassword = false;
    private boolean matchedUserName = false;
    private String generatedSecurePasswordHash = "";
    private String generatedSecuredUserNameHash = "";
    HashFunction hash = new HashFunction();

    @FXML
    private void handleButtonLoginAction(ActionEvent event) {
        //int a
        //int b
        // if(a==b)
        try {

            username_var = userID.getText().toString();
            password_var = passwordID.getText().toString();
            generatedSecuredUserNameHash = hash.generateStorngPasswordHash(username_var);
            generatedSecurePasswordHash = hash.generateStorngPasswordHash(password_var);
            System.out.println(generatedSecuredUserNameHash);
            System.out.println(generatedSecurePasswordHash);
            matchedUserName = hash.validatePassword(USERNAME, generatedSecuredUserNameHash);
            matchedPassword = hash.validatePassword(PASSWORD, generatedSecurePasswordHash);

            
            System.out.println(matchedUserName);
            System.out.println(matchedPassword);

            if (matchedUserName==true && matchedPassword==true) {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                System.out.println("Taking you to next page!");
            } else {
                outputmessageID.setText("Login failed!!!!");
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong!");
        }

        System.out.println("You clicked me!");

    }

}
