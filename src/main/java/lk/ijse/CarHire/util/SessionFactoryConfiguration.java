package lk.ijse.CarHire.util;

import lk.ijse.CarHire.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfiguration {
    private static SessionFactoryConfiguration sessionFactoryConfiguration;
    private SessionFactory sessionFactory;
    private SessionFactoryConfiguration(){
        Configuration configuration = new Configuration().configure()
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(CustomerEntity.class)
                .addAnnotatedClass(CarEntity.class)
                .addAnnotatedClass(CarCategoryEntity.class)
                .addAnnotatedClass(RentEntity.class);
        sessionFactory = configuration.buildSessionFactory();
    }
    public static SessionFactoryConfiguration getInstance(){
        return sessionFactoryConfiguration == null ?
                sessionFactoryConfiguration = new SessionFactoryConfiguration()
                : sessionFactoryConfiguration;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }
}