package com.example.demo1.Utils;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    /***
     * Shows alert message to gui.
     * @param owner the stage
     * @param type the type of the alert
     * @param header the header of the alert message
     * @param text the text of the alert message
     */
    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }
}