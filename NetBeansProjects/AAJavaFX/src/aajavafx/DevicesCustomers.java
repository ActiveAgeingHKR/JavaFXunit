/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import aajavafx.entities.Customers;
import java.io.Serializable;

/**
 *
 * @author lokeshdhakal
 */
public class DevicesCustomers implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String devId;
    private String devName;
    private Customers customersCuId;
    
    public DevicesCustomers() {
    }

    public DevicesCustomers(String devId) {
        this.devId = devId;
    }

    public DevicesCustomers(String devId, String devName, Customers customersCuID) {
        this.devId = devId;
        this.devName = devName;
        this.customersCuId = customersCuID;
    }

    
    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }
    
    public Customers getCustomersCuID() {
        return customersCuId;
    }

    public void setCustomersCuID(Customers cuID) {
        this.customersCuId = cuID;
    }
    
}
