package aajavafx;

import aajavafx.entities.Customers;
import aajavafx.entities.Employees;
import aajavafx.entities.EmployeeSchedule;
import com.google.gson.Gson;
import entitiesproperty.CustomerProperty;
import entitiesproperty.EmployeeProperty;
import entitiesproperty.EmployeeScheduleProperty;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.panos.SSLConnection;

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

    Singleton singleton, singletonSchedule = null;

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
                display.setText("You choose: "+ getDate());

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
        } catch (Exception ex) {
            Logger.getLogger(Schedule1Controller.class.getName()).log(Level.SEVERE, null, ex);
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
    private void handleDeleteButton(ActionEvent event) throws Exception {

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

    public ObservableList<EmployeeScheduleProperty> getSchedule() throws IOException, JSONException, Exception {
        EmployeeSchedule mySchedule = new EmployeeSchedule();
        Gson gson = new Gson();

        ObservableList<EmployeeScheduleProperty> employeeScheduleProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();

        SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
        String response = sslc.doGet("employeeschedule", "", SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
        JSONArray jsonArray = new JSONArray(response);

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

    public ObservableList<CustomerProperty> getCustomer() throws IOException, JSONException, Exception {
        Customers customers = new Customers();
        Gson gson = new Gson();
        ObservableList<CustomerProperty> customerProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();

        SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
        String response = sslc.doGet("customers", "", SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
        JSONArray jsonArray = new JSONArray(response);

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

    public ObservableList<EmployeeProperty> getEmployee() throws IOException, JSONException, Exception {
        Employees myEmployee = new Employees();

        Gson gson = new Gson();
        ObservableList<EmployeeProperty> employeesProperty = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();

        SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
        String response = sslc.doGet("employees", "", SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
        JSONArray jsonArray = new JSONArray(response);

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

    public ObservableList<CustomerProperty> getUnsignedCustomers() throws IOException, JSONException, Exception {

        EmployeeSchedule mySchedule = new EmployeeSchedule();
        singleton = Singleton.getInstance();
        Gson gson = new Gson();

        ObservableList<CustomerProperty> customerPropertyCustomersSigned = FXCollections.observableArrayList();
        ObservableList<CustomerProperty> customerPropertyAllCustomers = this.getCustomer();
        JSONObject jo = new JSONObject();

        SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
        String response = sslc.doGet("employeeschedule/date", getDate(), SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
        JSONArray jsonArray = new JSONArray(response);
        System.out.println("1 " + jsonArray);

        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);

            mySchedule = gson.fromJson(jo.toString(), EmployeeSchedule.class);

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

    public double getNumbersOfHoursPerDay(int id) throws JSONException, IOException, Exception {
        double numberHours = 0;
        double numberStart = 0;
        double numberFinish = 0;
        double total = 8.00;
        //Customers customers = new Customers();
        EmployeeSchedule mySchedule = new EmployeeSchedule();

        Gson gson = new Gson();
        JSONObject jo = new JSONObject();
        String date = getDate();
        if (date.equals(null)) {
            display.setText("You must choose a date first!!!");
        } else {
            SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
            String response = sslc.doGet("employeeschedule/date", date, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
            JSONArray jsonArray = new JSONArray(response);

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
            total = total - numberHours;

            return total;
        }
        return 0.0;
    }

    @FXML
    private void handleCreateNewSchedule(ActionEvent event) throws JSONException, IOException, Exception {
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

    public void setDate(String date) {
        this.date = date;
    }

    @FXML
    private void handleValidate(ActionEvent event) throws Exception {
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
            String jsonString = gson.toJson(schedule);

            SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
            String response = sslc.doPost("employeeschedule", jsonString, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
            System.out.println(response);

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
            SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
            String response = sslc.doDelete("employeeschedule", idToDelete, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
            System.out.println(response);
            System.out.println("you want to delete: " + id);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
