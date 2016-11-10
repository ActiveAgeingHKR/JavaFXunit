/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author lokeshdhakal
 */
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cuID;

    private String cuFirstname;

    private String cuLastname;

    private String cuBirthdate;
    private String cuAddress;

    private String cuPersonnummer;

    public Customers() {
    }

    public Customers(Integer cuId, String cuFirstname, String cuLastname,  String cuAddress, String cuBirthdate,String cuPersonnummer) {
     
        
        this.cuID = cuID;
        this.cuFirstname = cuFirstname;
        this.cuLastname = cuLastname;
        this.cuBirthdate = cuBirthdate;
        this.cuAddress = cuAddress;
        this.cuPersonnummer = cuPersonnummer;
    }

    public String getCuID() {
        return cuID;
    }

    public void setCuID(String cuID) {
        this.cuID = cuID;
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
}
