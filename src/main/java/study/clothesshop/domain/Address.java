package study.clothesshop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String province;
    private String city;
    private String town;
    private String village;
    private String lotNumber;
    private String buildingName;
    private String detailAddress;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;


    protected Address() {
    }

 /*   public Address(String city, String street, String zipcode) {
        this.city = city;

    }*/
}
