package lk.ijse.CarHire.dao;

import lk.ijse.CarHire.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){

    }
    public static DaoFactory getInstance(){
        return daoFactory == null? daoFactory = new DaoFactory() : daoFactory;
    }
    public enum DaoTypes{
        USER, CUSTOMER, CAR, CAR_CATEGORY, RENT
    }
    public SuperDao getDao(DaoTypes daoTypes){
        switch (daoTypes){
            case USER:
                return new UserDaoImpl();
            case CUSTOMER:
                return new CustomerDaoImpl();
            case CAR:
                return new CarDaoImpl();
            case CAR_CATEGORY:
                return new CarCategoryDaoImpl();
            case RENT:
                return new RentDaoImpl();
            default:
                return null;
        }
    }
}