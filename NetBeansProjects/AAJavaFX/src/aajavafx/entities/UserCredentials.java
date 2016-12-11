/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx.entities;

import java.io.Serializable;

/**
 *
 * @author Chris
 */
public class UserCredentials implements Serializable {
    
    private String encUsername;
    private String encPassword;
    
    public UserCredentials(String encUsername, String encPassword) {
        this.encUsername = encUsername;
        this.encPassword = encPassword;
    }
    
    public String getUsername() {
        return encUsername;
    }
    
    public String getPassword() {
        return encPassword;
    }
    
}
