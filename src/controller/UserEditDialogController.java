package controller;

import domain.Role;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserEditDialogController {
    private User user;
    private boolean okClicked = false;

    @FXML
    private Label errorLabel;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ChoiceBox<Role> roleChoiceBox;
    @FXML
    private ObservableList<Role> roles = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        errorLabel.setText("");
        roles.addAll(Role.USER, Role.DRIVER, Role.DISPATCHER, Role.ADMINISTRATOR);
        roleChoiceBox.setItems(roles);
        roleChoiceBox.setValue(roles.get(0));
    }

    public void save(ActionEvent actionEvent) {
        if (isInputValid()) {
            if (user == null) {
                user = new User();
            }
            user.setLogin(loginTextField.getText());
            user.setPassword(passwordTextField.getText());
            user.setRole(roleChoiceBox.getValue());

            okClicked = true;
            cancel(actionEvent);
            System.out.println(user);
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
        if (loginTextField.getText() == null || loginTextField.getText().length() == 0) {
            message += "No valid login!\n";
        }
        if (passwordTextField.getText() == null || passwordTextField.getText().length() == 0) {
            message += "No valid password!\n";
        }
        errorLabel.setText(message);
        return errorLabel.getText().length() == 0;
    }

    public void setUser(User editedUser) {
        user = editedUser;
        loginTextField.setText(user.getLogin());
        passwordTextField.setText(user.getPassword());
        roleChoiceBox.setValue(user.getRole());
    }

    public User getUser() {
        return user;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
