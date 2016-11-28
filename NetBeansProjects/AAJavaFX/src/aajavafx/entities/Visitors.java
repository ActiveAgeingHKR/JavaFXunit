/*
 You can comment here
 */
package aajavafx.entities;

import java.io.Serializable;

/**
 *
 * @author Rolandas
 */
public class Visitors implements Serializable {
    private static final long serialVersionUID = 1L;
    private String visId;
    private String visFirstname;
    private String visLastname;
    private String visEmail;
    private String visPhone;
    private Company companyCompId;
               
    public Visitors() {
    }

    public Visitors(String visId) {
        this.visId = visId;
    }

    public Visitors(String visId, String visFirstname, String visLastname, String visEmail, String visPhone, Company companyId) {
        this.visId = visId;
        this.visFirstname = visFirstname;
        this.visLastname = visLastname;
        this.visEmail = visEmail;
        this.visPhone = visPhone;
        this.companyCompId = companyId;
    }

    public String getVisId() {
        return visId;
    }

    public void setVisId(String visId) {
        this.visId = visId;
    }

    public String getVisFirstname() {
        return visFirstname;
    }

    public void setVisFirstname(String visFirstname) {
        this.visFirstname = visFirstname;
    }

    public String getVisLastname() {
        return visLastname;
    }

    public void setVisLastname(String visLastname) {
        this.visLastname = visLastname;
    }

    public String getVisEmail() {
        return visEmail;
    }

    public void setVisEmail(String visEmail) {
        this.visEmail = visEmail;
    }

    public String getVisPhone() {
        return visPhone;
    }

    public void setVisPhone(String visPhone) {
        this.visPhone = visPhone;
    }

    public Company getCompanyCompId() {
        return companyCompId;
    }

    public void setCompanyCompId(Company companyCompId) {
        this.companyCompId = companyCompId;
    }
   
}
