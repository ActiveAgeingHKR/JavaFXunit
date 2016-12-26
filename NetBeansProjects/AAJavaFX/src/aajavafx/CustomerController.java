///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package aajavafx;
//
//import aajavafx.entities.Customers;
//import aajavafx.entities.Employees;
//import aajavafx.entities.Managers;
//import entitiesproperty.CustomerProperty;
//import com.google.gson.Gson;
//import entitiesproperty.EmployeeProperty;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.Locale;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// * FXML Controller class
// *
// * @author lokeshdhakal
// */
//public class CustomerController implements Initializable {
//
//    private static String postCustomerURL = "http://localhost:8080/MainServerREST/api/customers";
//   // private static String postCustomerURL = "http://192.168.43.205:8080/MainServerREST/api/customers";
//
//    @FXML
//    private TableView<CustomerProperty> tableCustomers = new TableView<CustomerProperty>();
//
//    @FXML
//    private TableView<CustomerProperty> tableCustomer;
//    @FXML
//    private TableColumn<CustomerProperty, Integer> idCustomer;
//    @FXML
//    private TableColumn<CustomerProperty, String> firstNameColumn;
//    @FXML
//    private TableColumn<CustomerProperty, String> lastNameColumn;
//    @FXML
//    private TableColumn<CustomerProperty, String> addressColumn;
//    @FXML
//    private TableColumn<CustomerProperty, String> birthdateColumn;
//    @FXML
//    private TableColumn<CustomerProperty, String> persunnumerColumn;
//
//    @FXML
//    private TextField firstNameID;
//    @FXML
//    private TextField lastNameID;
//    @FXML
//    private TextField addressID;
//    @FXML
//    private TextField birthdateID;
//    @FXML
//    private TextField persunnumerID;
//    @FXML
//    private Button buttonRegister;
//
//    //in order to create customer object from "register" button
//    String lastName;
//    String firstName;
//    String address;
//    String birthdate;
//    String persunnumer;
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        buttonRegister.setVisible(false);
//        //customerID.setVisible(false);
//        firstNameID.setVisible(false);
//        lastNameID.setVisible(false);
//        addressID.setVisible(false);
//        birthdateID.setVisible(false);
//        persunnumerID.setVisible(false);
//
//        //initialize columns
//        idCustomer.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
//        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
//        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
//        birthdateColumn.setCellValueFactory(cellData -> cellData.getValue().birthdateProperty());
//        persunnumerColumn.setCellValueFactory(cellData -> cellData.getValue().personnumerProperty());
//
//       try{
//        //Populate table 
//        tableCustomer.setItems(getCustomer());
//    } catch (IOException ex) {
//            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JSONException ex) {
//            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @FXML
//    private void handleGoBack(ActionEvent event) {
//        //labelError.setText(null);
//        try {
//            Node node = (Node) event.getSource();
//            Stage stage = (Stage) node.getScene().getWindow();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
//            Parent root = loader.load();
//            Scene scene = new Scene(root, 879, 599);
//            stage.setScene(scene);
//
//            stage.setTitle("Main menu");
//            stage.show();
//
//        } catch (Exception ex) {
//            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @FXML
//    private void handleNewButton(ActionEvent event) {
//        buttonRegister.setVisible(true);
//        //customerID.setVisible(true);
//        firstNameID.setVisible(true);
//        lastNameID.setVisible(true);
//        addressID.setVisible(true);
//        birthdateID.setVisible(true);
//        persunnumerID.setVisible(true);
//
//    }
//
//    @FXML
//    private void handleRegisterButton(ActionEvent event) {
//        //labelError.setText(null);
//
//        try {
//            //String customerNumber = customerID.getText();
//            //customerID.clear();
//
//            String lastName = lastNameID.getText();
//            lastNameID.clear();
//            String firstName = firstNameID.getText();
//            firstNameID.clear();
//            String address = addressID.getText();
//            addressID.clear();
//
//            String birthdate = birthdateID.getText();
//           
//            birthdateID.clear();
//            String persunnumer = persunnumerID.getText();
//            persunnumerID.clear();
//
//            try {
//                Gson gson = new Gson();
//                HttpClient httpClient = HttpClientBuilder.create().build();
//                HttpPost post = new HttpPost(postCustomerURL);
//
//                Customers customer = new Customers(1, firstName, lastName, address, birthdate, persunnumer);
//
//                String jsonString = new String(gson.toJson(customer));
//                System.out.println("json string: " + jsonString);
//                StringEntity postString = new StringEntity(jsonString);
//
//                post.setEntity(postString);
//                post.setHeader("Content-type", "application/json");
//                HttpResponse response = httpClient.execute(post);
//                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
//                if (response != null) {
//                    response.getEntity().writeTo(outstream);
//                    byte[] responseBody = outstream.toByteArray();
//                    String str = new String(responseBody, "UTF-8");
//                    System.out.print(str);
//                } else {
//                    System.out.println("Success");
//                }
//            } catch (UnsupportedEncodingException ex) {
//                System.out.println(ex);
//            } catch (IOException e) {
//                System.out.println(e);
//            }
//
//        } catch (Exception ex) {
//            // labelError.setText("Salary or phone field does not have a integer!");
//        }
//
//    }
//
//    @FXML
//    private void handlePrintButton(ActionEvent event) {
//        //  labelError.setText(null);
//
//        buttonRegister.setVisible(false);
//
//        //customerID.setVisible(true);
//        firstNameID.setVisible(true);
//        lastNameID.setVisible(true);
//        addressID.setVisible(true);
//        birthdateID.setVisible(true);
//        persunnumerID.setVisible(true);
//
//    }
//
//    @FXML
//    private void handleChangeButton(ActionEvent event) { //I think we don't need this for customer
//        //  labelError.setText(null);
//
//        try {
//            String lastName = lastNameID.getText();
//            lastNameID.clear();
//            String firstName = firstNameID.getText();
//            firstNameID.clear();
//            String adress = addressID.getText();
//            addressID.clear();
//            String birthdate = birthdateID.getText();
//            birthdateID.clear();
//
//        } catch (Exception ex) {
//            System.out.println("Something went wrong");
//
//        }
//
//    }
//
//    public ObservableList<CustomerProperty> getCustomer() throws IOException, JSONException{
//
//        ObservableList<CustomerProperty> customers = FXCollections.observableArrayList();
//        //customers.add(new CustomerProperty(1, "Johny", "Walker", "London", "1972-07-01", "7207012222"));
//        Customers myCustomer = new Customers();
//        //Managers manager = new Managers();
//        Gson gson = new Gson();
//        ObservableList<CustomerProperty> customersProperty = FXCollections.observableArrayList();
//        JSONObject jo = new JSONObject();
//        JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL("http://localhost:8080/MainServerREST/api/customers"), Charset.forName("UTF-8")));
//        System.out.println(jsonArray);
//        for (int i = 0; i < jsonArray.length(); i++) {
//            jo = (JSONObject) jsonArray.getJSONObject(i);
//            myCustomer = gson.fromJson(jo.toString(), Customers.class);
//            System.out.println(myCustomer.getCuId());
//            customersProperty.add(new CustomerProperty(myCustomer.getCuId(), myCustomer.getCuFirstname(),
//                    myCustomer.getCuLastname(), myCustomer.getCuBirthdate(), myCustomer.getCuAddress(),
//                    myCustomer.getCuPersonnummer()));
//
//        
//    }
//        return customersProperty;
//}
//}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;
import aajavafx.entities.Customers;
import aajavafx.entities.Employees;
import aajavafx.entities.Managers;
import entitiesproperty.CustomerProperty;
import com.google.gson.Gson;
import entitiesproperty.EmployeeProperty;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
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
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * FXML Controller class
 *
 * @author lokeshdhakal
 */
