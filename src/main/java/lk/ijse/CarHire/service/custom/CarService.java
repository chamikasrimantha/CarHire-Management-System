package lk.ijse.CarHire.service.custom;

import lk.ijse.CarHire.dto.CarCategoryDto;
import lk.ijse.CarHire.dto.CarDto;
import lk.ijse.CarHire.service.SuperService;

import java.util.List;

public interface CarService<T> extends SuperService {
    String saveCar(CarDto carDto);
    String updateCar(CarDto carDto);
    String deleteCar(CarDto carDto);
    List<T> getAllCars();
    public T searchCar(String id);
}