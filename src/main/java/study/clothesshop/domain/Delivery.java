package study.clothesshop.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter //@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // deliveryStatus를 staatus로 바꿨떠니 됨 왜?????????

    @OneToOne(mappedBy = "delivery")
    private Order order;


    public void setAddress(Address address) {
    }

    public void setStatus(DeliveryStatus deliveryStatus) {
    }
}
