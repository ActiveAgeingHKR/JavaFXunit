/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

/**
 *
 * @author Iuliu
 */
import aajavafx.Person;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Iuliu
 */
public class Employee extends Person {

    private final StringProperty empUserName = new SimpleStringProperty(this, "empUserName");
    private final StringProperty empPassword = new SimpleStringProperty(this, "empPassword");
    private final StringProperty empEmail = new SimpleStringProperty(this, "empEmail");
    private final StringProperty empPhone = new SimpleStringProperty(this, "empPhone");
    private final IntegerProperty managerId = new SimpleIntegerProperty(this, "managerId");
    private final IntegerProperty empValidation = new SimpleIntegerProperty(this, "empValidation");

    public StringProperty empUserNameProperty() {
        return empUserName;
    }

    public final String getEmpUserName() {
        return empUserNameProperty().get();
    }

    public final void setEmpUserName(String userName) {
        empUserNameProperty().set(userName);
    }

    public StringProperty empPasswordProperty() {
        return empPassword;
    }

    public final String getEmpPassword() {
        return empPasswordProperty().get();
    }

    public final void setEmpPassword(String empPassword) {
        empPasswordProperty().set(empPassword);
    }
 public StringProperty empEmailProperty() {
        return empEmail;
    }

    public final String getEmpEmail() {
        return empEmailProperty().get();
    }

    public final void setEmpEmail(String empEmail) {
        empEmailProperty().set(empEmail);
    }
     public StringProperty empPhoneProperty() {
        return empPhone;
    }

    public final String getEmpPhone() {
        return empPhoneProperty().get();
    }

    public final void setEmpPhone(String empPhone) {
        empPhoneProperty().set(empPhone);
    }
     public IntegerProperty managerIdProperty() {
        return managerId;
    }

    public final Integer getManagerId() {
        return managerIdProperty().get();
    }

    public final void setManagerId(Integer managerId) {
        managerIdProperty().set(managerId);
    }
     public IntegerProperty empValidationProperty() {
        return empValidation;
    }

    public final Integer getEmpValidation() {
        return empValidationProperty().get();
    }

    public final void setEmpValidation(Integer empValidation) {
        empValidationProperty().set(empValidation);
    }
    public Employee() {
    }

    public Employee(int id, String lastName, String firstName, String userName, String password, String email, String phone, int managerId,int isValidate) {
        super(id,lastName, firstName);
        setEmpUserName(userName);
        setEmpPassword(password);
        setEmpEmail(email);
        setEmpPhone(phone);
        setManagerId(managerId);
        setEmpValidation(isValidate);
    }

}
