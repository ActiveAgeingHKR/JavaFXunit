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
 * @author Rolandas
 */
public class VisitorsProperty {
    private final StringProperty visId = new SimpleStringProperty(this, "visId");
    private final StringProperty visFirstname = new SimpleStringProperty(this, "visFirstname");
    private final StringProperty visLastname = new SimpleStringProperty(this, "visLastname");
    private final StringProperty visEmail = new SimpleStringProperty(this, "visEmail");
    private final StringProperty visPhone = new SimpleStringProperty(this, "visPhone");
    private final IntegerProperty companyCompId = new SimpleIntegerProperty(this, "companyCompId");

    public StringProperty visIdProperty() {
        return visId;
    }

    public final String getVisId() {
        return visIdProperty().get();
    }

    public final void setVisId(String visitorId) {
        visIdProperty().set(visitorId);
    }

    public StringProperty visFirstnameProperty() {
        return visFirstname;
    }
    
    public final String getVisFirstname() {
        return visFirstnameProperty().get();
    }
    
    public final void setVisFirstname(String visitorFirstname) {
        visFirstnameProperty().set(visitorFirstname);
    }
    
    public StringProperty visLastnameProperty() {
        return visLastname;
    }
    
    public final String getVisLastname() {
        return visLastnameProperty().get();
    }
    
    public final void setVisLastname(String visitorLastname) {
        visLastnameProperty().set(visitorLastname);
    }
    
    public StringProperty visEmailProperty() {
        return visEmail;
    }
    
    public final String getVisEmail() {
        return visEmailProperty().get();
    }
    
    public final void setVisEmail(String visitorEmail) {
        visEmailProperty().set(visitorEmail);
    }
    
    public StringProperty visPhoneProperty() {
        return visPhone;
    }
    
    public final String getVisPhone() {
        return visPhoneProperty().get();
    }
    
    public final void setVisPhone(String visitorPhone) {
        visEmailProperty().set(visitorPhone);
    }
    
    public IntegerProperty companyCompIdProperty() {
        return companyCompId;
    }
    
    public final Integer getCompanyCompId() {
        return companyCompIdProperty().get();
    }
    
    public final void setCompanyCompId(Integer companyId) {
        companyCompIdProperty().set(companyId);
    }
    
    public VisitorsProperty() {
    }

    public VisitorsProperty(String visId, String visFirstname, String visLastname, String visEmail, String visPhone, Integer companyId) {

        setVisId(visId);
        setVisFirstname(visFirstname);
        setVisLastname(visLastname);
        setVisEmail(visEmail);
        setVisPhone(visPhone);
        setCompanyCompId(companyId);
    }

}
