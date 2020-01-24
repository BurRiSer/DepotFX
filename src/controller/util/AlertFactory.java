package controller.util;

import javafx.scene.control.Alert;

public class AlertFactory {
    public Alert getConfirmationAlert(String object){
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Do you really want to delete '" + object + "'?");
        return confirmationAlert;
    }
    public Alert getErrorAlert(String object){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Sorry, can't delete '" + object + "'.");
        return errorAlert;
    }
    public Alert getAboutAlert(){
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About DepotFX");
        aboutAlert.setHeaderText("DepotFX App \n Version 1.2");
        aboutAlert.setContentText("© 2020 Sergey Bury \n burik.razor@gmail.com");
        return aboutAlert;
    }
    public Alert getInfoAlert(String message){
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText(message);
        return infoAlert;
    }
}
