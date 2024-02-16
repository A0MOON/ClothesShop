package study.clothesshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    private String name;
    private int price;
    private int stockQuantity;
    private String description;

}
