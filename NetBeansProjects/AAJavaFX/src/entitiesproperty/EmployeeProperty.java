/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesproperty;

/**
 *
 * @author Iuliu
 */
import java.io.Serializable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Iuliu
 */
public class EmployeeProperty extends PersonProperty {

    private final StringProperty empUserName = new SimpleStringProperty(this, "empUserName");
    private final StringProperty empPassword = new SimpleStringProperty(this, "empPassword");
    private final StringProperty empEmail = new SimpleStringProperty(this, "empEmail");
    private final StringProperty empPhone = new SimpleStringProperty(this, "empPhone");
    private final IntegerProperty managerId = new SimpleIntegerProperty(this, "managerId");
    private final BooleanProperty empValidation = new SimpleBooleanProperty(this, "empValidation");

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
     public BooleanProperty empValidationProperty() {
        return empValidation;
    }

    public final boolean getEmpValidation() {
        return empValidationProperty().get();
    }

    public final void setEmpValidation(boolean empValidation) {
        empValidationProperty().set(empValidation);
    }
    public EmployeeProperty() {
    }

    public EmployeeProperty(Integer empId, String empFirstname, String empLastname, String empUsername, String empPassword,
            String empEmail, String empPhone, Integer managersID,boolean empRegistered) {
        super(empId,empFirstname, empLastname);
        setEmpUserName(empUsername);
        setEmpPassword(empPassword);
        setEmpEmail(empEmail);
        setEmpPhone(empPhone);
        setManagerId(managersID);
        setEmpValidation(empRegistered);
    }
  public EmployeeProperty(Integer empId, String empFirstname, String empLastname) {
        super(empId,empFirstname, empLastname);
       
    }
}
