/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx.entities;

import javafx.beans.property.StringProperty;

/**
 *
 * @author Iuliu
 */
public abstract class Person {

    private Integer empId;

    private String empFirstname;

   private String empLastname;

    public Person() {
    }

    public Person(Integer id, String lastname, String firstname) {
        this.setEmpId(id);
        this.setEmpLastname(lastname);
        this.setEmpFirstname(firstname);

    }

   public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpFirstname() {
        return empFirstname;
    }

    public void setEmpFirstname(String empFirstname) {
        this.empFirstname = empFirstname;
    }

    public String getEmpLastname() {
        return empLastname;
    }

    public void setEmpLastname(String empLastname) {
        this.empLastname = empLastname;
    }
}
