package lk.ijse.CarHire.service.custom.impl;

import lk.ijse.CarHire.dao.DaoFactory;
import lk.ijse.CarHire.dao.custom.UserDao;
import lk.ijse.CarHire.dto.UserDto;
import lk.ijse.CarHire.entity.UserEntity;
import lk.ijse.CarHire.service.custom.UserService;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.management.Query;
import java.awt.*;

public class UserServiceImpl implements UserService {

    public TextField txtid;

    UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.USER);
    @Override
    public String saveUser(UserDto userDto) throws Exception{
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getName());
        userEntity.setMobileno(userDto.getMobileno());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        return String.valueOf(userDao.save(userEntity));
    }

}