/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import java.io.Serializable;
import java.util.Date;



public class EmployeeSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
  
    
    private Integer schId;
   
    
    private Date schDate;
   
    
    private Date schFromTime;
  
    
    private Date schUntilTime;
    private Integer employeeId;
    private Integer customerId;
    
    private boolean emplVisitedCust;

    public EmployeeSchedule() {
    }

    public EmployeeSchedule(Integer schId) {
        this.schId = schId;
    }

    public EmployeeSchedule(Integer schId,Integer employeeId,Integer customerId, Date schDate, Date schFromTime, Date schUntilTime, boolean emplVisitedCust) {
        this.schId = schId;
        this.employeeId=employeeId;
        this.customerId=customerId;     
        this.schDate = schDate;
        this.schFromTime = schFromTime;
        this.schUntilTime = schUntilTime;
        this.emplVisitedCust = emplVisitedCust;
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public Date getSchDate() {
        return schDate;
    }

    public void setSchDate(Date schDate) {
        this.schDate = schDate;
    }

    public Date getSchFromTime() {
        return schFromTime;
    }

    public void setSchFromTime(Date schFromTime) {
        this.schFromTime = schFromTime;
    }

    public Date getSchUntilTime() {
        return schUntilTime;
    }

    public void setSchUntilTime(Date schUntilTime) {
        this.schUntilTime = schUntilTime;
    }

    public boolean getEmplVisitedCust() {
        return emplVisitedCust;
    }

    public void setEmplVisitedCust(boolean emplVisitedCust) {
        this.emplVisitedCust = emplVisitedCust;
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

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    
}
