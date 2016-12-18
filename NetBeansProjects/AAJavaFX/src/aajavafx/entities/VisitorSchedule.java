/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx.entities;


public class VisitorSchedule {
     
    protected VisitorSchedulePK visitorSchedulePK;
   
    private String visitStartDate;
    
    private String visitStartTime;
    
    private String visitEndTime;
    
    private String visRepetitionCircle;
    
    private String visitHash;
   
    private Visitors visitors;
    
    private Customers customers;
    
    
     public VisitorSchedule() {
    }

    public VisitorSchedule(VisitorSchedulePK visitorSchedulePK) {
        this.visitorSchedulePK = visitorSchedulePK;
    }

    public VisitorSchedule(VisitorSchedulePK visitorSchedulePK, String visitStartDate, String visitStartTime, String visitEndTime, String visRepetitionCircle, String visitHash) {
        this.visitorSchedulePK = visitorSchedulePK;
        this.visitStartDate = visitStartDate;
        this.visitStartTime = visitStartTime;
        this.visitEndTime = visitEndTime;
        this.visRepetitionCircle = visRepetitionCircle;
        this.visitHash = visitHash;
    }

    public VisitorSchedule(int visSchId, String visitorsVisId, int customersCuId) {
        this.visitorSchedulePK = new VisitorSchedulePK(visSchId, visitorsVisId, customersCuId);
    }

    public VisitorSchedulePK getVisitorSchedulePK() {
        return visitorSchedulePK;
    }

    public void setVisitorSchedulePK(VisitorSchedulePK visitorSchedulePK) {
        this.visitorSchedulePK = visitorSchedulePK;
    }

    public String getVisitStartDate() {
        return visitStartDate;
    }

    public void setVisitStartDate(String visitStartDate) {
        this.visitStartDate = visitStartDate;
    }

    public String getVisitStartTime() {
        return visitStartTime;
    }

    public void setVisitStartTime(String visitStartTime) {
        this.visitStartTime = visitStartTime;
    }

    public String getVisitEndTime() {
        return visitEndTime;
    }

    public void setVisitEndTime(String visitEndTime) {
        this.visitEndTime = visitEndTime;
    }

    public String getVisRepetitionCircle() {
        return visRepetitionCircle;
    }

    public void setVisRepetitionCircle(String visRepetitionCircle) {
        this.visRepetitionCircle = visRepetitionCircle;
    }

    public String getVisitHash() {
        return visitHash;
    }

    public void setVisitHash(String visitHash) {
        this.visitHash = visitHash;
    }

    public Visitors getVisitors() {
        return visitors;
    }

    public void setVisitors(Visitors visitors) {
        this.visitors = visitors;
    }
    
    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitorSchedulePK != null ? visitorSchedulePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitorSchedule)) {
            return false;
        }
        VisitorSchedule other = (VisitorSchedule) object;
        if ((this.visitorSchedulePK == null && other.visitorSchedulePK != null) || (this.visitorSchedulePK != null && !this.visitorSchedulePK.equals(other.visitorSchedulePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.VisitorSchedule[ visitorSchedulePK=" + visitorSchedulePK + " ]";
    }
    
}
