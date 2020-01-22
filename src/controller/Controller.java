package controller;

import factory.ServiceFactory;

abstract public class Controller {
    private ServiceFactory serviceFactory;

    public final ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public final void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
