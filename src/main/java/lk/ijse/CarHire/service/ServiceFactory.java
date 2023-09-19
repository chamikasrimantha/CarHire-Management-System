package lk.ijse.CarHire.service;

import lk.ijse.CarHire.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory(){

    }
    public static ServiceFactory getInstance(){
        return serviceFactory == null ? serviceFactory = new ServiceFactory() : serviceFactory;
    }
    public enum ServiceTypes {
        USER, CUSTOMER, CAR, CAR_CATEGORY, RENT
    }
    public SuperService getService(ServiceTypes serviceTypes){
        switch (serviceTypes){
            case USER:
                return new UserServiceImpl();
            case CUSTOMER:
                return new CustomerServiceImpl();
            case CAR:
                return new CarServiceImpl();
            case CAR_CATEGORY:
                return new CarCategoryServiceImpl();
            case RENT:
                return new RentServiceImpl();
            default:
                return null;
        }
    }
}