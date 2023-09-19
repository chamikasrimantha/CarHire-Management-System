package lk.ijse.CarHire.dao.custom;

import lk.ijse.CarHire.dao.CrudDao;
import lk.ijse.CarHire.entity.UserEntity;

public interface UserDao extends CrudDao<UserEntity> {
    boolean valid(String username, String password);
}