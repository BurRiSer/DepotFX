package controller;

import domain.Role;
import domain.User;
import factory.FactoryException;
import factory.ServiceFactory;
import factory.ServiceFactoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pool.ConnectionPool;
import pool.PoolException;
import service.ServiceException;
import service.UserService;

import java.io.IOException;
import java.util.Optional;

public class UsersTabController extends Controller {
    private UserService userService;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Long> idColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, Role> roleColumn;

    @FXML
    private Button userListButton;
    @FXML
    private Button userEditButton;
    @FXML
    private Button userDeleteButton;
    @FXML
    private Button userCreateButton;

    private ObservableList<User> users = FXCollections.observableArrayList();

    private MainController mainController;

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() throws FactoryException, ServiceException {
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Long>("Id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<User, Role>("role"));
        System.out.println("Users Tab");

        try {
            ConnectionPool.getInstance().init("com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost/mydb?useUnicode=true&characterEncoding=UTF-8",
                    "root", "mysql");
            System.out.println("init successful");
        } catch (PoolException e) {
            e.printStackTrace();
        }

        ServiceFactory factory = new ServiceFactoryImpl();
        setServiceFactory(factory);

        userService = getServiceFactory().getUserService();

        users.addAll(userService.findAll());
        usersTable.setItems(users);
    }

    /*вывести список в tableview*/
    public void list(ActionEvent actionEvent) {
        /*временное решение*/
        users.removeAll(users);

        try {
            users.addAll(userService.findAll());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        usersTable.setItems(users);
    }

    public void edit(ActionEvent actionEvent) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            System.out.println("Start edit " + user.getLogin());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/UserEditDialog.fxml"));
                Parent parent = loader.load();

                UserEditDialogController dialogController = loader.getController();
                dialogController.setUser(user);

                dialogCreator(parent, "Edit User");

                if (dialogController.isOkClicked()) {
                    userService.save(dialogController.getUser());
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
        User user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            try {
                if (userService.canDelete(user.getId())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Do you really want to delete '" + user.getLogin() + "'?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        //удаление
                        userService.delete(user.getId());
                        list(actionEvent);
                        System.out.println("Deleted!");
                    }
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Sorry, can't delete '" + user.getLogin() + "'.");
                    errorAlert.showAndWait();
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/UserEditDialog.fxml"));
            Parent parent = loader.load();

            UserEditDialogController dialogController = loader.getController();

            dialogCreator(parent, "Add new User");

            if (dialogController.isOkClicked()) {
                userService.save(dialogController.getUser());
                list(actionEvent);
                System.out.println(dialogController.getUser().getLogin() + " created!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void dialogCreator(Parent parent, String title) {
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(parent);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();
    }
}
