package study.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.clothesshop.domain.Notice;
import study.clothesshop.dto.NoticeDTO;
import study.clothesshop.repository.NoticeRepository;
import study.clothesshop.service.NoticeService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final NoticeService noticeService;

    //notice page
    @GetMapping(value = "/admin/customercenter") // url
    public String CustomerCenterNoticeForm() {
        log.info("notice controller");
        return "notice"; // html
    }

    // notice 글 등록
    @GetMapping(value = "/admin/notice/new")
    public String createNoticeForm(Model model) {
        model.addAttribute("NoticeDTO", new NoticeDTO());
        return "admin/notice";
    }

    @PostMapping(value = "/admin/notice/new")
    public String createNoticeForm(@ModelAttribute NoticeDTO noticeDTO) {
        Notice notice = new Notice();
        notice.setNoticeTitle(noticeDTO.getNoticeTitle());
        notice.setNoticeDate(noticeDTO.getNoticeDate());
        notice.setNoticeWriter(noticeDTO.getNoticeWriter());
        noticeService.save(notice);
        return "home";
    }


}
