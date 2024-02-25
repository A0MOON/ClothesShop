package study.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.clothesshop.domain.Item;
import study.clothesshop.dto.ItemDTO;
import study.clothesshop.service.ItemService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items/all") // url
    public String ItemForm(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "items/all"; // html
    }

    // 상품 등록
    @GetMapping(value = "/items/new")
    public String createItemForm(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "shop/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String createItemsForm(@ModelAttribute ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setStockQuantity(itemDTO.getStockQuantity());
        item.setDescription(itemDTO.getDescription());
        itemService.saveItem(item);
        return "redirect:/";
    }

    // 상품 목록 조회
    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "shop/itemList";
    }

    // 상품 수정
    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Optional<Item> optionalItem = itemService.findById(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setName(item.getName());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setStockQuantity(item.getStockQuantity());
            itemDTO.setDescription(item.getDescription());
            model.addAttribute("itemDTO", itemDTO);
            return "shop/updateItemForm";
        } else {
            return "redirect:/items";
        }
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@ModelAttribute ItemDTO itemDTO, @PathVariable("itemId") Long itemId) {
        Optional<Item> optionalItem = itemService.findById(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setName(itemDTO.getName());
            item.setPrice(itemDTO.getPrice());
            item.setStockQuantity(itemDTO.getStockQuantity());
            item.setDescription(itemDTO.getDescription());
            itemService.saveItem(item);
        }
        return "redirect:/items";
    }

    // top page
    @GetMapping(value = "/items/top") // url
    public String ItemTopForm(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "items/top"; // html
    }

    // bottom page
    @GetMapping(value = "/items/bottom") // url
    public String ItemBottomForm(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "items/bottom"; // html
    }

    // accessory page
    @GetMapping(value = "/items/accessory") // url
    public String ItemAccessoryForm(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "items/accessory"; // html
    }

    @GetMapping("/users/{userid}/item/{itemid}")
    public String showProductDetails(@PathVariable String userid, @PathVariable String itemid, Model model) {
        // 여기서는 간단히 상품 정보를 모델에 추가합니다. 실제로는 상품 정보를 DB에서 가져와야 합니다.
        model.addAttribute("userid", userid);
        model.addAttribute("itemid", itemid);
        return "items/product_details"; // 상품 상세 정보 페이지로 이동
    }


}
