package study.clothesshop.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    // 1.
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private int parentId;
    private String categoryName;

    // 3.
    @OneToMany(mappedBy = "category")
    List<Item> item = new ArrayList<>();
}
