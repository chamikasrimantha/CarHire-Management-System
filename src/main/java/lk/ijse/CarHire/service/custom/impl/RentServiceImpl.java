package lk.ijse.CarHire.service.custom.impl;

import lk.ijse.CarHire.dao.DaoFactory;
import lk.ijse.CarHire.dao.custom.CarDao;
import lk.ijse.CarHire.dao.custom.CustomerDao;
import lk.ijse.CarHire.dao.custom.RentDao;
import lk.ijse.CarHire.dto.CarDto;
import lk.ijse.CarHire.dto.CustomerDto;
import lk.ijse.CarHire.dto.RentDto;
import lk.ijse.CarHire.entity.CarEntity;
import lk.ijse.CarHire.entity.CustomerEntity;
import lk.ijse.CarHire.entity.RentEntity;
import lk.ijse.CarHire.service.custom.RentService;

import java.util.List;

public class RentServiceImpl implements RentService {

    CarDao carDao = (CarDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR);
    CustomerDao customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CUSTOMER);
    RentDao rentDao = (RentDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.RENT);

    @Override
    public String saveRent(RentDto rentDto) {
        RentEntity rentEntity = new RentEntity();
        rentEntity.setId(rentDto.getId());
        rentEntity.setFromDate(rentDto.getFromDate());
        rentEntity.setToDate(rentDto.getToDate());
        rentEntity.setIsReturn(rentDto.getIsReturn());
        rentEntity.setPerDayRent(rentDto.getPerDayRent());
        rentEntity.setTotal(rentDto.getTotal());

        CarEntity carEntity = carDao.findById(rentDto.getCarDto().getId());
        rentEntity.setCarEntity(carEntity);

        CustomerEntity customerEntity = customerDao.findById(rentDto.getCustomerDto().getId());
        rentEntity.setCustomerEntity(customerEntity);

        return String.valueOf(rentDao.save(rentEntity));
    }

    @Override
    public String updateRent(RentDto rentDto) {
        RentEntity rentEntity = new RentEntity();
        rentEntity.setId(rentDto.getId());
        rentEntity.setFromDate(rentDto.getFromDate());
        rentEntity.setToDate(rentDto.getToDate());
        rentEntity.setIsReturn(rentDto.getIsReturn());
        rentEntity.setPerDayRent(rentDto.getPerDayRent());
        rentEntity.setTotal(rentDto.getTotal());

        CarEntity carEntity = carDao.findById(rentDto.getCarDto().getId());
        rentEntity.setCarEntity(carEntity);

        CustomerEntity customerEntity = customerDao.findById(rentDto.getCustomerDto().getId());
        rentEntity.setCustomerEntity(customerEntity);

        return String.valueOf(rentDao.update(rentEntity));
    }

    @Override
    public String deleteRent(RentDto rentDto) {
        try {
            RentEntity rentEntity = rentDao.findById(rentDto.getId());

            if (rentEntity != null) {
                boolean isDeleted = rentDao.delete(rentEntity);

                if (isDeleted) {
                    return "SUCCESS";
                } else {
                    return "FAILURE";
                }
            } else {
                return "NOT_FOUND";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}