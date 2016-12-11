/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author lokeshdhakal
 */
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cuId;

    private String cuFirstname;

    private String cuLastname;

    private String cuBirthdate;
    private String cuAddress;

    private String cuPersonnummer;

    public Customers() {
    }
public Customers(Integer cuId) {
        this.cuId = cuId;
    }
    public Customers(Integer cuId, String cuFirstname, String cuLastname,  String cuAddress, String cuBirthdate,String cuPersonnummer) {
     
        
        this.cuId = cuId;
        this.cuFirstname = cuFirstname;
        this.cuLastname = cuLastname;
        this.cuBirthdate = cuBirthdate;
        this.cuAddress = cuAddress;
        this.cuPersonnummer = cuPersonnummer;
    }

    public int getCuId() {
        return cuId;
    }

    public void setCuID(int cuId) {
        this.cuId = cuId;
    }

    public String getCuFirstname() {
        return cuFirstname;
    }
    
    public void setCuFirstname(String cuFirstname) {
        this.cuFirstname = cuFirstname;
    }
    
    public String getCuLastname() {
        return cuLastname;
    }

    public void setCuLastname(String cuLastname) {
        this.cuLastname = cuLastname;
    }

    public String getCuBirthdate() {
        return cuBirthdate;
    }

    public void setCuBirthdate(String cuBirthdate) {
        this.cuBirthdate = cuBirthdate;
    }

    public String getCuAddress() {
        return cuAddress;
    }

    public void setCuAdress(String cuAddress) {
        this.cuAddress = cuAddress;
    }

    public String getCuPersonnummer() {
        return cuPersonnummer;
    }

    public void setCuPersonnummer(String cuPersonnummer) {
        this.cuPersonnummer = cuPersonnummer;
    }
    //set what we want to get back when calling toString on a customer object (eg. for displaying in drop down menus)
    
    @Override
    public String toString() {
        return cuId +". " + cuFirstname + " " + cuLastname + " " + cuPersonnummer;
    }
}
