package lk.ijse.CarHire.dao.custom.impl;

import lk.ijse.CarHire.dao.custom.UserDao;
import lk.ijse.CarHire.entity.UserEntity;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public List<UserEntity> getAll() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM user");
            nativeQuery.addEntity(UserEntity.class);
            List<UserEntity> userEntities = nativeQuery.list();
            transaction.commit();;
            return userEntities;
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(UserEntity entity) {
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
    public boolean update(UserEntity entity) {
        return false;
    }

    @Override
    public String generateNewId() {
        return null;
    }

    @Override
    public boolean delete(UserEntity entity) {
        return false;
    }

    @Override
    public UserEntity search(Integer id) {
        return null;
    }

    @Override
    public UserEntity findById(Integer customerId) {
        return null;
    }

    @Override
    public boolean valid(String username, String password) {
        Transaction transaction = null;

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // Query to check if a user with the provided username and password exists
            Query<UserEntity> query = session.createQuery(
                    "FROM UserEntity WHERE username = :username AND password = :password",
                    UserEntity.class
            );
            query.setParameter("username", username);
            query.setParameter("password", password);

            UserEntity user = query.uniqueResult();

            transaction.commit();

            // If a user was found, return true; otherwise, return false
            return user != null;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}