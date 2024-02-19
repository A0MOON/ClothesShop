package study.clothesshop.domain;

public enum OrderStatus {
    PAID, // 결제 완료
    SHIPPED, //배송 중
    DELIVERED, // 배송 완료
    CANCELED // 주문 취소됨
    ;

    public void setOrder(Order order) {

    }
}
