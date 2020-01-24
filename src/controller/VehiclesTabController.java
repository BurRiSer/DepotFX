package controller;

import controller.util.AlertFactory;
import controller.util.DialogFactory;
import domain.Vehicle;
import domain.VehicleType;
import factory.FactoryException;
import factory.ServiceFactoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ServiceException;
import service.VehicleService;

import java.io.IOException;
import java.util.Optional;

public class VehiclesTabController extends Controller {
    private VehicleService vehicleService;

    @FXML
    private TableColumn<Vehicle, Long> idColumn;
    @FXML
    private TableColumn<Vehicle, String> modelColumn;
    @FXML
    private TableColumn<Vehicle, Boolean> serviceabilityColumn;
    @FXML
    private TableColumn<Vehicle, VehicleType> typeColumn;
    @FXML
    private TableView<Vehicle> vehiclesTable;

    private ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Long>("id"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("model"));
        serviceabilityColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Boolean>("serviceability"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, VehicleType>("type"));
        try {
            //init user service
            setServiceFactory(new ServiceFactoryImpl());
            vehicleService = getServiceFactory().getVehicleService();
            //init table list
            vehicles.addAll(vehicleService.findAll());
            vehiclesTable.setItems(vehicles);
        } catch (FactoryException | ServiceException e) {
            e.printStackTrace();
        }
        setDialogFactory(new DialogFactory());
        setAlertFactory(new AlertFactory());
    }

    public void list(ActionEvent actionEvent) {
        vehicles.removeAll(vehicles);

        try {
            vehicles.addAll(vehicleService.findAll());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        vehiclesTable.setItems(vehicles);
    }

    public void edit(ActionEvent actionEvent) {
        Vehicle vehicle = vehiclesTable.getSelectionModel().getSelectedItem();
        if (vehicle != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/VehicleEditDialog.fxml"));
                Parent parent = loader.load();

                VehicleEditDialogController dialogController = loader.getController();
                dialogController.setVehicle(vehicle);

                Dialog dialog = getDialogFactory().getDialog(parent, "Edit Vehicle");
                dialog.showAndWait();

                if (dialogController.isOkClicked()) {
                    vehicleService.save(dialogController.getVehicle());
                    list(actionEvent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(ActionEvent actionEvent) {
        Vehicle vehicle = vehiclesTable.getSelectionModel().getSelectedItem();
        if (vehicle != null) {
            try {
                if (vehicleService.canDelete(vehicle.getId())) {
                    Alert alert = getAlertFactory().getConfirmationAlert(vehicle.getModel());
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        //удаление
                        vehicleService.delete(vehicle.getId());
                        list(actionEvent);
                    }
                } else {
                    Alert errorAlert = getAlertFactory().getErrorAlert(vehicle.getModel());
                    errorAlert.showAndWait();
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/VehicleEditDialog.fxml"));
            Parent parent = loader.load();
            VehicleEditDialogController dialogController = loader.getController();

            Dialog dialog = getDialogFactory().getDialog(parent, "Add new Vehicle");
            dialog.showAndWait();

            if (dialogController.isOkClicked()) {
                vehicleService.save(dialogController.getVehicle());
                list(actionEvent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
