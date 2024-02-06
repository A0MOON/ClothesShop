package study.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.clothesshop.domain.Member;
import study.clothesshop.service.MemberService;
import study.clothesshop.web.MemberForm;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "member/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Validated MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "member/createMemberForm";
        }
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setLoginId(form.getLoginId());
        member.setEmail(form.getEmail());
        member.setPhone(form.getPhone());
        memberService.join(member);
        return "redirect:/";
    }
}