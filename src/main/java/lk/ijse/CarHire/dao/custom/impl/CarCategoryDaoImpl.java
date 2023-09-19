package lk.ijse.CarHire.dao.custom.impl;

import lk.ijse.CarHire.dao.custom.CarCategoryDao;
import lk.ijse.CarHire.entity.CarCategoryEntity;
import lk.ijse.CarHire.entity.CustomerEntity;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class CarCategoryDaoImpl implements CarCategoryDao {
    @Override
    public List<CarCategoryEntity> getAll() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM car_category");
            nativeQuery.addEntity(CarCategoryEntity.class);
            List<CarCategoryEntity> carCategoryEntities = nativeQuery.list();
            transaction.commit();;
            return carCategoryEntities;
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(CarCategoryEntity entity) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(entity);
            transaction.commit();
            return true;
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(CarCategoryEntity entity) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(entity);
            transaction.commit();
            return true;
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public String generateNewId() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            Query query = session.createQuery("SELECT id FROM car_category ORDER BY id DESC");
            query.setMaxResults(1);
            List results = query.list();
            transaction.commit();
            return (results.size()==0) ? null : (String) results.get(0);
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return "0";
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(CarCategoryEntity entity) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.delete(entity);
            transaction.commit();
            return true;
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public CarCategoryEntity search(Integer id) {
        Transaction transaction=null;
        CarCategoryEntity carCategoryEntity = null;
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()){
            transaction = session.beginTransaction();
            carCategoryEntity = session.get(CarCategoryEntity.class,id);
            transaction.commit();
        }catch (Exception e){
            if (transaction!=null){transaction.rollback();
            }
        }
        return carCategoryEntity;
    }

    @Override
    public CarCategoryEntity findById(Integer customerId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            return session.get(CarCategoryEntity.class, customerId);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return null;
        }
    }

    @Override
    public List<String> loadCarCategoryIds() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("SELECT id FROM car_category ORDER BY id ASC");
            List<String> carCategoryIds = (List<String>) query.list();
            return carCategoryIds;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if (session!=null){
                session.close();
            }
        }
    }
}