package lk.ijse.CarHire.service.custom.impl;

import lk.ijse.CarHire.dao.DaoFactory;
import lk.ijse.CarHire.dao.custom.CarCategoryDao;
import lk.ijse.CarHire.dao.custom.CarDao;
import lk.ijse.CarHire.dto.CarCategoryDto;
import lk.ijse.CarHire.dto.CarDto;
import lk.ijse.CarHire.entity.CarCategoryEntity;
import lk.ijse.CarHire.entity.CarEntity;
import lk.ijse.CarHire.service.custom.CarService;

import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {

    CarCategoryDao carCategoryDao = (CarCategoryDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR_CATEGORY);
    CarDao carDao = (CarDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR);

    @Override
    public String saveCar(CarDto carDto) {
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carDto.getId());
        carEntity.setBrand(carDto.getBrand());
        carEntity.setModel(carDto.getModel());
        carEntity.setYear(carDto.getYear());
        carEntity.setVehicleNo(carDto.getVehicleNo());
        carEntity.setPricePerDay(carDto.getPricePerDay());

        CarCategoryEntity carCategoryEntity = carCategoryDao.findById(carDto.getCarCategoryDto().getId());
        carEntity.setCarCategoryEntity(carCategoryEntity);
        return String.valueOf(carDao.save(carEntity));
    }

    @Override
    public String updateCar(CarDto carDto) {
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carDto.getId());
        carEntity.setBrand(carDto.getBrand());
        carEntity.setModel(carDto.getModel());
        carEntity.setYear(carDto.getYear());
        carEntity.setVehicleNo(carDto.getVehicleNo());
        carEntity.setPricePerDay(carDto.getPricePerDay());

        CarCategoryEntity carCategoryEntity = new CarCategoryEntity();
        carCategoryEntity.setId(carDto.getCarCategoryDto().getId());
        carCategoryEntity.setName(carDto.getCarCategoryDto().getName());
        carEntity.setCarCategoryEntity(carCategoryEntity);
        return String.valueOf(carDao.update(carEntity));
    }

    @Override
    public String deleteCar(CarDto carDto) {
        try {
            CarEntity carEntity = carDao.findById(carDto.getId());

            if (carEntity != null) {
                boolean isDeleted = carDao.delete(carEntity);

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

    @Override
    public List getAllCars() {
        List<CarEntity> carEntities = carDao.getAll();
        List<CarDto> carDtos = new ArrayList<>();
        for (CarEntity carEntity : carEntities){
            CarDto carDto = new CarDto();
            carDto.setId(carEntity.getId());
            carDto.setBrand(carEntity.getBrand());
            carDto.setModel(carEntity.getModel());
            carDto.setYear(carEntity.getYear());
            carDto.setPricePerDay(carEntity.getPricePerDay());
            carDto.setVehicleNo(carEntity.getVehicleNo());
            carDtos.add(carDto);
        }
        return carDtos;
    }

    @Override
    public CarDto searchCar(String id) {
        CarEntity carEntity = carDao.search(Integer.valueOf(id));
        CarDto carDto = new CarDto();
        carDto.setId(carEntity.getId());
        carDto.setBrand(carEntity.getBrand());
        carDto.setModel(carEntity.getModel());
        carDto.setVehicleNo(carEntity.getVehicleNo());
        carDto.setYear(carEntity.getYear());
        carDto.setPricePerDay(carEntity.getPricePerDay());
        return carDto;
    }
}