package study.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.clothesshop.domain.Item;
import study.clothesshop.loginweb.OrderForm;
import study.clothesshop.service.ItemService;

@Controller
@RequiredArgsConstructor
public class OrderController {

    @GetMapping("/users/{userid}/item/{itemid}/order")
    public String showOrderForm(@PathVariable String userid, @PathVariable String itemid, Model model) {
        // 여기서는 간단히 주문 폼을 보여주기 위해 사용자 ID와 상품 ID를 모델에 추가합니다.
        model.addAttribute("userid", userid);
        model.addAttribute("itemid", itemid);
        return "order_form"; // 주문 작성 페이지로 이동
    }

    @PostMapping("/users/{userid}/item/{itemid}/order/confirm")
    public String confirmOrder(@PathVariable String userid, @PathVariable String itemid, @ModelAttribute OrderForm orderForm) {
        // 주문 확인 로직을 수행합니다. 여기서는 간단히 주문 정보를 출력합니다.
        System.out.println("User ID: " + userid);
        System.out.println("Item ID: " + itemid);
        System.out.println("Quantity: " + orderForm.getQuantity());
        System.out.println("Option: " + orderForm.getOption());
        System.out.println("Address: " + orderForm.getAddress());
        // 주문 확인 후 다른 페이지로 리다이렉트 또는 주문 내역을 보여주는 페이지로 이동할 수 있습니다.
        return "redirect:/"; // 홈 페이지로 리다이렉트
    }


}

