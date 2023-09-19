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
@Table(name = "rent")
public class RentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentID", length = 6, nullable = false)
    private Integer id;

    @Column(name = "fromDate", columnDefinition = "date")
    private Date fromDate;

    @Column(name = "toDate", columnDefinition = "date")
    private Date toDate;

    @Column(name = "perDayRent", nullable = false)
    private Double perDayRent;

//    @Column(name = "advancePayment", nullable = false)
//    private Double advancePayment;

//    @Column(name = "refundableDeposit", nullable = false)
//    private Double refundableDeposit;

//    @Column(name = "balance", nullable = false)
//    private Double balance;

    @Column(name = "isReturn", nullable = false)
    private String isReturn;

    @Column(name = "total", nullable = false)
    private Double total;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CustID", nullable = false)
    private CustomerEntity customerEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CarID", nullable = false)
    private CarEntity carEntity;
}