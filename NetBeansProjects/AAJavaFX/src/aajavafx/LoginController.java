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
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
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
    

    HashFunction hash = new HashFunction();

    @FXML
    private void handleButtonLoginAction(ActionEvent event) {

        try {

           username_var = userID.getText().toString();
            password_var = passwordID.getText().toString();
            Kripto myKripto = new Kripto();
            System.out.println(myKripto.encrypt(password_var));

            if (saveCredentials.isSelected()) {
                UserCredentials userCred = new UserCredentials(username_var, password_var);
                try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(savedCredentials))) {
                    out.writeObject(userCred);
                } catch (IOException ex) {
                    System.out.println("IO Exception: " + ex);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex);;
                }
            }
           
            
            //////Check password disabled 
            
           String passwordCypherFromDB = getPassword(username_var);
           String passwordFromDB = myKripto.decrypt(passwordCypherFromDB);
           if (passwordFromDB.equals(password_var)) {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageTab.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                System.out.println("Taking you to next page!");
           } else {
               outputmessageID.setText("Login failed!!!!");
            }
        } catch (Exception ex) {
            System.out.println("LOAD PAGE EXCEPTION: " + ex);
        }

        System.out.println("You clicked me!");

    }

    public static String getPassword(String userName) throws MalformedURLException, JSONException, IOException {
        String password = "";

        Managers manager = new Managers();
        Managers myManager = new Managers();
        Gson gson = new Gson();

        JSONObject jo = new JSONObject();
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/managers/username/" + userName);

        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            manager = gson.fromJson(jo.toString(), Managers.class);
            if (manager.getManUsername().equals(userName)) {
                password = manager.getManPassword();
            }

            System.out.println(password);

        }
        return password;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
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
                System.out.println("IO Exception: " + ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
