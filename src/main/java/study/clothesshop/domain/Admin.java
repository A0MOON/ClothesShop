package study.clothesshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Admin {
    // 1,
    @Id
    @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    private String adminName;

    // 3.
    @OneToMany(mappedBy = "admin")
    private List<AdminItem> adminItem = new ArrayList<>();

    @OneToMany(mappedBy = "admin")
    private List<Notice> notice = new ArrayList<>();
}
