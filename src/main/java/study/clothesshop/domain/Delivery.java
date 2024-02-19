package study.clothesshop.domain;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

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





   /* public static Delivery createDelivery(Member member) {
        return new Delivery(member);
    }

    public Delivery(Member member) {
    }

*/

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setStatus(DeliveryStatus status) {

        this.status = status;
    }

}
