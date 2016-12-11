/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx.entities;

import java.util.Date;

/**
 *
 * @author Suraj
 */
public class CustomersTakesMedicines {
    
    protected CustomersTakesMedicinesPK customersTakesMedicinesPK;
    
    private String medDosage;
    
    private String medStartDate;
    
    private double medicationintakeschedule;
    
    private Customers customers;
    
    private Medicines medicines;


public CustomersTakesMedicines() {
    }

    public CustomersTakesMedicines(CustomersTakesMedicinesPK customersTakesMedicinesPK) {
        this.customersTakesMedicinesPK = customersTakesMedicinesPK;
    }

    public CustomersTakesMedicines(CustomersTakesMedicinesPK customersTakesMedicinesPK, String medDosage, String medStartDate, double medicationintakeschedule) {
        this.customersTakesMedicinesPK = customersTakesMedicinesPK;
        this.medDosage = medDosage;
        this.medStartDate = medStartDate;
        this.medicationintakeschedule = medicationintakeschedule;
    }

    public CustomersTakesMedicines(int customersId, int medicinsId) {
        this.customersTakesMedicinesPK = new CustomersTakesMedicinesPK(customersId, medicinsId);
    }

    public CustomersTakesMedicinesPK getCustomersTakesMedicinesPK() {
        return customersTakesMedicinesPK;
    }

    public void setCustomersTakesMedicinesPK(CustomersTakesMedicinesPK customersTakesMedicinesPK) {
        this.customersTakesMedicinesPK = customersTakesMedicinesPK;
    }

    public String getMedDosage() {
        return medDosage;
    }

    public void setMedDosage(String medDosage) {
        this.medDosage = medDosage;
    }

    public String getMedStartDate() {
        return medStartDate;
    }

    public void setMedStartDate(String medStartDate) {
        this.medStartDate = medStartDate;
    }

   public double getMedicationintakeschedule() {
        return medicationintakeschedule;
    }

    public void setMedicationintakeschedule(double medicationintakeschedule) {
        this.medicationintakeschedule = medicationintakeschedule;
    }
    
    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Medicines getMedicines() {
        return medicines;
    }

    public void setMedicines(Medicines medicines) {
        this.medicines = medicines;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customersTakesMedicinesPK != null ? customersTakesMedicinesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomersTakesMedicines)) {
            return false;
        }
        CustomersTakesMedicines other = (CustomersTakesMedicines) object;
        if ((this.customersTakesMedicinesPK == null && other.customersTakesMedicinesPK != null) || (this.customersTakesMedicinesPK != null && !this.customersTakesMedicinesPK.equals(other.customersTakesMedicinesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CustomersTakesMedicines[ customersTakesMedicinesPK=" + customersTakesMedicinesPK + " ]";
    }
        
}
