package study.clothesshop.domain;
public enum RewardStatus {
    AVAILABLE, // 사용가능 적립금
    USED,      // 사용된 적립금
    UNAVAILABLE, // 미가용 적립금
    REFUND_PENDING // 환불예정 적립금
}
