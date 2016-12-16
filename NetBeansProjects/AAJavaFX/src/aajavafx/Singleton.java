/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import entitiesproperty.CustomerProperty;
import entitiesproperty.EmployeeScheduleProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Iuliu
 */
public class Singleton {

    private static Singleton instance = null;
private ObservableList<CustomerProperty> list;
    protected Singleton() {
        // Exists only to defeat instantiation.
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    /**
     * @return the list
     */
    public ObservableList<CustomerProperty>getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ObservableList<CustomerProperty> list) {
        this.list = list;
    }
}
