/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author lokeshdhakal
 */
public class Customer extends PersonCustomer {
    
    private final StringProperty initialer = new SimpleStringProperty(this, "initialer");
    
    public StringProperty initialerProperty() {
        return initialer;
    }

    public final String getInitialer() {
        return initialerProperty().get();
    }

    public final void setInitialer(String title) {
        initialerProperty().set(title);
    }
    
     public Customer() {
    }

    //public Customer(int customerID, String lastName, String firstName, String adress, String birthdate, String personnumer, String initialer) {
      //  super(customerID,firstName, lastName, adress, birthdate, personnumer);
        //setInitialer(initialer);
        public Customer(int customerID, String lastName, String firstName, String adress, String birthdate, String personnumer) {
        super(customerID,firstName, lastName, adress, birthdate, personnumer);
        
        
        
    }
    
}
