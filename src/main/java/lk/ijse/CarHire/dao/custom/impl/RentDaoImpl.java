package lk.ijse.CarHire.dao.custom.impl;

import lk.ijse.CarHire.dao.custom.RentDao;
import lk.ijse.CarHire.entity.CarEntity;
import lk.ijse.CarHire.entity.RentEntity;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class RentDaoImpl implements RentDao {
    @Override
    public List<RentEntity> getAll() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM rent");
            nativeQuery.addEntity(CarEntity.class);
            List<RentEntity> rentEntities = nativeQuery.list();
            transaction.commit();;
            return rentEntities;
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(RentEntity entity) {
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
    public boolean update(RentEntity entity) {
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
        return null;
    }

    @Override
    public boolean delete(RentEntity entity) {
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
    public RentEntity search(Integer id) {
        Transaction transaction=null;
        RentEntity rentEntity = null;
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()){
            transaction = session.beginTransaction();
            rentEntity = session.get(RentEntity.class,id);
            transaction.commit();
        }catch (Exception e){
            if (transaction!=null){transaction.rollback();
            }
        }
        return rentEntity;
    }

    @Override
    public RentEntity findById(Integer customerId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            return session.get(RentEntity.class, customerId);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return null;
        }
    }
}