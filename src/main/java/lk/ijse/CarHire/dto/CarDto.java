package lk.ijse.CarHire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDto {
    private Integer id;
    private String brand;
    private String model;
    private String vehicleNo;
    private String year;
    private Double pricePerDay;
    private List<RentDto> rentDtoList = new ArrayList<>();
    private CarCategoryDto carCategoryDto;
}