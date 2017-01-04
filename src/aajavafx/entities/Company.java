/*
 You can comment here
 */
package aajavafx.entities;

import java.io.Serializable;

/**
 *
 * @author Rolandas
 */
public class Company implements Serializable {
    private int compId;
    private String compName;
    
    public Company() {
    }

    public Company(Integer compId) {
        this.compId = compId;
    }

    public Company(Integer compId, String compName) {
        this.compId = compId;
        this.compName = compName;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }
}
