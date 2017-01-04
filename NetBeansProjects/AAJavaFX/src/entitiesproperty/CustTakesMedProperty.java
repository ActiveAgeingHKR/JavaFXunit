/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesproperty;

import aajavafx.entities.Customers;
import aajavafx.entities.Medicines;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Suraj
 */
public class CustTakesMedProperty {
    private IntegerProperty cuId = new SimpleIntegerProperty(this, "cuId");
    private StringProperty cuFirstname = new SimpleStringProperty(this, "cuFirstName");
    private StringProperty cuLastname = new SimpleStringProperty(this, "cuLastname");
    
    private StringProperty medId = new SimpleStringProperty(this, "medId");
    private StringProperty medName = new SimpleStringProperty(this, "medName");
    private StringProperty medVolume = new SimpleStringProperty(this, "volume");
    private StringProperty medMeasurementUnit = new SimpleStringProperty(this, "medMeasurementUnit");

    private StringProperty medDosage = new SimpleStringProperty(this, "medDosage");
    private StringProperty medStartDate = new SimpleStringProperty(this, "cuLastname");
    private DoubleProperty medicationintakeschedule = new SimpleDoubleProperty(this, "cuBirthdate");  //shall we keep birthday as string "1934-05-06" or declare as integer

    /**
     * @return the cuId
     */
    public IntegerProperty getCuId() {
        return cuId;
    }

    /**
     * @param cuId the cuId to set
     */
    public void setCuId(IntegerProperty cuId) {
        this.cuId = cuId;
    }

    /**
     * @return the cuFirstname
     */
    public StringProperty getCuFirstname() {
        return cuFirstname;
    }

    /**
     * @param cuFirstname the cuFirstname to set
     */
    public void setCuFirstname(StringProperty cuFirstname) {
        this.cuFirstname = cuFirstname;
    }

    /**
     * @return the cuLastname
     */
    public StringProperty getCuLastname() {
        return cuLastname;
    }

    /**
     * @param cuLastname the cuLastname to set
     */
    public void setCuLastname(StringProperty cuLastname) {
        this.cuLastname = cuLastname;
    }

    /**
     * @return the medId
     */
    public StringProperty getMedId() {
        return medId;
    }

    /**
     * @param medId the medId to set
     */
    public void setMedId(StringProperty medId) {
        this.medId = medId;
    }

    /**
     * @return the medName
     */
    public StringProperty getMedName() {
        return medName;
    }

    /**
     * @param medName the medName to set
     */
    public void setMedName(StringProperty medName) {
        this.medName = medName;
    }

    /**
     * @return the medVolume
     */
    public StringProperty getMedVolume() {
        return medVolume;
    }

    /**
     * @param medVolume the medVolume to set
     */
    public void setMedVolume(StringProperty medVolume) {
        this.medVolume = medVolume;
    }

    /**
     * @return the medMeasurementUnit
     */
    public StringProperty getMedMeasurementUnit() {
        return medMeasurementUnit;
    }

    /**
     * @param medMeasurementUnit the medMeasurementUnit to set
     */
    public void setMedMeasurementUnit(StringProperty medMeasurementUnit) {
        this.medMeasurementUnit = medMeasurementUnit;
    }

    /**
     * @return the medDosage
     */
    public StringProperty getMedDosage() {
        return medDosage;
    }

    /**
     * @param medDosage the medDosage to set
     */
    public void setMedDosage(StringProperty medDosage) {
        this.medDosage = medDosage;
    }

    /**
     * @return the medStartDate
     */
    public StringProperty getMedStartDate() {
        return medStartDate;
    }

    /**
     * @param medStartDate the medStartDate to set
     */
    public void setMedStartDate(StringProperty medStartDate) {
        this.medStartDate = medStartDate;
    }

    /**
     * @return the medicationintakeschedule
     */
    public DoubleProperty getMedicationintakeschedule() {
        return medicationintakeschedule;
    }

    /**
     * @param medicationintakeschedule the medicationintakeschedule to set
     */
    public void setMedicationintakeschedule(DoubleProperty medicationintakeschedule) {
        this.medicationintakeschedule = medicationintakeschedule;
    }
    
    
}
