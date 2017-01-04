/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesproperty;

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
public abstract class PersonProperty {
    
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
    public StringProperty firstNProperty() {
        return firstName;
    }

    public final String getFirstName() {
        return firstNProperty().get();
    }

    public final void setFirstName(String firstName) {
        firstNProperty().set(firstName);
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

    public PersonProperty() {
    }

   public PersonProperty(Integer id,String lastname, String firstname) {
        setId(id);
        setLastName(lastname);
        setFirstName(firstname);
            
    }
   
}

