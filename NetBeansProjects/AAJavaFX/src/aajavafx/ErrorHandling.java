///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package aajavafx;
//
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.DialogPane;
//
///**
// *
// * @author Rolandas
// */
//public class ErrorHandling {
//    
//    // Create pop up message.
//    public void popUpMessage(String title, String errorMessage) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle(title);
//        alert.setContentText(errorMessage);
//        alert.setHeaderText(null);
//        // Dialog pane to style the pop up window.
//        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.setStyle("-fx-background-color: blueviolet;");
//        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 16px;" + "-fx-font-weight: bold;");
//        // Do something then button is pressed.
//        alert.showAndWait().ifPresent((ButtonType event) -> {
//            if (event == ButtonType.OK) {
//                // to do if needed
//            }
//        });
//    }
//}
