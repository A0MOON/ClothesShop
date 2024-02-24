package study.clothesshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Notice {
    // 1.
    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;
    private String noticeTitle;
    private String noticeDate;
    private String noticeWriter;
    private String noticeContents;

    // 2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
