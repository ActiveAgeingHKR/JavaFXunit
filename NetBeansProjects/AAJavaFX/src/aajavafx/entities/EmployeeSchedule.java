/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx.entities;

import java.io.Serializable;



public class EmployeeSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer schId;
    private String schDate;
    private String schFromTime;
    private String schUntilTime;
    private boolean emplVisitedCust;
    private Customers customersCuId;
    private Employees employeesEmpId;
            

    public EmployeeSchedule() {
    }

    public EmployeeSchedule(Integer schId) {
        this.schId = schId;
    }

    public EmployeeSchedule(Integer schId, String schDate, String schFromTime, String schUntilTime,
            boolean emplVisitedCust,Customers customersCuId,Employees employeesEmpId) {
        this.schId = schId;
        this.schDate = schDate;
        this.schFromTime = schFromTime;
        this.schUntilTime = schUntilTime;
        this.emplVisitedCust = emplVisitedCust;
        this.customersCuId=customersCuId;
        this.employeesEmpId=employeesEmpId;
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public String getSchDate() {
        return schDate;
    }

    public void setSchDate(String schDate) {
        this.schDate = schDate;
    }

    public String getSchFromTime() {
        return schFromTime;
    }

    public void setSchFromTime(String schFromTime) {
        this.schFromTime = schFromTime;
    }

    public String getSchUntilTime() {
        return schUntilTime;
    }

    public void setSchUntilTime(String schUntilTime) {
        this.schUntilTime = schUntilTime;
    }

    public boolean getEmplVisitedCust() {
        return emplVisitedCust;
    }

    public void setEmplVisitedCust(boolean emplVisitedCust) {
        this.emplVisitedCust = emplVisitedCust;
    }
    
    public Customers getCustomersCuId() {
        return customersCuId;
    }

    public void setCustomersCuId(Customers customersCuId) {
        this.customersCuId = customersCuId;
    }

    public Employees getEmployeesEmpId() {
        return employeesEmpId;
    }

    public void setEmployeesEmpId(Employees employeesEmpId) {
        this.employeesEmpId = employeesEmpId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schId != null ? schId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeSchedule)) {
            return false;
        }
        EmployeeSchedule other = (EmployeeSchedule) object;
        if ((this.schId == null && other.schId != null) || (this.schId != null && !this.schId.equals(other.schId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EmployeeSchedule[ schId=" + schId + " ]";
    }
    
}
