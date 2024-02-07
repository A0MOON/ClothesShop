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
import study.clothesshop.web.IdFinderForm;
import study.clothesshop.web.LoginForm;
import study.clothesshop.web.MemberForm;
import study.clothesshop.web.SignUpForm;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @GetMapping(value = "/login/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signupForm", new SignUpForm());
        return "signup";
    }

    @PostMapping(value = "/login/signup")
    public String signUp(@Validated MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "signup";
        }
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setPassword(form.getPassword2());
        member.setLoginId(form.getLoginId());
        member.setEmail(form.getEmail());
        member.setPhone(form.getPhone());
        memberService.join(member);
        return "redirect:/";
    }

    // 로그인
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

    // 아이디 찾기
    @GetMapping(value = "/login/id-finder")
    public String idFinderForm(Model model) {
        model.addAttribute("idFinderForm", new IdFinderForm());
        return "id-finder";
    }

    @PostMapping("/login/id-finder")
    public String idFinder(@ModelAttribute("idFinderForm") IdFinderForm idFinderForm, Model model) {
        String name = idFinderForm.getName();
        String email = idFinderForm.getEmail();
        String findLoginId = memberService.findLoginIdByNameAndEmail(name, email);

        if (findLoginId != null) { // 회원이 존재하는 경우
            model.addAttribute("loginId", findLoginId);
            return "id-found"; // 아이디를 찾은 페이지로 이동
        } else {
            // 회원이 존재하지 않는 경우
            model.addAttribute("error", true);
            return "id-finder"; // 아이디 찾기 폼 페이지로 리다이렉트
        }
    }



}