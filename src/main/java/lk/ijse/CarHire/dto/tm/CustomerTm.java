package lk.ijse.CarHire.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerTm {
    private Integer id;
    private Long nic;
    private String name;
    private String address;
    private Integer mobileno;
}