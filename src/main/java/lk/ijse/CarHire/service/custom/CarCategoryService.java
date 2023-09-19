package lk.ijse.CarHire.service.custom;

import lk.ijse.CarHire.dto.CarCategoryDto;
import lk.ijse.CarHire.dto.CustomerDto;
import lk.ijse.CarHire.service.SuperService;

import java.util.List;

public interface CarCategoryService<T> extends SuperService {
    String saveCarCategory(CarCategoryDto carCategoryDto);
    String updateCarCategory(CarCategoryDto carCategoryDto);
    String deleteCarCategory(CarCategoryDto carCategoryDto);
    List<T> getAllCarCategories();
    public T searchCarCategory(String id);
}