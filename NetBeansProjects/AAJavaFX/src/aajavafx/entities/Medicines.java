/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx.entities;

import java.io.Serializable;

/**
 *
 * @author Suraj
 */
public class Medicines implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer medId;
    private String medName;
    private Integer volume;
    private String medMeasurementUnit;
    private Customers customersCuId;
    
    public Medicines() {
    }
    
    public Medicines (Integer medId){
        this.medId = medId;
    }

    public Medicines(Integer medId, String medName, Integer volume, String medMeasurementUnit, Customers customersCuID) {
        this.medId = medId;
        this.medName = medName;
        this.volume = volume;
        this.medMeasurementUnit = medMeasurementUnit;
        this.customersCuId = customersCuID;
    }

    public Integer getmedId() {
        return medId;
    }

    
    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }
     public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getMedMeasurementUnit() {
        return medMeasurementUnit;
    }

    public void setMedMeasurementUnit(String medMeasurementUnit) {
        this.medMeasurementUnit = medMeasurementUnit;
    }
    
    public Customers getCustomersCuID() {
        return customersCuId;
    }

    public void setCustomersCuID(Customers cuID) {
        this.customersCuId = cuID;
    }
    
    @Override
    public String toString() {
        return medName + " " + volume + " " + medMeasurementUnit;
    }
    
}
