/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import java.io.Serializable;

public class Employees  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String empUsername;

    private String empPassword;

    private String empEmail;

    private String empPhone;
    private Managers managersManId;

    private Integer empId;

    private String empFirstname;

    private String empLastname;
    private boolean empRegistered;

    public Employees() {
    }

    public Employees( Integer empId,String empFirstname, String empLastname, String empUsername, String empPassword, String empEmail, String empPhone, Managers managersManId, boolean empRegistered) {

        this.empId = empId;
        this.empFirstname = empFirstname;
        this.empLastname = empLastname;
        this.empUsername = empUsername;
        this.empPassword = empPassword;
        this.empEmail = empEmail;
        this.empPhone = empPhone;
        this.managersManId = managersManId;
        this.empRegistered = empRegistered;
    }

    public String getEmpUsername() {
        return empUsername;
    }

    public void setEmpUsername(String empUsername) {
        this.empUsername = empUsername;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public boolean getEmpRegistered() {
        return empRegistered;
    }

    public void setEmpRegistered(boolean empRegistered) {
        this.empRegistered = empRegistered;
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
     public Managers getManagersManId() {
        return managersManId;
    }

    public void setManagersManId(Managers managersManId) {
        this.managersManId = managersManId;
    }
}
