package lk.ijse.CarHire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarCategoryDto {
    private Integer id;
    private String name;
    private List<CarDto> carDtoList = new ArrayList<>();
}