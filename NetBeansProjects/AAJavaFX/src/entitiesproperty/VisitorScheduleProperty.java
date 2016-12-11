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
 * @author Rolandis
 */
public class VisitorScheduleProperty {
    
    private final IntegerProperty visSchId = new SimpleIntegerProperty(this, "visSchId");
    private final StringProperty visitorsVisId = new SimpleStringProperty(this, "visitorsVisId");
    private final IntegerProperty customersCuId = new SimpleIntegerProperty(this, "customersCuId");
    private final StringProperty visitStartDate = new SimpleStringProperty(this, "visitStartDate");
    private final StringProperty visitStartTime = new SimpleStringProperty(this, "visitStartTime");
    private final StringProperty visitEndTime = new SimpleStringProperty(this, "visitEndTime");
    private final StringProperty visRepetitionCircle = new SimpleStringProperty(this, "visRepetitionCircle");
    private final StringProperty visitHash = new SimpleStringProperty(this, "visitHash");
    
   public IntegerProperty visSchIdProperty() {
        return visSchId;
    }

    public final Integer getVisSchId() {
        return visSchIdProperty().get();
    }

    public final void setVisSchId(Integer visSchId) {
        visSchIdProperty().set(visSchId);
    }

    public StringProperty visitorsVisIdProperty() {
        return visitorsVisId;
    }
    
    public final String getVisitorsVisId() {
        return visitorsVisIdProperty().get();
    }
    
    public final void setVisitorsVisId(String visitorsVisId) {
        visitorsVisIdProperty().set(visitorsVisId);
    }
    public IntegerProperty customersCuIdProperty() {
        return customersCuId;
    }

    public final Integer getCustomersCuId() {
        return customersCuIdProperty().get();
    }

    public final void setCustomersCuId(Integer customersCuId) {
        customersCuIdProperty().set(customersCuId);
    }

    public StringProperty visitStartDateProperty() {
        return visitStartDate;
    }
    
    public final String getVisitStartDate() {
        return visitStartDateProperty().get();
    }
    
    public final void setVisitStartDate(String visitStartDate) {
        visitStartDateProperty().set(visitStartDate);
    }
    
    public StringProperty visitStartTimeProperty() {
        return visitStartTime;
    }
    
    public final String getVisitStartTime() {
        return visitStartTimeProperty().get();
    }
    
    public final void setVisitStartTime(String visitStartTime) {
        visitStartTimeProperty().set(visitStartTime);
    }
    
    public StringProperty visitEndTimeProperty() {
        return visitEndTime;
    }
    
    public final String getVisitEndTime() {
        return visitEndTimeProperty().get();
    }
    
    public final void setVisitEndTime(String visitEndTime) {
        visitEndTimeProperty().set(visitEndTime);
    }
    
    public StringProperty visRepetitionCircleProperty() {
        return visRepetitionCircle;
    }
    
    public final String getVisRepetitionCircle() {
        return visRepetitionCircleProperty().get();
    }
    
    public final void setVisRepetitionCircle(String visRepetitionCircle) {
        visRepetitionCircleProperty().set(visRepetitionCircle);
    }
    public StringProperty visitHashProperty() {
        return visitHash;
    }
    
    public final String getVisitHash() {
        return visitHashProperty().get();
    }
    
    public final void setVisitHash(String visitHash) {
        visitHashProperty().set(visitHash);
    }
    
    public VisitorScheduleProperty() {
    }

    public VisitorScheduleProperty(Integer visSchId, String visitorsVisId, Integer customersCuId, String visitStartDate,
    String visitStartTime, String visitEndTime, String visRepetitionCircle, String visitHash) {

        setVisSchId(visSchId);
        setVisitorsVisId(visitorsVisId);
        setCustomersCuId(customersCuId);
        setVisitStartDate(visitStartDate);
        setVisitStartTime(visitStartTime);
        setVisitEndTime(visitEndTime);
        setVisRepetitionCircle(visRepetitionCircle);
        setVisitHash(visitHash);
    }
    
}
