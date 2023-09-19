package lk.ijse.CarHire.dto;

import lombok.*;

import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
public class UserDto {
    private String name;
    private String email;
    private Integer mobileno;
    private String username;
    private String password;
}