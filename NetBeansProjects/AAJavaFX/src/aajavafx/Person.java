/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

/**
 *
 * @author Iuliu
 */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Iuliu
 */
public abstract class Person {
    
    private final IntegerProperty id =new SimpleIntegerProperty (this, "id");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
  
   public IntegerProperty idProperty(){
   return id;
   }
   
   public final int getId(){
   return idProperty().get();
   }
   
   public final void setId(int id){
   idProperty().set(id);
   }
    public StringProperty firstProperty() {
        return firstName;
    }

    public final String getFirstName() {
        return firstProperty().get();
    }

    public final void setFirstName(String firstName) {
        firstProperty().set(firstName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public final String getName() {
        return lastNameProperty().get();
    }

    public final void setLastName(String lastName) {
        lastNameProperty().set(lastName);
    }

    public Person() {
    }

   public Person(int id,String lastname, String firstname) {
        setId(id);
        setLastName(lastname);
        setFirstName(firstname);
            
    }
   
}

