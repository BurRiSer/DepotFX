package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {
    @FXML
    private UsersTabController usersTabController;
    @FXML
    private OrdersTabController ordersTabController;
    @FXML
    private DriversTabController driversTabController;
    @FXML
    private VehiclesTabController vehiclesTabController;

    @FXML
    public void initialize() {
        usersTabController.injectMainController(this);
        System.out.println("Hello");
    }

    public void close(ActionEvent actionEvent) {
        System.out.println("Close");
    }
}
