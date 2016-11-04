/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author lokeshdhakal
 */
//This class can be removed
//and extend Customer class from Person class instead
//by doing so few adjustment with customerclass would be needed.
public abstract class PersonCustomer {
    private final IntegerProperty customerID = new SimpleIntegerProperty(this, "customerID");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
    private final StringProperty address = new SimpleStringProperty(this, "adress");
    private final StringProperty birthdate = new SimpleStringProperty(this, "birthdate");  //shall we keep birthday as string "1934-05-06" or declare as integer
    private final StringProperty personnumer = new SimpleStringProperty(this, "personnumer"); //same concern as of birthdate

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public final String getFirstName() {
        return firstNameProperty().get();
    }

    public final void setFirstName(String firstName) {
        firstNameProperty().set(firstName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public final String getName() {
        return lastNameProperty().get();
    }

    public final void setLastName(String lastName) {
        lastNameProperty().set(lastName);
    }

    

    public StringProperty addressProperty() {
        return address;
    }

    public final String getAdress() {
        return addressProperty().get();
    }

    public final void setAdress(String adress) {
        addressProperty().set(adress);
    }

    public IntegerProperty customerIDProperty() {
        return customerID;
    }

    public final int getCustomerID() {
        return customerIDProperty().get();
    }

    public final void setCustomerID(int customerID) {
        customerIDProperty().set(customerID);
    }
    
    public StringProperty birthdateProperty() {
        return birthdate;
    }

    public final String getBirthdate() {
        return birthdateProperty().get();
    }

    public final void setBirthdate(String birthdate) {
        birthdateProperty().set(birthdate);
    }
    
    public StringProperty personnumerProperty() {
        return personnumer;
    }

    public final String getPersonnumer() {
        return personnumerProperty().get();
    }

    public final void setPersonnumer(String personnumer) {
        personnumerProperty().set(personnumer);
    }
    
    

    public PersonCustomer() {
    }

    public PersonCustomer(int customerID, String firstname, String lastname, String adress, String birthdate, String personnumer) {
        
        setCustomerID(customerID);
        setFirstName(firstname);
        setLastName(lastname);
        setAdress(adress);  
        setBirthdate(birthdate);   
        setPersonnumer(personnumer); 
    }

}
