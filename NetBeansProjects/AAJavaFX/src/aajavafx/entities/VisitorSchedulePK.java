/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx.entities;

/**
 *
 * @author Rolandas
 */
public class VisitorSchedulePK {
    
    
    private int visSchId;
    
    private String visitorsVisId;
    
    private int customersCuId;
    
    
    public VisitorSchedulePK() {
    }

    public VisitorSchedulePK(int visSchId, String visitorsVisId, int customersCuId) {
        this.visSchId = visSchId;
        this.visitorsVisId = visitorsVisId;
        this.customersCuId = customersCuId;
    }

    public int getVisSchId() {
        return visSchId;
    }

    public void setVisSchId(int visSchId) {
        this.visSchId = visSchId;
    }

    public String getVisitorsVisId() {
        return visitorsVisId;
    }

    public void setVisitorsVisId(String visitorsVisId) {
        this.visitorsVisId = visitorsVisId;
    }

    public int getCustomersCuId() {
        return customersCuId;
    }

    public void setCustomersCuId(int customersCuId) {
        this.customersCuId = customersCuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) visSchId;
        //hash += (int) visitorsVisId;
        hash += (int) customersCuId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitorSchedulePK)) {
            return false;
        }
        VisitorSchedulePK other = (VisitorSchedulePK) object;
        if (this.visSchId != other.visSchId) {
            return false;
        }
        if (this.visitorsVisId != other.visitorsVisId) {
            return false;
        }
        if (this.customersCuId != other.customersCuId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.VisitorSchedulePK[ visSchId=" + visSchId + ", visitorsVisId=" + visitorsVisId + ", customersCuId=" + customersCuId + " ]";
    }
    
}
