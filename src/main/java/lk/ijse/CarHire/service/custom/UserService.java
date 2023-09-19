package lk.ijse.CarHire.service.custom;

import lk.ijse.CarHire.dto.UserDto;
import lk.ijse.CarHire.service.SuperService;

public interface UserService extends SuperService {
    String saveUser(UserDto userDto) throws Exception;
}