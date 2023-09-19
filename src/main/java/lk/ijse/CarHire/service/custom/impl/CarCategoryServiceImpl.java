package lk.ijse.CarHire.service.custom.impl;

import lk.ijse.CarHire.dao.DaoFactory;
import lk.ijse.CarHire.dao.custom.CarCategoryDao;
import lk.ijse.CarHire.dto.CarCategoryDto;
import lk.ijse.CarHire.entity.CarCategoryEntity;
import lk.ijse.CarHire.service.custom.CarCategoryService;

import java.util.ArrayList;
import java.util.List;

public class CarCategoryServiceImpl implements CarCategoryService {

    CarCategoryDao carCategoryDao = (CarCategoryDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR_CATEGORY);
    @Override
    public String saveCarCategory(CarCategoryDto carCategoryDto) {
        CarCategoryEntity carCategoryEntity = new CarCategoryEntity();
        carCategoryEntity.setId(carCategoryDto.getId());
        carCategoryEntity.setName(carCategoryDto.getName());
        return String.valueOf(carCategoryDao.save(carCategoryEntity));
    }

    @Override
    public String updateCarCategory(CarCategoryDto carCategoryDto) {
        try {
            CarCategoryEntity entityToUpdate = carCategoryDao.findById(carCategoryDto.getId());
            if (entityToUpdate!=null){
                entityToUpdate.setName(carCategoryDto.getName());
                boolean updated = carCategoryDao.update(entityToUpdate);
                if (updated) {
                    return "CarCategory updated successfully.";
                } else {
                    return "Failed to update CarCategory.";
                }
            } else {
                return "CarCategory not found.";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "An error occurred while updating CarCategory.";
        }
    }

    @Override
    public String deleteCarCategory(CarCategoryDto carCategoryDto) {
        try {
            CarCategoryEntity carCategoryEntity = carCategoryDao.findById(carCategoryDto.getId());
            if (carCategoryEntity != null) {
                boolean isDeleted = carCategoryDao.delete(carCategoryEntity);

                if (isDeleted) {
                    return "SUCCESS";
                } else {
                    return "FAILURE";
                }
            } else {
                return "NOT_FOUND";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }

    @Override
    public List getAllCarCategories() {
        List<CarCategoryEntity> carCategoryEntities = carCategoryDao.getAll();
        List<CarCategoryDto> carCategoryDtos = new ArrayList<>();
        for (CarCategoryEntity carCategoryEntity : carCategoryEntities){
            CarCategoryDto carCategoryDto = new CarCategoryDto();
            carCategoryDto.setId(carCategoryEntity.getId());
            carCategoryDto.setName(carCategoryEntity.getName());
            carCategoryDtos.add(carCategoryDto);
        }
        return carCategoryDtos;
    }

    @Override
    public CarCategoryDto searchCarCategory(String id) {
        CarCategoryEntity carCategoryEntity = carCategoryDao.search(Integer.valueOf(id));
        CarCategoryDto carCategoryDto = new CarCategoryDto();
        carCategoryDto.setId(carCategoryEntity.getId());
        carCategoryDto.setName(carCategoryEntity.getName());
        return carCategoryDto;
    }
}