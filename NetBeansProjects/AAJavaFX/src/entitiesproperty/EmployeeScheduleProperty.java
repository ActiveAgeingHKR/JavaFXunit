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
import aajavafx.*;
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
public class EmployeeScheduleProperty {

    private final IntegerProperty schId = new SimpleIntegerProperty(this, "schId");
    private final StringProperty schDate = new SimpleStringProperty(this, "schDate");
    private final StringProperty schFromTime = new SimpleStringProperty(this, "schFromTime");
    private final StringProperty schUntilTime = new SimpleStringProperty(this, "schUntilTime");
    private final BooleanProperty emplVisitedCust = new SimpleBooleanProperty(this, "emplVisitedCust");
    private final StringProperty customersCuId = new SimpleStringProperty(this, "customersCuId");
    private final StringProperty employeesEmpId = new SimpleStringProperty(this, "employeesEmpId");

    
    
    public IntegerProperty schIdProperty() {
        return schId;
    }

    public final Integer getSchId() {
        return schIdProperty().get();
    }

    public final void setSchId(Integer schId) {
        schIdProperty().set(schId);
    }

    
    /////
    
    
    public StringProperty schDateProperty() {
        return schDate;
    }

    public final String getSchDate() {
        return schDateProperty().get();
    }

    public final void setSchDate(String schDate) {
        schDateProperty().set(schDate);
    }

    ////
    
    public StringProperty schFromTimeProperty() {
        return schFromTime;
    }

    public final String getSchFromTime() {
        return schFromTimeProperty().get();
    }

    public final void setSchFromTime(String schFromTime) {
        schFromTimeProperty().set(schFromTime);
    }
////
    
    
    public StringProperty schUntilTimeProperty() {
        return schUntilTime;
    }

    public final String getSchUntilTime() {
        return schUntilTimeProperty().get();
    }

    public final void setSchUntilTime(String schUntilTime) {
        schUntilTimeProperty().set(schUntilTime);
    }

    ////
    
    public BooleanProperty emplVisitedCustProperty() {
        return emplVisitedCust;
    }

    public final Boolean getEmplVisitedCust() {
        return emplVisitedCustProperty().get();
    }

    public final void setEmplVisitedCust(Boolean emplVisitedCust) {
        emplVisitedCustProperty().set(emplVisitedCust);
    }

    ////
    
    public StringProperty customersCuIdProperty() {
        return customersCuId;
    }

    public final String getCustomersCuId() {
        return customersCuIdProperty().get();
    }

    public final void setCustomersCuId(String customersCuId) {
        customersCuIdProperty().set(customersCuId);
    }
//////
    public StringProperty employeesEmpIdProperty() {
        return employeesEmpId;
    }

    public final String getEmployeesEmpId() {
        return employeesEmpIdProperty().get();
    }

    public final void setEmployeesEmpId(String employeesEmpId) {
        employeesEmpIdProperty().set(employeesEmpId);
    }
    //////
    
    public EmployeeScheduleProperty() {
    }

     
    public EmployeeScheduleProperty(Integer schId, String schDate, String schFromTime, String schUntilTime, boolean emplVisitedCust,
            String customersCuId, String employeesEmpId) {
       
        setSchId(schId);
        setSchDate(schDate);
        setSchFromTime(schFromTime);
        setSchUntilTime(schUntilTime);
        setEmplVisitedCust(emplVisitedCust);
        setCustomersCuId(customersCuId);
        setEmployeesEmpId(employeesEmpId);
    }

}
