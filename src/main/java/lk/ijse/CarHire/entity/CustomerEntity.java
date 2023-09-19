package lk.ijse.CarHire.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustID", length = 6, nullable = false)
    private Integer id;

    @Column(name = "CustNIC", length = 12, nullable = false)
    private Long nic;

    @Column(name = "CustName",nullable = false)
    private String name;

    @Column(name = "CustAddress", nullable = false)
    private String address;

    @Column(name = "CustMobile", length = 10, nullable = false)
    private Integer mobileno;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    @OneToMany(mappedBy = "customerEntity", targetEntity = RentEntity.class)
    private List<RentEntity> rentEntityList;
}