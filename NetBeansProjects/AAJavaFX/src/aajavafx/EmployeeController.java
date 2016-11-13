/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

/**
 * FXML Controller class
 *
 * @author Iuliu
 */
import aajavafx.entities.Managers;
import aajavafx.entities.Employees;
import entitiesproperty.EmployeeProperty;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
 * @author Iuliu
 */
public class EmployeeController implements Initializable {

    @FXML
    private TableView<EmployeeProperty> tableEmployees;
    @FXML
    private TableColumn<EmployeeProperty, String> firstNameColumn;
    @FXML
    private TableColumn<EmployeeProperty, String> lastNameColumn;
    @FXML
    private TableColumn<EmployeeProperty, String> userNameColumn;
    @FXML
    private TableColumn<EmployeeProperty, String> passwordColumn;
    @FXML
    private TableColumn<EmployeeProperty, String> emailColumn;
    @FXML
    private TableColumn<EmployeeProperty, String> phoneColumn;
    @FXML
    private TableColumn<EmployeeProperty, Integer> idEmployee;
    @FXML
    private TableColumn<EmployeeProperty, Integer> idManager;

    @FXML
    private TableColumn<EmployeeProperty, Boolean> validateEmployee;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textFirstName;
    @FXML
    private TextField textUserName;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textPhone;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textActivate;

    @FXML
    private Button buttonRegister;

    private static String postEmployeesURL = "http://localhost:9090/MainServerREST/api/employees/";
  
    @FXML
    private Label labelError;
    Client client = Client.create();
    Managers manager = new Managers(1);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelError.setText(null);
        buttonRegister.setVisible(false);
      
        textLastName.setVisible(false);
        textFirstName.setVisible(false);
        textUserName.setVisible(false);
        textEmail.setVisible(false);
        textPhone.setVisible(false);
        textPassword.setVisible(false);
        textActivate.setVisible(false);
        //initialize columns
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        userNameColumn.setCellValueFactory(cellData -> cellData.getValue().empUserNameProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().empPasswordProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().empEmailProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().empPhoneProperty());
        idEmployee.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        idManager.setCellValueFactory(cellData -> cellData.getValue().managerIdProperty().asObject());
        validateEmployee.setCellValueFactory(cellData -> cellData.getValue().empValidationProperty().asObject());
        try {
            //populate table
            tableEmployees.setItems(getEmployee());
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        textLastName.setVisible(true);
        textFirstName.setVisible(true);
        textUserName.setVisible(true);
        textEmail.setVisible(true);
        textPhone.setVisible(true);
        textPassword.setVisible(true);
        textActivate.setVisible(true);
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Employees employee = new Employees();

        try {
            buttonRegister.setVisible(false);
            textLastName.setVisible(false);
            textFirstName.setVisible(false);
            textUserName.setVisible(false);
            textEmail.setVisible(false);
            textPhone.setVisible(false);
            textPassword.setVisible(false);
            textActivate.setVisible(false);
            int id = tableEmployees.getSelectionModel().getSelectedItem().getId();
            String idToDelete = id + "";
            WebResource webResource = client.resource("http://localhost:9090/MainServerREST/api/employees");
            Employees myReturnedObject = webResource.path(idToDelete).delete(Employees.class);
            System.out.println("you want to delete: " + id);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            //populate table
            tableEmployees.setItems(getEmployee());
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) {
        labelError.setText(null);

        String lastName = textLastName.getText();
        textLastName.clear();
        String firstName = textFirstName.getText();
        textFirstName.clear();
        String userName = textUserName.getText();
        textUserName.clear();
        String email = textEmail.getText();
        textEmail.clear();
        String phone = textPhone.getText();
        textPhone.clear();
        String password = textPassword.getText();
        textPassword.clear();
        Boolean register = false;
        String tempValidation = textActivate.getText();
        textActivate.clear();
        if (tempValidation.contentEquals("Y")) {
            register = true;
        } else {
            register = false;
        }

        try {
            Gson gson = new Gson();
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("http://localhost:9090/MainServerREST/api/employees");

            Employees employee = new Employees(1, firstName, lastName, userName, password, email, phone, manager, register);

            String jsonString = gson.toJson(employee);
            System.out.println("json string: " + jsonString);
            StringEntity postString = new StringEntity(jsonString);

            post.setEntity(postString);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(post);
            
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            //populate table
            tableEmployees.setItems(getEmployee());
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @FXML
    private void handleChangeValidation(ActionEvent event) throws UnsupportedEncodingException, IOException, JSONException {

        Gson gson = new Gson();
        int id = tableEmployees.getSelectionModel().getSelectedItem().getId();
        String idToChange = id + "";

        boolean tempValidation = tableEmployees.getSelectionModel().getSelectedItem().getEmpValidation();
        if (tempValidation == true) {
            labelError.setText("This employee is valid!!!");
        } else {
            try {
                JSONObject json1 = new JSONObject(IOUtils.toString(new URL(postEmployeesURL + idToChange), Charset.forName("UTF-8")));
                Employees employeeTemp = new Employees();
                System.out.println("Bou" + json1.toString());
                employeeTemp = gson.fromJson(json1.toString(), Employees.class);

                boolean register = true;

                //String jsonString = gson.toJson(employee);
                WebResource resource = client.resource(postEmployeesURL + idToChange);

                employeeTemp.setEmpRegistered(register);
                Employees response = resource.put(Employees.class, employeeTemp);
                tableEmployees.setItems(getEmployee());
            } catch (IOException ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
     public ObservableList<EmployeeProperty> getEmployee() throws IOException, JSONException {
        Employees myEmployee = new Employees();
        Managers manager = new Managers();
        Gson gson = new Gson();
        ObservableList<EmployeeProperty> employeesProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL("http://localhost:9090/MainServerREST/api/employees"), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            myEmployee = gson.fromJson(jo.toString(), Employees.class);
            System.out.println(myEmployee.getEmpPhone());
            employeesProperty.add(new EmployeeProperty(myEmployee.getEmpId(), myEmployee.getEmpLastname(),
                    myEmployee.getEmpFirstname(), myEmployee.getEmpUsername(), myEmployee.getEmpPassword(),
                    myEmployee.getEmpEmail(), myEmployee.getEmpPhone(), myEmployee.getManagersManId().getManId(), myEmployee.getEmpRegistered()));

        }
        return employeesProperty;
    }

}
