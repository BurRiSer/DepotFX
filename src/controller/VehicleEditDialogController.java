package controller;

import domain.Vehicle;
import domain.VehicleType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class VehicleEditDialogController {
    private Vehicle vehicle;
    private boolean okClicked = false;
    @FXML
    private TextField modelTextField;
    @FXML
    private CheckBox serviceabilityCheckBox;
    @FXML
    private ChoiceBox<VehicleType> typeChoiceBox;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;
    private ObservableList<VehicleType> types = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        errorLabel.setText("");
        types.addAll(VehicleType.SEDAN, VehicleType.WAGON, VehicleType.MINIVAN, VehicleType.MINIBUS);
        typeChoiceBox.setItems(types);
        typeChoiceBox.setValue(types.get(0));
        serviceabilityCheckBox.setSelected(true);
    }

    public void setVehicle(Vehicle storedVehicle) {
        vehicle = storedVehicle;
        modelTextField.setText(vehicle.getModel());
        serviceabilityCheckBox.setSelected(vehicle.getServiceability());
        typeChoiceBox.setValue(vehicle.getType());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void save(ActionEvent actionEvent) {
        if (isInputValid()) {
            if (vehicle == null) {
                vehicle = new Vehicle();
            }
            vehicle.setModel(modelTextField.getText());
            vehicle.setServiceability(serviceabilityCheckBox.isSelected());
            vehicle.setType(typeChoiceBox.getValue());

            okClicked = true;
            cancel(actionEvent);
        } else {
            errorLabel.setVisible(true);
        }
    }

    public void cancel(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean isInputValid() {
        String message = "";
        if (modelTextField.getText() == null || modelTextField.getText().length() == 0) {
            message += "No valid model!\n";
        }
        errorLabel.setText(message);
        return errorLabel.getText().length() == 0;
    }
}
