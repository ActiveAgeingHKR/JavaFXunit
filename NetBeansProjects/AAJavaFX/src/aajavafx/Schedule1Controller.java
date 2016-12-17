/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import aajavafx.entities.Customers;
import aajavafx.entities.Employees;
import aajavafx.entities.EmployeeSchedule;
import aajavafx.entities.Managers;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import entitiesproperty.CustomerProperty;
import entitiesproperty.EmployeeProperty;
import entitiesproperty.EmployeeScheduleProperty;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import org.apache.http.client.methods.HttpDelete;
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
 * @author Iuliu
 */
public class Schedule1Controller implements Initializable {

    /**
     * Initializes the controller class.
     *
     */
    @FXML
    private Label display;
    @FXML
    private TextField dateText;
    @FXML
    private TextField textEmpId;
    @FXML
    private TextField textCustId;
    @FXML
    private TextField textFinish;
    @FXML
    private TextField textStart;
    @FXML
    private TableView<EmployeeScheduleProperty> tableSchedule;
    @FXML
    private TableColumn<EmployeeScheduleProperty, Integer> schIdColumn;
    @FXML
    private TableColumn<EmployeeScheduleProperty, String> schDateColumn;
    @FXML
    private TableColumn<EmployeeScheduleProperty, String> schFromTimeColumn;
    @FXML
    private TableColumn<EmployeeScheduleProperty, String> schUntilTimeColumn;
    @FXML
    private TableColumn<EmployeeScheduleProperty, Boolean> emplVisitedCustColumn;
    @FXML
    private TableColumn<EmployeeScheduleProperty, String> customersCuIdColumn;
    @FXML
    private TableColumn<EmployeeScheduleProperty, String> employeesEmpIdColumn;

    @FXML
    private TableView<EmployeeProperty> tableEmployee;
    @FXML
    private TableColumn<EmployeeProperty, Integer> empIdColumn;
    @FXML
    private TableColumn<EmployeeProperty, String> empFirstNameColumn;
    @FXML
    private TableColumn<EmployeeProperty, String> empLastColumn;
    @FXML
    private TableColumn<EmployeeProperty, String> empUserNameColumn;

    @FXML
    private TableView<CustomerProperty> tableCustomer;
    @FXML
    private TableColumn<CustomerProperty, Integer> cuIdColumn;
    @FXML
    private TableColumn<CustomerProperty, String> cuFirstNameColumn;
    @FXML
    private TableColumn<CustomerProperty, String> cuLastNameColumn;
    @FXML
    private TableColumn<CustomerProperty, String> cuPersonnumerColumn;

    @FXML
    private DatePicker pickADate;
    Button calendar;
    private String date;
    private int idEmployee;
    private int idCustomer;
   
