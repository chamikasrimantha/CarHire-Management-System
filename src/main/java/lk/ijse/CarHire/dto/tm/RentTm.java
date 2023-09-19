package lk.ijse.CarHire.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentTm {
    private Integer id;
    private Date fromDate;
    private Date toDate;
    private Double perDayRent;
//    private Double advancePayment;
//    private Double refundableDeposit;
//    private Double balance;
    private String isReturn;
    private Double total;
}