package controller;

import controller.util.AlertFactory;
import controller.util.DialogFactory;
import factory.ServiceFactory;

abstract public class Controller {
    private ServiceFactory serviceFactory;
    private DialogFactory dialogFactory;
    private AlertFactory alertFactory;

    public final ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public final void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public DialogFactory getDialogFactory() {
        return dialogFactory;
    }

    public void setDialogFactory(DialogFactory dialogFactory) {
        this.dialogFactory = dialogFactory;
    }

    public AlertFactory getAlertFactory() {
        return alertFactory;
    }

    public void setAlertFactory(AlertFactory alertFactory) {
        this.alertFactory = alertFactory;
    }
}
