package lk.ijse.CarHire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentDto {
    private Integer id;
    private Date fromDate;
    private Date toDate;
    private Double perDayRent;
//    private Double advancePayment;
//    private Double refundableDeposit;
//    private Double balance;
    private String isReturn;
    private Double total;
    private CustomerDto customerDto;
    private CarDto carDto;
}