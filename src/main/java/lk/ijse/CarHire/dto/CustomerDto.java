package lk.ijse.CarHire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    private Integer id;
    private Long nic;
    private String name;
    private String address;
    private Integer mobileno;
    private List<RentDto> rentDtoList = new ArrayList<>();
}