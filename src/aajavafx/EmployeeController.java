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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.panos.SSLConnection;

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

    @FXML
    private Label labelError;

    Managers manager = new Managers(1);

    ErrorHandling eH = new ErrorHandling();

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
        } catch (Exception ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
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
        textLastName.setVisible(true);
        textFirstName.setVisible(true);
        textUserName.setVisible(true);
        textEmail.setVisible(true);
        textPhone.setVisible(true);
        textPassword.setVisible(true);
        textActivate.setVisible(true);
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) throws Exception {
        Employees employee = new Employees();

        buttonRegister.setVisible(false);
        textLastName.setVisible(false);
        textFirstName.setVisible(false);
        textUserName.setVisible(false);
        textEmail.setVisible(false);
        textPhone.setVisible(false);
        textPassword.setVisible(false);
        textActivate.setVisible(false);
        int id = tableEmployees.getSelectionModel().getSelectedItem().getId();
        this.deleteRow(id);
        try {

            tableEmployees.setItems(getEmployee());
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) throws Exception {
        boolean isValid = false;
        // Check if email is in right format
        try {
            InternetAddress internetaddress = new InternetAddress(textEmail.getText());
            internetaddress.validate();
            isValid = true;
            System.out.println("Email is valid format");
        } catch (AddressException e) {
            System.out.println("Email is invalid - " + textEmail.getText());
        }
        if (isValid == true) {
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
            } else if (tempValidation.contentEquals("N")) {
                register = false;
            } else if (!tempValidation.contentEquals("Y") || tempValidation.contentEquals("N")) {
                eH.popUpMessage("Invalid validation", "Please enter the correct input for validating an employee. (Y for true, N for false)");
            }
            Employees employee = new Employees(1, firstName, lastName, userName, password, email, phone, manager, register);

            validate(employee);

            if (lastName.length() == 0 || firstName.length() == 0 || userName.length() == 0
                    || email.length() == 0 || phone.length() == 0 || password.length() == 0
                    || tempValidation.length() == 0) {
                eH.popUpMessage("Invalid input", "Please make sure all necessary fields have the correct input.");

            }
            try {
                tableEmployees.setItems(getEmployee());
            } catch (IOException ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (isValid == false) {
            eH.popUpMessage("Invalid email address", "Please enter a valid email address.");
        }

    }

    @FXML
    private void handleChangeValidation(ActionEvent event) throws Exception {

        Gson gson = new Gson();
        int id = tableEmployees.getSelectionModel().getSelectedItem().getId();
        String idToChange = id + "";

        boolean tempValidation = tableEmployees.getSelectionModel().getSelectedItem().getEmpValidation();
        if (tempValidation == true) {
            labelError.setText("This employee is valid!!!");
        } else {

            change(id);
            try {
                tableEmployees.setItems(getEmployee());
            } catch (IOException ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public ObservableList<EmployeeProperty> getEmployee() throws IOException, JSONException, Exception {
        Employees myEmployee = new Employees();
        Managers manager = new Managers();
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
            System.out.println(myEmployee.getEmpPhone());
            employeesProperty.add(new EmployeeProperty(myEmployee.getEmpId(), myEmployee.getEmpLastname(),
                    myEmployee.getEmpFirstname(), myEmployee.getEmpUsername(), myEmployee.getEmpPassword(),
                    myEmployee.getEmpEmail(), myEmployee.getEmpPhone(), myEmployee.getManagersManId().getManId(), myEmployee.getEmpRegistered()));
        }
        return employeesProperty;
    }

    public static void validate(Employees emp) throws Exception {
        try {
            Gson gson = new Gson();
            String jsonString = new String(gson.toJson(emp));
            System.out.println("json string: " + jsonString);
            StringEntity postString = new StringEntity(jsonString);
            SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
            String response = sslc.doPost("employees", jsonString, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
            System.out.println(response);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void deleteRow(int id) {
        try {
            String idToDelete = id + "";

            SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
            String response = sslc.doDelete("employees", idToDelete, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
            System.out.println(response);
            System.out.println("you want to delete: " + id);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void change(int id) throws IOException, JSONException, Exception {
        Employees myEmployee = new Employees();

        Gson gson = new Gson();
        Employees employeeNew = null;
        JSONObject jo = new JSONObject();

        SSLConnection sslc = new SSLConnection("https://localhost:8181/MainServerREST/api/");
        String response = sslc.doGet("employees", "", SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.JSON, SSLConnection.USER_MODE.EMPLOYEE);
        JSONArray jsonArray = new JSONArray(response);

        boolean register = true;
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            jo = (JSONObject) jsonArray.getJSONObject(i);
            myEmployee = gson.fromJson(jo.toString(), Employees.class);
            if (myEmployee.getEmpId().equals(id)) {
                employeeNew = new Employees(1, myEmployee.getEmpFirstname(), myEmployee.getEmpLastname(),
                        myEmployee.getEmpUsername(), myEmployee.getEmpPassword(),
                        myEmployee.getEmpEmail(), myEmployee.getEmpPhone(), manager, register);
            }

        }
        deleteRow(id);
        validate(employeeNew);
    }

}
