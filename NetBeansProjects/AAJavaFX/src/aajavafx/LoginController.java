/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import aajavafx.entities.Managers;
import aajavafx.entities.UserCredentials;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;

/**
 *
 * @author lokeshdhakal,Iuliu
 */
public class LoginController extends ControllerClass {

    @FXML
    private Label outputmessageID;

    @FXML
    private TextField userID;
    @FXML
    private PasswordField passwordID;
    
    @FXML
    private CheckBox saveCredentials;

    private final String USERNAME = "a";  // hard coded username to login
    private final String PASSWORD = "1"; //hard coded password to login
    
    Path savedCredentials = Paths.get("usr.conf");

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
            //String passwordFromDB = this.getPassword("1");
            
            if (saveCredentials.isSelected()) {

            //create serializable user credentials
            UserCredentials userCred = new UserCredentials(username_var, password_var);

            //save the file to the destination defined in variable savedCredentials
            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(savedCredentials))) {
                out.writeObject(userCred);
            } catch (IOException ex) {
                System.out.println("IO Exception: "+ex);
            } catch (Exception ex) {
                System.out.println("Exception: "+ex);;
            }
        }
          
        //    if (passwordFromDB.equals(password_var)) {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                System.out.println("Taking you to next page!");
          //  } else {
            //    outputmessageID.setText("Login failed!!!!");
          //  }
        } catch (Exception ex) {
            System.out.println("LOAD PAGE EXCEPTION: "+ex);
        }

        System.out.println("You clicked me!");

    }

    public static String getPassword(String userName) throws MalformedURLException, JSONException, IOException {
        String password = "";

        Managers manager = new Managers();
        Gson gson = new Gson();

      //JSONObject json = new JSONObject(IOUtils.toString(new URL("http://localhost:9090/MainServerREST/api/managers?manUsername=" + userName), Charset.forName("UTF-8")));
        JSONObject json = new JSONObject(IOUtils.toString(new URL("http://localhost:9090/MainServerREST/api/managers/" + userName), Charset.forName("UTF-8")));
        System.out.println(json.toString());
        manager = gson.fromJson(json.toString(), Managers.class);
        System.out.println(manager);

        password = manager.getManPassword();
        System.out.println(password);
        return password;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Disables checkbox if username and password are blank
        saveCredentials.disableProperty().bind(Bindings.isEmpty(userID.textProperty())
                .or(Bindings.isEmpty(passwordID.textProperty())
                ));
        if (Files.isReadable(savedCredentials)) {
            //reads file and autofills the textfields
            try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(savedCredentials))) {
                UserCredentials user = (UserCredentials) in.readObject();
                userID.setText(user.getUsername());
                passwordID.setText(user.getPassword());

            } catch (IOException ex) {
                System.out.println("IO Exception: "+ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
