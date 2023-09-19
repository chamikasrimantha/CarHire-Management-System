package lk.ijse.CarHire.dao.custom.impl;

import lk.ijse.CarHire.dao.custom.CustomerDao;
import lk.ijse.CarHire.entity.CustomerEntity;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public List<CustomerEntity> getAll() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM customer");
            nativeQuery.addEntity(CustomerEntity.class);
            List<CustomerEntity> customerEntities = nativeQuery.list();
            transaction.commit();;
            return customerEntities;
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(CustomerEntity entity) {
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
    public boolean update(CustomerEntity entity) {
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
            Query query = session.createQuery("SELECT id FROM customer ORDER BY id DESC");
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
    public boolean delete(CustomerEntity entity) {
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
    public CustomerEntity search(Integer id) {
        Transaction transaction=null;
        CustomerEntity customerEntity = null;
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()){
            transaction = session.beginTransaction();
            customerEntity = session.get(CustomerEntity.class,id);
            transaction.commit();
        }catch (Exception e){
            if (transaction!=null){transaction.rollback();
            }
        }
        return customerEntity;
    }

    @Override
    public CustomerEntity findById(Integer customerId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            return session.get(CustomerEntity.class, customerId);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return null;
        }
    }
}