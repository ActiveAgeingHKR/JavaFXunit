/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesproperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author lokeshdhakal
 */
//This class can be removed
//and extend CustomerProperty class from Person class instead
//by doing so few adjustment with customerclass would be needed.
public class CustomerProperty {

    private final IntegerProperty cuId = new SimpleIntegerProperty(this, "cuId");

    private final StringProperty cuFirstname = new SimpleStringProperty(this, "cuFirstName");
    private final StringProperty cuLastname = new SimpleStringProperty(this, "cuLastname");

    private final StringProperty cuBirthdate = new SimpleStringProperty(this, "cuBirthdate");  //shall we keep birthday as string "1934-05-06" or declare as integer
    private final StringProperty cuAddress = new SimpleStringProperty(this, "cuAddress");
    private final StringProperty cuPersonnummer = new SimpleStringProperty(this, "cuPersonnumer"); //same concern as of cuBirthdate

    public StringProperty firstNameProperty() {
        return cuFirstname;
    }

    public final String getFirstName() {
        return firstNameProperty().get();
    }

    public final void setFirstName(String firstName) {
        firstNameProperty().set(firstName);
    }

    public StringProperty lastNameProperty() {
        return cuLastname;
    }

    public final String getName() {
        return lastNameProperty().get();
    }

    public final void setLastName(String lastName) {
        lastNameProperty().set(lastName);
    }

    public StringProperty addressProperty() {
        return cuAddress;
    }

    public final String getAdress() {
        return addressProperty().get();
    }

    public final void setAdress(String adress) {
        addressProperty().set(adress);
    }

    public IntegerProperty customerIdProperty() {
        return cuId;
    }

    public final Integer getCustomerId() {
        return customerIdProperty().get();
    }

    public final void setCustomerId(Integer customerId) {
        customerIdProperty().set(customerId);
    }

    public StringProperty birthdateProperty() {
        return cuBirthdate;
    }

    public final String getBirthdate() {
        return birthdateProperty().get();
    }

    public final void setBirthdate(String birthdate) {
        birthdateProperty().set(birthdate);
    }

    public StringProperty personnumerProperty() {
        return cuPersonnummer;
    }

    public final String getPersonnumer() {
        return personnumerProperty().get();
    }

    public final void setPersonnumer(String personnumer) {
        personnumerProperty().set(personnumer);
    }

    public CustomerProperty() {
    }

    public CustomerProperty(Integer cuId, String cuFirstname, String cuLastname, String cuBirthdate, String cuAddress, String cuPersonnumer) {

        setCustomerId(cuId);
        setFirstName(cuFirstname);
        setLastName(cuLastname);
        setAdress(cuBirthdate);
        setBirthdate(cuAddress);
        setPersonnumer(cuPersonnumer);
    }

   // CustomerProperty customer = new CustomerProperty (0, lastName, firstName, birthdate, address, persunnumer);
    public CustomerProperty(Integer cuId,String cuFirstname, String cuLastname, String e) {

        setCustomerId(cuId);
        setFirstName(cuFirstname);
        setLastName(cuLastname);
        setPersonnumer(e);
    }
}
