
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



public class Managers implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer manId;
  
    private String manFirstname;
   
    private String manLastname;
   
    private String manUsername;
   
    private String manPassword;
   
    private String manEmail;
   
    private String manPhone;
   // @OneToMany(cascade = CascadeType.ALL, mappedBy = "managersManId")
  //  private Collection<Employees> employeesCollection;

    public Managers() {
    }

    public Managers(Integer manId) {
        this.manId = manId;
    }

  /*  public Managers(Integer manId, String manFirstname, String manLastname, String manUsername, String manPassword, String manEmail, String manPhone) {
        this.manId = manId;
        this.manFirstname = manFirstname;
        this.manLastname = manLastname;
        this.manUsername = manUsername;
        this.manPassword = manPassword;
        this.manEmail = manEmail;
        this.manPhone = manPhone;
    }*/
   // public Managers(Integer manID){
        
   // }

    public Integer getManId() {
        return manId;
    }

    public void setManId(Integer manId) {
        this.manId = manId;
    }

    public String getManFirstname() {
        return manFirstname;
    }

    public void setManFirstname(String manFirstname) {
        this.manFirstname = manFirstname;
    }

    public String getManLastname() {
        return manLastname;
    }

    public void setManLastname(String manLastname) {
        this.manLastname = manLastname;
    }

    public String getManUsername() {
        return manUsername;
    }

    public void setManUsername(String manUsername) {
        this.manUsername = manUsername;
    }

    public String getManPassword() {
        return manPassword;
    }

    public void setManPassword(String manPassword) {
        this.manPassword = manPassword;
    }

    public String getManEmail() {
        return manEmail;
    }

    public void setManEmail(String manEmail) {
        this.manEmail = manEmail;
    }

    public String getManPhone() {
        return manPhone;
    }

    public void setManPhone(String manPhone) {
        this.manPhone = manPhone;
    }

 /*   @XmlTransient
    public Collection<Employees> getEmployeesCollection() {
        return employeesCollection;
    }

    public void setEmployeesCollection(Collection<Employees> employeesCollection) {
        this.employeesCollection = employeesCollection;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manId != null ? manId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Managers)) {
            return false;
        }
        Managers other = (Managers) object;
        if ((this.manId == null && other.manId != null) || (this.manId != null && !this.manId.equals(other.manId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Managers[ manId=" + manId + " ]";
    }
    
}
