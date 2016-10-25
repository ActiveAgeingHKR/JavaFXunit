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

    private final StringProperty initialer = new SimpleStringProperty(this, "initialer");
    private final IntegerProperty salary = new SimpleIntegerProperty(this, "salary");

    public StringProperty initialerProperty() {
        return initialer;
    }

    public final String getInitialer() {
        return initialerProperty().get();
    }

    public final void setInitialer(String title) {
        initialerProperty().set(title);
    }

    public IntegerProperty salaryProperty() {
        return salary;
    }

    public final int getSalary() {
        return salaryProperty().get();
    }

    public final void setSalary(int salary) {
        salaryProperty().set(salary);
    }

    public Employee() {
    }

    public Employee(String lastName, String firstName, String adress, String email, int phone, String initialer, int salary) {
        super(lastName, firstName, adress, email, phone);
        setInitialer(initialer);
        setSalary(salary);
    }

   
}


