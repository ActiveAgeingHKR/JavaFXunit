/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package aajavafx;

import aajavafx.entities.Company;
import entitiesproperty.VisitorsProperty;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
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
import aajavafx.entities.Visitors;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
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
public class VisitorController implements Initializable {
    
    @FXML
    private Button buttonRegister;
    @FXML
    private TextField visitorIDField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField companyIDField;
    @FXML
    private TableView<VisitorsProperty> tableVisitors;
    @FXML
    private TableColumn<VisitorsProperty, Integer> visitorIDColumn;
    @FXML
    private TableColumn<VisitorsProperty, String> firstNameColumn;
    @FXML
    private TableColumn<VisitorsProperty, String> lastNameColumn;
    @FXML
    private TableColumn<VisitorsProperty, String> emailColumn;
    @FXML
    private TableColumn<VisitorsProperty, String> phoneColumn;
    @FXML
    private TableColumn<VisitorsProperty, Integer> companyIDColumn;
    
    private static String postVisitorsURL = "http://localhost:9090/MainServerREST/api/visitors/";
    Company company = new Company();
    Client client = Client.create();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        buttonRegister.setVisible(false);
        visitorIDField.setVisible(false);
        firstNameField.setVisible(false);
        lastNameField.setVisible(false);
        emailField.setVisible(false);
        phoneNumberField.setVisible(false);
        companyIDField.setVisible(false);
        
        visitorIDColumn.setCellValueFactory(cellData -> cellData.getValue().visIdProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().visFirstnameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().visLastnameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().visEmailProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().visPhoneProperty());
        companyIDColumn.setCellValueFactory(cellData -> cellData.getValue().companyCompIdProperty().asObject());
        
        try{
            //Populate table
            tableVisitors.setItems(getVisitors());
        } catch (IOException ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(VisitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleNewVisitor(ActionEvent event) {
        buttonRegister.setVisible(true);
        visitorIDField.setVisible(true);
        firstNameField.setVisible(true);
        lastNameField.setVisible(true);
        emailField.setVisible(true);
        phoneNumberField.setVisible(true);
        companyIDField.setVisible(true);
    }
    
    @FXML
    private void handleRemoveVisitor(ActionEvent event) {
        try {
            buttonRegister.setVisible(false);
            visitorIDField.setVisible(false);
            firstNameField.setVisible(false);
            lastNameField.setVisible(false);
            emailField.setVisible(false);
            phoneNumberField.setVisible(false);
            companyIDField.setVisible(false);
            
            int id = tableVisitors.getSelectionModel().getSelectedItem().getVisId();
            String idToDelete = id + "";
            WebResource webResource = client.resource("http://localhost:9090/MainServerREST/api/employees");
            Visitors myReturnedObject = webResource.path(idToDelete).delete(Visitors.class);
            System.out.println("you want to delete: " + id);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            //populate table
            tableVisitors.setItems(getVisitors());
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @FXML
    private void handleGoBack(ActionEvent event) {
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
    private void handleRegister(ActionEvent event) {
        
        //  String visitorId = visitorIDField.getText();
        //  visitorIDField.clear();
        String firstName = firstNameField.getText();
        firstNameField.clear();
        String lastName = lastNameField.getText();
        lastNameField.clear();
        String email = emailField.getText();
        emailField.clear();
        String phoneNumber = phoneNumberField.getText();
        phoneNumberField.clear();
        String companyId = companyIDField.getText();
        companyIDField.clear();
        
        try {
            Gson gson = new Gson();
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(postVisitorsURL);
            
            Visitors visitor = new Visitors(1, firstName, lastName, email, phoneNumber, company);
            
            String jsonString = new String(gson.toJson(visitor));
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
    }
    
    
    public ObservableList<VisitorsProperty> getVisitors() throws IOException, JSONException{
        Visitors myVisitors = new Visitors();
        Gson gson = new Gson();
        ObservableList<VisitorsProperty> visitorsProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL("http://localhost:9090/MainServerREST/api/visitors"), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            myVisitors = gson.fromJson(jo.toString(), Visitors.class);
            System.out.println(myVisitors.getVisId());
            visitorsProperty.add(new VisitorsProperty(myVisitors.getVisId(), myVisitors.getVisFirstname(),
                    myVisitors.getVisLastname(), myVisitors.getVisEmail(), myVisitors.getVisPhone(),
                    myVisitors.getCompanyCompId().getCompId()));
        }
        return visitorsProperty;
    }
    
}
