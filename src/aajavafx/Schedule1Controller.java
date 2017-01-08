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
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    private Button viewEmployee;
    @FXML
    private Button viewSchedule;
    
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
    @FXML
    private Button validation;
    Button calendar;
    private String date;
    private int idEmployee;
    private int idCustomer;
    boolean allEmployee;
    boolean allSchedule;
    Singleton singleton, singletonSchedule = null;
    private String myDate = "";
    
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        
        Date dateTemp = new Date();
        myDate = dateFormat.format(dateTemp);
        
        display.setText("Current date: " + myDate);
        
        pickADate.setValue(LocalDate.now());
        pickADate.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = pickADate.getValue();
                System.err.println("Selected date: " + date);
                setDate(pickADate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                System.out.println("Date now: " + getDate());
                myDate = getDate();
                display.setText("You choose: " + myDate);
                //  viewEmployee.setVisible(true);
                //  viewSchedule.setVisible(true);

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
        validation.setVisible(false);
        allEmployee = false;
        allSchedule = false;
        // viewEmployee.setVisible(false);
        // viewSchedule.setVisible(false);
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
        
        ArrayList<CustomerProperty> customerPropertyCustomersSigned = new ArrayList<CustomerProperty>();
        ObservableList<CustomerProperty> customerPropertyAllCustomers = this.getCustomer();
        JSONObject jo = new JSONObject();
        
        SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
        String response = sslc.doGet("employeeschedule/date", myDate, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
        JSONArray jsonArray = new JSONArray(response);
        
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            
            mySchedule = gson.fromJson(jo.toString(), EmployeeSchedule.class);
            
            customerPropertyCustomersSigned.add(
                    new CustomerProperty(mySchedule.getCustomersCuId().getCuId(),
                            mySchedule.getCustomersCuId().getCuFirstname(), mySchedule.getCustomersCuId().getCuLastname(),
                            mySchedule.getCustomersCuId().getCuPersonnummer()));
            
        }
        
        try {
            for (int i = 0; i < customerPropertyCustomersSigned.size(); i++) {
                
                for (int j = 0; j < customerPropertyAllCustomers.size(); j++) {
                    
                    if (customerPropertyCustomersSigned.get(i).getPersonnumer().equals(customerPropertyAllCustomers.get(j).getPersonnumer())) {
                        
                        customerPropertyAllCustomers.remove(j);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        singleton.setList(customerPropertyAllCustomers);
        return customerPropertyAllCustomers;
    }
    
    public double getNumbersOfHoursPerDay(int id) throws JSONException, IOException, Exception {
        double numberHours = 0;
        double numberStart = 0;
        double numberFinish = 0;
        double total = 7.60;
        //Customers customers = new Customers();
        EmployeeSchedule mySchedule = new EmployeeSchedule();
        
        Gson gson = new Gson();
        JSONObject jo = new JSONObject();
        //String date = getDate();

        SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
        String response = sslc.doGet("employeeschedule/date", myDate, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
        JSONArray jsonArray = new JSONArray(response);
        
        System.out.println("2 " + jsonArray);
        
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            
            mySchedule = gson.fromJson(jo.toString(), EmployeeSchedule.class);
            if (mySchedule.getEmployeesEmpId().getEmpId().equals(id)) {
                numberFinish = Double.valueOf(mySchedule.getSchUntilTime()) + numberFinish;
                numberFinish = this.splitDouble(numberFinish);
                System.out.println("Finish: " + Double.valueOf(mySchedule.getSchUntilTime()));
                numberStart = Double.valueOf(mySchedule.getSchFromTime()) + numberStart;
                numberStart = this.splitDouble(numberStart);
            }
        }
        numberHours = this.calculateTime(numberFinish, numberStart);
        if(total>=numberHours){
        total = this.calculateTime(total, numberHours);
        }else{
             total = this.calculateTime(numberHours,total);
             total=total*(-1);
        }
        total = this.splitDouble(total);
        
        return total;
        
    }
    
    @FXML
    private void handleCreateNewSchedule(ActionEvent event) throws JSONException, IOException, Exception {
        display.setText("");
        try{
        String tempDate;
        DecimalFormat df2 = new DecimalFormat(".##");
        idEmployee = tableEmployee.getSelectionModel().getSelectedItem().getId();
        idCustomer = tableCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        
        String tempId = idEmployee + "";
        String tempIdCust = idCustomer + "";
        textEmpId.setText(tempId);
        textCustId.setText(tempIdCust);
        dateText.setText(myDate);
        System.out.println(myDate);
        System.out.println("Hours : " + df2.format(this.getNumbersOfHoursPerDay(idEmployee)));
        
        display.setText("There are " + df2.format(this.getNumbersOfHoursPerDay(idEmployee)) + " hours remain to work for this employee");
        validation.setVisible(true);
        }catch(Exception ex){
            System.out.println("Error: "+ex);
            display.setText("You must choose one employee and one customer first");
        }
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
            if (tempFinish.equals("") || (tempStart.equals(""))) {
                display.setText("You must fill the hours first!");
            } else {
                
                Gson gson = new Gson();
                Employees employee = new Employees(empId);
                Customers customers = new Customers(cuId);
                EmployeeSchedule schedule = new EmployeeSchedule(1, tempDate, tempStart, tempFinish, false, customers, employee);
                String jsonString = gson.toJson(schedule);
                
                SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
                String response = sslc.doPost("employeeschedule", jsonString, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
                System.out.println(response);
            }
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
        validation.setVisible(false);
        textFinish.clear();
        textStart.clear();
        display.setText("");
        
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
    
    @FXML
    private void handleViewEmployee(ActionEvent event) throws JSONException, Exception {
        
        if (allEmployee == true) {
            viewEmployee.setText("Select available employees");
            allEmployee = false;
            tableEmployee.setItems(getEmployee());
            
        } else {
            viewEmployee.setText("Select all employees");
            allEmployee = true;
            tableEmployee.setItems(getAvailableEmployee());
        }
    }
    
    @FXML
    private void handleViewSchedule(ActionEvent event) throws JSONException, Exception {
        if (allSchedule == true) {
            viewSchedule.setText("View schedule by date");
            allSchedule = false;
            tableSchedule.setItems(getSchedule());
        } else {
            
            viewSchedule.setText("View all schedule");
            allSchedule = true;
            tableSchedule.setItems(getScheduleByDate(myDate));
            
        }
        
    }
    
    public ObservableList<EmployeeProperty> getAvailableEmployee() throws JSONException, Exception {
        double tempHours = 0.0;
        double maxHours = 8.0;
        
        ObservableList<EmployeeProperty> allEmployees = this.getEmployee();
        System.out.println("Size: " + allEmployees.size());
        for (int i = 0; i < allEmployees.size(); i++) {
            tempHours = this.getNumbersOfHoursPerDay(allEmployees.get(i).getId());
            if (tempHours > 0) {
                tempHours = 8 - tempHours;
            } else {
                tempHours = tempHours - 8;
                tempHours = (-1) * tempHours;
            }
            if (tempHours >= maxHours) {
                allEmployees.remove(i);
                
            } else {
                System.out.println("You have hours to work!!");
            }
        }
        return allEmployees;
    }
    
    public ObservableList<EmployeeScheduleProperty> getScheduleByDate(String date) throws IOException, JSONException, Exception {
        EmployeeSchedule mySchedule = new EmployeeSchedule();
        Gson gson = new Gson();
        ObservableList<EmployeeScheduleProperty> schedulePropertyByDate = FXCollections.observableArrayList();
        JSONObject jo = new JSONObject();
        
        SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
        String response = sslc.doGet("employeeschedule/date", date, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
        JSONArray jsonArray = new JSONArray(response);
        
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            
            mySchedule = gson.fromJson(jo.toString(), EmployeeSchedule.class);
            
            schedulePropertyByDate.add(
                    new EmployeeScheduleProperty(mySchedule.getSchId(), mySchedule.getSchDate(),
                            mySchedule.getSchFromTime(), mySchedule.getSchUntilTime(), mySchedule.getEmplVisitedCust(),
                            mySchedule.getCustomersCuId().getCuPersonnummer(), mySchedule.getEmployeesEmpId().getEmpUsername()));
        }
        
        return schedulePropertyByDate;
    }
    
    public double splitDouble(double d) {
        
        double value = 0.0;
        int whole = (int) d;
        double tempFract =Math.round((d - whole)*100);      
        int fract=(int)tempFract;
          
        if (fract < 60) {
            value = d;
        } else {
            whole = whole + 1;
            fract = fract - 60;
            value = (double) whole + ((double) fract / 100);
        }
        System.out.println("Number from " + d + " to " + value);
 
        return value;
    }
    
    public double calculateTime(double d1, double d2) {
        
        double value = 0.0;
        int whole1 = (int) d1;
        
        double tempFract1 =Math.round((d1 - whole1)*100);
        
        int fract1=(int)tempFract1;
        
        int whole2 = (int) d2;
        double tempFract2 =Math.round((d2 - whole2)*100);
        int fract2=(int)tempFract2;
        System.out.println("d1: " + d1 + " whole1 " + whole1 + " fract1 " + fract1 + " d2: " + d2 + " whole2 " + whole2 + " fract2 " + fract2);
        if (fract1 >= fract2) {
            value = d1 - d2;
        } else {
            whole1 = whole1 - 1;
            fract1 = fract1 + 60;
            d1 = (double) whole1 + ((double) fract1 / 100);
            value = d1 - d2;
        }
        System.out.println("Scadere " + value + "fract1 " + fract1 + "fract2 " + fract2);
       
        return value;
    }
    
}