    Singleton singleton = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        schIdColumn.setCellValueFactory(cellData -> cellData.getValue().schIdProperty().asObject());
        schDateColumn.setCellValueFactory(cellData -> cellData.getValue().schDateProperty());
        schFromTimeColumn.setCellValueFactory(cellData -> cellData.getValue().schFromTimeProperty());
        schUntilTimeColumn.setCellValueFactory(cellData -> cellData.getValue().schUntilTimeProperty());
        emplVisitedCustColumn.setCellValueFactory(cellData -> cellData.getValue().emplVisitedCustProperty().asObject());
        customersCuIdColumn.setCellValueFactory(cellData -> cellData.getValue().customersCuIdProperty());
        employeesEmpIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeesEmpIdProperty());

        empIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        empFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNProperty());
        empLastColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        empUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().empUserNameProperty());

        cuIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        cuFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        cuLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        cuPersonnumerColumn.setCellValueFactory(cellData -> cellData.getValue().personnumerProperty());

        pickADate.setValue(LocalDate.now());
        pickADate.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = pickADate.getValue();
                System.err.println("Selected date: " + date);
                setDate(pickADate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                System.out.println("Date now: " + getDate());

            }

        });

        try {

            tableEmployee.setItems(getEmployee());
            tableCustomer.setItems(getCustomer());
            tableSchedule.setItems(getSchedule());
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleGetCustomersUnvisited(ActionEvent event) throws IOException {
        try {
            getUnsignedCustomers();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UnsignedCustomers.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {

        int id = tableSchedule.getSelectionModel().getSelectedItem().getSchId();
        this.deleteRow(id);
        try {

            tableSchedule.setItems(getSchedule());
            System.out.println("Update");
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
            Logger.getLogger(MainPageController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<EmployeeScheduleProperty> getSchedule() throws IOException, JSONException {
        EmployeeSchedule mySchedule = new EmployeeSchedule();

        Customers customers = new Customers();
        Employees employee = new Employees();
        Gson gson = new Gson();

        ObservableList<EmployeeScheduleProperty> employeeScheduleProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EMPLOYEE", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/employeeschedule");
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);

        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            jo = (JSONObject) jsonArray.getJSONObject(i);
            mySchedule
                    = gson.fromJson(jo.toString(), EmployeeSchedule.class
                    );

            employeeScheduleProperty.add(
                    new EmployeeScheduleProperty(mySchedule.getSchId(), mySchedule.getSchDate(),
                            mySchedule.getSchFromTime(), mySchedule.getSchUntilTime(), mySchedule.getEmplVisitedCust(),
                            mySchedule.getCustomersCuId().getCuPersonnummer(), mySchedule.getEmployeesEmpId().getEmpUsername()));
        }

        return employeeScheduleProperty;
    }

    public ObservableList<CustomerProperty> getCustomer() throws IOException, JSONException {
        Customers customers = new Customers();
        Gson gson = new Gson();
        ObservableList<CustomerProperty> customerProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EMPLOYEE", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/customers");
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);

        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            customers
                    = gson.fromJson(jo.toString(), Customers.class
                    );

            customerProperty.add(
                    new CustomerProperty(customers.getCuId(), customers.getCuFirstname(), customers.getCuLastname(), customers.getCuPersonnummer()));
        }
        return customerProperty;

    }

    public ObservableList<EmployeeProperty> getEmployee() throws IOException, JSONException {
        Employees myEmployee = new Employees();

        Gson gson = new Gson();
        ObservableList<EmployeeProperty> employeesProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EMPLOYEE", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/employees");
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);

        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            myEmployee = gson.fromJson(jo.toString(), Employees.class);
            if (myEmployee.getEmpRegistered() == true) {
                employeesProperty.add(new EmployeeProperty(myEmployee.getEmpId(), myEmployee.getEmpFirstname(), myEmployee.getEmpLastname(), myEmployee.getEmpUsername()));
            }
        }
        return employeesProperty;
    }

    public ObservableList<CustomerProperty> getUnsignedCustomers() throws IOException, JSONException {
        //Customers customers = new Customers();
        EmployeeSchedule mySchedule = new EmployeeSchedule();
        singleton = Singleton.getInstance();
        Gson gson = new Gson();

        ObservableList<CustomerProperty> customerPropertyCustomersSigned = FXCollections.observableArrayList();
        ObservableList<CustomerProperty> customerPropertyAllCustomers = this.getCustomer();
        JSONObject jo = new JSONObject();

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EMPLOYEE", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/employeeschedule/date/" + getDate());
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);

        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));
        System.out.println("1 " + jsonArray);

        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);

            mySchedule
                    = gson.fromJson(jo.toString(), EmployeeSchedule.class
                    );

            customerPropertyCustomersSigned.add(
                    new CustomerProperty(mySchedule.getCustomersCuId().getCuId(),
                            mySchedule.getCustomersCuId().getCuFirstname(), mySchedule.getCustomersCuId().getCuLastname(),
                            mySchedule.getCustomersCuId().getCuPersonnummer()));

        }

        for (int i = 0; i < customerPropertyAllCustomers.size(); i++) {

            for (int j = 0; j < customerPropertyCustomersSigned.size(); j++) {

                if (customerPropertyAllCustomers.get(i).getPersonnumer().equals(customerPropertyCustomersSigned.get(j).getPersonnumer())) {

                    customerPropertyAllCustomers.remove(i);
                }
            }
        }

        singleton.setList(customerPropertyAllCustomers);
        return customerPropertyAllCustomers;
    }

    public double getNumbersOfHoursPerDay(int id) throws JSONException, IOException {
        double numberHours = 0;
        double numberStart = 0;
        double numberFinish = 0;
        //Customers customers = new Customers();
        EmployeeSchedule mySchedule = new EmployeeSchedule();

        Gson gson = new Gson();
        JSONObject jo = new JSONObject();

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EMPLOYEE", "password");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet get = new HttpGet("http://localhost:8080/MainServerREST/api/employeeschedule/date/" + getDate());
        HttpResponse response = client.execute(get);
        System.out.println("RESPONSE IS: " + response);

        JSONArray jsonArray = new JSONArray(IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8")));

        System.out.println("2 " + jsonArray);

        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);

            mySchedule = gson.fromJson(jo.toString(), EmployeeSchedule.class);
            if (mySchedule.getEmployeesEmpId().getEmpId().equals(id)) {
                numberFinish = Double.valueOf(mySchedule.getSchUntilTime()) + numberFinish;
                System.out.println("Finish: " + Double.valueOf(mySchedule.getSchUntilTime()));
                numberStart = Double.valueOf(mySchedule.getSchFromTime()) + numberStart;
            }
        }
        numberHours = numberFinish - numberStart;

        return numberHours;
    }

    @FXML
    private void handleCreateNewSchedule(ActionEvent event) throws JSONException, IOException {
        String tempDate;
        DecimalFormat df2 = new DecimalFormat(".##");
        idEmployee = tableEmployee.getSelectionModel().getSelectedItem().getId();
        idCustomer = tableCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        tempDate = this.getDate();
        String tempId = idEmployee + "";
        String tempIdCust = idCustomer + "";
        textEmpId.setText(tempId);
        textCustId.setText(tempIdCust);
        dateText.setText(tempDate);
        System.out.println(tempDate);
        System.out.println("Hours : " + df2.format(this.getNumbersOfHoursPerDay(idEmployee)));
        display.setText("Hours : " + df2.format(this.getNumbersOfHoursPerDay(idEmployee)));
    }

    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    @FXML
    private void handleValidate(ActionEvent event) {
        String tempDate;
        String tempEmpId;
        String tempCustId;
        String tempFinish;
        String tempStart;

        tempDate = dateText.getText();
        tempEmpId = textEmpId.getText();
        tempCustId = textCustId.getText();
        tempFinish = textFinish.getText();
        tempStart = textStart.getText();
        int empId = Integer.valueOf(tempEmpId);
        int cuId = Integer.valueOf(tempCustId);

        try {
            Gson gson = new Gson();
            Employees employee = new Employees(empId);
            Customers customers = new Customers(cuId);
            EmployeeSchedule schedule = new EmployeeSchedule(1, tempDate, tempStart, tempFinish, false, customers, employee);

            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EMPLOYEE", "password");
            provider.setCredentials(AuthScope.ANY, credentials);

            HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
            HttpPost post = new HttpPost("http://localhost:8080/MainServerREST/api/employeeschedule");

            String jsonString = gson.toJson(schedule);
            System.out.println("json string: " + jsonString);
            StringEntity postString = new StringEntity(jsonString);

            post.setEntity(postString);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(post);
            System.out.println("Post response: "+response);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            tableSchedule.setItems(getSchedule());
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteRow(int id) {

        try {
            String idToDelete = id + "";
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EMPLOYEE", "password");
            provider.setCredentials(AuthScope.ANY, credentials);
            HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
            HttpDelete delete = new HttpDelete("http://localhost:8080/MainServerREST/api/employeeschedule/" + id);
            HttpResponse response = httpClient.execute(delete);
            System.out.println("you want to delete: " + id);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
