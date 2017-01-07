/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class MainPageTabController implements Initializable {
    
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane tabPane;
    //list of tab names to create
    private String[] tabNames = {"Home", "Customers", "CustomersMedicines", "Devices", "Employees", 
        "EmpSched", "Medicines", "Visitors", "VisitSched"};
    //corresponding list of fxml views to populate them with (obviously must be same size as tab name list)
    private String[] fileNames = {"MainPage.fxml", "Customer.fxml", "CustomerTakesMedicine.fxml", "Devices.fxml", "Employee.fxml", 
        "Schedule1.fxml","Medicines.fxml", "Visitor.fxml", "VisitorSchedule.fxml"};
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
        //set up empty tabs
        for (String city : tabNames) {
            tabPane.getTabs().add(new Tab(city));
        }
        
        tabPane.getSelectionModel().clearSelection();
        //this change listener listens for tab selections and initializes the content for that tab
        tabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                System.out.println("Tab Selected: "+t1.getText());
                    try {
                        // Loading content on demand
                        int x = 0;
                        for(int i=0; i<tabNames.length; i++) {
                            if(tabNames[i].equalsIgnoreCase(t1.getText())) {
                                x = i;
                                break;
                            }
                        }
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileNames[x]));
                        Parent root = (Parent) loader.load();
                        t1.setContent(root);
                       
                        
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                        
                }
            }
        );
        
        
        tabPane.getSelectionModel().selectFirst();
        
    }   
    
    
    
}
