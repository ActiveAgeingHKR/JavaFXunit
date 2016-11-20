/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import aajavafx.entities.Customers;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * FXML Controller class
 *
 * @author lokeshdhakal
 */
public class DevicesController {

   
    
    private static String postDevicesCustomerURL = "http://localhost:9090/MainServerREST/api/DevicesCustomers";
    
    @FXML
    private TextField DevName;
    @FXML
    private TextField CuID;
    
    Customers customer = new Customers();
    
    @FXML
    private void handleAddButton(ActionEvent event) {
        //labelError.setText(null);

        try {
            //String customerNumber = customerID.getText();
            //customerID.clear();

            String devName = DevName.getText();
            DevName.clear();
            String cuID = CuID.getText();
            CuID.clear();
            
           
         

            try {
                Gson gson = new Gson();
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(postDevicesCustomerURL);

                DevicesCustomers devicesCustomers = new DevicesCustomers(1, devName, customer);

                String jsonString = new String(gson.toJson(devicesCustomers));
                System.out.println("json string: " + jsonString);
                StringEntity postString = new StringEntity(jsonString);

                post.setEntity(postString);
                post.setHeader("Content-type", "application/json");
                HttpResponse response = httpClient.execute(post);
                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                if (response != null) {
                    response.getEntity().writeTo(outstream);
                    byte[] responseBody = outstream.toByteArray();
                    String str = new String(responseBody, "UTF-8");
                    System.out.print(str);
                } else {
                    System.out.println("Success");
                }
            } catch (UnsupportedEncodingException ex) {
                System.out.println(ex);
            } catch (IOException e) {
                System.out.println(e);
            }

        } catch (Exception ex) {
            
        }

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

    
    
    
}
