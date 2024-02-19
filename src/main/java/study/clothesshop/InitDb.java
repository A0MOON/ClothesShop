package study.clothesshop;
import org.springframework.beans.factory.annotation.Autowired;
import study.clothesshop.domain.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.service.MemberService;

import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final MemberService memberService;

        public void dbInit1() {
            Member member = createMember("userA", "유저에이", "1", "1111", "test1@example.com");
            memberService.join(member);
            // Book book1 = createBook("JPA1 BOOK", 10000, 100);
            Item item1 = createItem("top", "this is ", 20000, 20, 2000);
            // em.persist(book1);
            em.persist(item1);
            // Book book2 = createBook("JPA2 BOOK", 20000, 100);
            Item item2 = createItem("bottom", "it is", 30000, 30, 3000);
            // em.persist(book2);
            em.persist(item2);
            // OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
     /*       OrderItem orderItem1 = OrderItem.createOrderItem(20000, 1, false, item1);
            OrderItem orderItem2 = OrderItem.createOrderItem(30000, 2, false, item2);*/
            // OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
            // Order order = Order.createOrder(member, createDelivery(member),

            //         orderItem1, orderItem2);
            //Order order = Order.createOrder(member, createDelivery(member), orderItem1);

            /*List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);
            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItems);
            em.persist(order);*/

            // em.persist(order);
        }



        public void dbInit2() {
            Member member = createMember("userB", "유저비", "2", "2222", "test2@example.com");
            memberService.join(member);
            // Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            // em.persist(book1);
            // Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            // em.persist(book2);
            // Delivery delivery = createDelivery(member);
            // OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            // OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
            // Order order = Order.createOrder(member, delivery, orderItem1,
            //         orderItem2);
            // em.persist(order);
        }

        private Member createMember(String loginId, String name, String password,
                                    String phone, String email) {
            Member member = new Member();
            member.setLoginId(loginId);
            member.setName(name);
            member.setPassword(password);
            member.setPhone(phone);
            member.setEmail(email);
            return member;
        }

        // private Book createBook(String name, int price, int stockQuantity) {
        //     Book book = new Book();
        //     book.setName(name);
        //     book.setPrice(price);
        //     book.setStockQuantity(stockQuantity);
        //     return book;
        // }
        //
        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
           return delivery;
        }

        private Item createItem(String name, String description, int price, int stockQuantity, int discountAmount) {
            Item item = new Item();
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setStockQuantity(stockQuantity);
            item.setDiscountAmount(discountAmount);
            return item;
        }

    }
}
