package study.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import study.clothesshop.domain.Item;
import study.clothesshop.domain.Notice;
import study.clothesshop.dto.ItemDTO;
import study.clothesshop.service.NoticeService;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CustomerCenterController {

    private final NoticeService noticeService;

    // customercenter page
    @GetMapping(value = "/customercenter/customercenter") // url
    public String CustomerCenterForm() {
        log.info("customercenter controller");
        return "customercenter/customercenter"; // html
    }


    //q&a page
    @GetMapping(value = "/customercenter/question") // url
    public String CustomerCenterQuestionForm() {
        log.info("question controller");
        return "customercenter/question"; // html
    }

    // notice 페이지 목록 조회
    @GetMapping(value = "/customercenter/notice")
    public String CustomerCenterNoticeForm(Model model) {
        List<Notice> notices = noticeService.findNotices();
        model.addAttribute("notices", notices);
        return "customercenter/notice";
    }

    // review 조회
    @GetMapping(value = "/customercenter/review")
    public String CustomerCenterReviewForm(Model model) {
        List<Notice> notices = noticeService.findNotices();
        model.addAttribute("notices", notices);
        return "customercenter/review";
    }



}
