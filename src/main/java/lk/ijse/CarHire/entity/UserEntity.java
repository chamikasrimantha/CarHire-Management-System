package lk.ijse.CarHire.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserID")
    private Integer id;
    @Column(name = "UsersName",nullable = false)
    private String name;
    @Column(name = "UserEmail", length = 50, nullable = false)
    private String email;
    @Column(name = "UserMobile",length = 10,nullable = false)
    private Integer mobileno;
    @Column(name = "username", length = 15, nullable = false)
    private String username;
    @Column(name = "password", length = 15, nullable = false)
    private String password;
    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

}