package lk.ijse.CarHire.dao.custom.impl;

import lk.ijse.CarHire.dao.custom.CarDao;
import lk.ijse.CarHire.entity.CarCategoryEntity;
import lk.ijse.CarHire.entity.CarEntity;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class CarDaoImpl implements CarDao {
    @Override
    public List<CarEntity> getAll() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM car");
            nativeQuery.addEntity(CarEntity.class);
            List<CarEntity> carEntities = nativeQuery.list();
            transaction.commit();;
            return carEntities;
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(CarEntity entity) {
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
    public boolean update(CarEntity entity) {
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
            Query query = session.createQuery("SELECT id FROM car ORDER BY id DESC");
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
    public boolean delete(CarEntity entity) {
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
    public CarEntity search(Integer id) {
        Transaction transaction=null;
        CarEntity carEntity = null;
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()){
            transaction = session.beginTransaction();
            carEntity = session.get(CarEntity.class,id);
            transaction.commit();
        }catch (Exception e){
            if (transaction!=null){transaction.rollback();
            }
        }
        return carEntity;
    }

    @Override
    public CarEntity findById(Integer customerId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            return session.get(CarEntity.class, customerId);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return null;
        }
    }
}
