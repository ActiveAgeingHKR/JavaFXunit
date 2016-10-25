/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Iuliu
 */
public class Schedule1Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private DatePicker pickADate;
    @FXML
    Button calendar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add some action (in Java 8 lambda syntax style).
         pickADate.setValue(LocalDate.now());
         pickADate.setOnAction(new EventHandler() {
     public void handle(Event t) {
         LocalDate date = pickADate.getValue();
         System.err.println("Selected date: " + date);
     }

            
 });
         String date = pickADate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
         System.out.println("Date now: "+date);
      
    }

    @FXML
    public void handleDatePickerCalenderAction(ActionEvent event) {
        System.out.println("Bou");

    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        //labelError.setText(null);
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 879, 599);
            stage.setScene(scene);

            stage.setTitle("Main menu");
            stage.show();

        } catch (Exception ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    
}
