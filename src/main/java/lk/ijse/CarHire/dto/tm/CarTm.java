package lk.ijse.CarHire.dto.tm;

import lk.ijse.CarHire.dto.CarCategoryDto;
import lk.ijse.CarHire.dto.RentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarTm {
    private Integer id;
    private String brand;
    private String model;
    private String vehicleNo;
    private String year;
    private Double pricePerDay;
}