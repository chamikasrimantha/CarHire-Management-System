package lk.ijse.CarHire.dao.custom;

import lk.ijse.CarHire.dao.CrudDao;
import lk.ijse.CarHire.entity.CarCategoryEntity;

import java.util.List;

public interface CarCategoryDao extends CrudDao<CarCategoryEntity> {
    List<String> loadCarCategoryIds();
}