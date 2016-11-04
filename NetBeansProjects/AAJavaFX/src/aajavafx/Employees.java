/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import java.io.Serializable;

public class Employees extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String empUsername;

    private String empPassword;

    private String empEmail;

    private String empPhone;
    private Integer managersID;

    private boolean empRegistered;

    public Employees() {
    }

    public Employees(Integer empId, String empFirstname, String empLastname, String empUsername, String empPassword, String empEmail, String empPhone, Integer managersID, boolean empRegistered) {
     
        super(empId,empFirstname,empLastname);
        this.empUsername = empUsername;
        this.empPassword = empPassword;
        this.empEmail = empEmail;
        this.empPhone = empPhone;
        this.managersID = managersID;
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
}
