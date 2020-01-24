package controller;

import controller.util.AlertFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pool.ConnectionPool;
import pool.PoolException;

import java.sql.SQLException;

public class MainController extends Controller {

    @FXML
    public void initialize() {
        setAlertFactory(new AlertFactory());
    }

    public void testConnection(ActionEvent actionEvent) {
        String message = "Connection closed :(";
        try {
            if(!ConnectionPool.getInstance().getConnection().isClosed()) {
                message = "Connection is OK :)";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            getAlertFactory().getInfoAlert(message).showAndWait();
        }
    }

    public void about(ActionEvent actionEvent) {
        getAlertFactory().getAboutAlert().showAndWait();
    }
}
