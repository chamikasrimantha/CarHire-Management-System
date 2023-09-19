package lk.ijse.CarHire.service.custom;

import lk.ijse.CarHire.dto.CarDto;
import lk.ijse.CarHire.dto.RentDto;
import lk.ijse.CarHire.service.SuperService;

import java.util.List;

public interface RentService extends SuperService {
    String saveRent(RentDto rentDto);
    String updateRent(RentDto rentDto);
    String deleteRent(RentDto rentDto);
}