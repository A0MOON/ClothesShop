package study.clothesshop.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Notice {
    // 1.
    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;
    private String noticeTitle;
    private String notice;

    // 2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
