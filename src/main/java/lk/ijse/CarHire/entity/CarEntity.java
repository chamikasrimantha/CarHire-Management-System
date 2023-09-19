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
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarID", length = 6, nullable = false)
    private Integer id;

    @Column(name = "CarBrand", length = 20, nullable = false)
    private String brand;

    @Column(name = "CarModel", length = 20, nullable = false)
    private String model;

    @Column(name = "CarNo", length = 15, nullable = false)
    private String vehicleNo;

    @Column(name = "CarYear", length = 30, nullable = false)
    private String year;

    @Column(name = "CarPricePerDay", length = 100, nullable = false)
    private Double pricePerDay;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    @OneToMany(mappedBy = "carEntity", targetEntity = RentEntity.class)
    private List<RentEntity> rentEntities;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CarCategoryID", nullable = false)
    private CarCategoryEntity carCategoryEntity;
}