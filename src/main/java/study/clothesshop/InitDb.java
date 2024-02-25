package study.clothesshop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Delivery;
import study.clothesshop.domain.Item;
import study.clothesshop.domain.Member;
import study.clothesshop.service.MemberService;


@Component
public class InitDb {
    private final InitService initService;

    @Autowired
    public InitDb(InitService initService) {
        this.initService = initService;
    }

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    static class InitService {
        private final EntityManager em;
        private final MemberService memberService;

        public InitService(EntityManager em, MemberService memberService) {
            this.em = em;
            this.memberService = memberService;
        }

        public void dbInit() {
            Member memberA = createMember("userA", "유저에이", "1", "1111", "test1@example.com");
            memberService.join(memberA);
            Member memberB = createMember("userB", "유저비", "2", "2222", "test2@example.com");
            memberService.join(memberB);

            Item item1 = createItem("Heart Cross bag (4 Colors)", "This is a heart cross bag available in 4 different colors", 128000, 10, 0);
            em.persist(item1);
            Item item2 = createItem("Teddy Slit Top (3 Colors)", "This is a teddy slit top available in 3 different colors", 44000, 20, 0);
            em.persist(item2);
            Item item3 = createItem("Soft Boucle Muffler", "This is a soft boucle muffler", 29000, 15, 0);
            em.persist(item3);
            Item item4 = createItem("Blang Shoulder Bag (2 Colors)", "This is a blang shoulder bag available in 2 different colors", 29000, 10, 0);
            em.persist(item4);
            Item item5 = createItem("Teddy Fur Jacket (3 Colors)", "This is a teddy fur jacket available in 3 different colors", 117000, 5, 0);
            em.persist(item5);
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