public class CustomerController implements Initializable {
    private static String postCustomerURL = "https://localhost:8181/MainServerREST/api/customers";
    // private static String postCustomerURL = "http://192.168.43.205:8080/MainServerREST/api/customers";
    @FXML
    private TableView<CustomerProperty> tableCustomers = new TableView<CustomerProperty>();
    @FXML
    private TableView<CustomerProperty> tableCustomer;
    @FXML
    private TableColumn<CustomerProperty, Integer> idCustomer;
    @FXML
    private TableColumn<CustomerProperty, String> firstNameColumn;
    @FXML
    private TableColumn<CustomerProperty, String> lastNameColumn;
    @FXML
    private TableColumn<CustomerProperty, String> addressColumn;
    @FXML
    private TableColumn<CustomerProperty, String> birthdateColumn;
    @FXML
    private TableColumn<CustomerProperty, String> persunnumerColumn;
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
    //in order to create customer object from "register" button
    String lastName;
    String firstName;
    String address;
    String birthdate;
    String persunnumer;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonRegister.setVisible(false);
        //customerID.setVisible(false);
        firstNameID.setVisible(false);
        lastNameID.setVisible(false);
        addressID.setVisible(false);
        birthdateID.setVisible(false);
        persunnumerID.setVisible(false);
        //initialize columns
        idCustomer.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        birthdateColumn.setCellValueFactory(cellData -> cellData.getValue().birthdateProperty());
        persunnumerColumn.setCellValueFactory(cellData -> cellData.getValue().personnumerProperty());
        try {
            //Populate table 
            tableCustomer.setItems(getCustomer());
        } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void handleGoBack(ActionEvent event) {
        //labelError.setText(null);
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageTab.fxml"));
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
        //customerID.setVisible(true);
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
            //String customerNumber = customerID.getText();
            //customerID.clear();
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
            try {
                Gson gson = new Gson();
                Customers customer = new Customers(1, firstName, lastName, address, birthdate, persunnumer);
                String jsonString = new String(gson.toJson(customer));
                System.out.println("json string: " + jsonString);
                StringEntity postString = new StringEntity(jsonString);
                CredentialsProvider provider = new BasicCredentialsProvider();
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
                provider.setCredentials(AuthScope.ANY, credentials);
                HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
                HttpPost post = new HttpPost(postCustomerURL);
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
            ex.printStackTrace();
            
            
        }
            try {
            //refresh table
            tableCustomer.setItems(getCustomer());
        } catch (Exception e) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, e);
        } 
        
    }
    @FXML
    private void handlePrintButton(ActionEvent event) {
        //  labelError.setText(null);
        buttonRegister.setVisible(false);
        //customerID.setVisible(true);
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
    public ObservableList<CustomerProperty> getCustomer() throws IOException, JSONException {
        
        ObservableList<CustomerProperty> customers = FXCollections.observableArrayList();
        //customers.add(new CustomerProperty(1, "Johny", "Walker", "London", "1972-07-01", "7207012222"));
        Customers myCustomer = new Customers();
        //Managers manager = new Managers();
        Gson gson = new Gson();
        ObservableList<CustomerProperty> customersProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("https://localhost:8181/MainServerREST/api/customers");
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);
        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            myCustomer = gson.fromJson(jo.toString(), Customers.class);
            System.out.println(myCustomer.getCuId());
            customersProperty.add(new CustomerProperty(myCustomer.getCuId(), myCustomer.getCuFirstname(),
                    myCustomer.getCuLastname(), myCustomer.getCuBirthdate(), myCustomer.getCuAddress(),
                    myCustomer.getCuPersonnummer()));
        }
        return customersProperty;
    }
}
 
