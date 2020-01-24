package controller.util;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class DialogFactory {
    public Dialog getDialog(Parent parent, String title) {
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(parent);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        return dialog;
    }

}
