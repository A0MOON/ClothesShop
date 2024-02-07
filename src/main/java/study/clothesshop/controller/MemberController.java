package study.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.clothesshop.domain.Member;
import study.clothesshop.service.MemberService;
import study.clothesshop.web.LoginForm;
import study.clothesshop.web.MemberForm;

import java.util.Optional;

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

    @GetMapping(value = "/login/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("login/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        String loginId = loginForm.getLoginId();
        String password = loginForm.getPassword();
        boolean login = memberService.login(loginId, password);

        if (!login) {
            // 로그인 실패 처리
            model.addAttribute("error", true);
            return "login"; // 로그인 폼 페이지로 리다이렉트
        } else {
            // 로그인 성공 처리
            return "redirect:/";
        }
    }
}