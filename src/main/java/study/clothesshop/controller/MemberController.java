package study.clothesshop.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import study.clothesshop.domain.Member;
import study.clothesshop.service.MemberService;
import study.clothesshop.loginweb.*;


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
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model, HttpSession session) {
        String loginId = loginForm.getLoginId();
        String password = loginForm.getPassword();
        boolean login = memberService.login(loginId, password);

        if (!login) {

            model.addAttribute("error", true);
            return "login";
        } else {
            session.setAttribute("loggedInUserId", loginId); // 사용자 아이디를 세션에 저장
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

        if (findLoginId != null) {
            model.addAttribute("loginId", findLoginId);
            return "id-found";
        } else {

            model.addAttribute("error", true);
            return "id-finder";
        }
    }

    // 비밀번호 변경을 위한 회원 찾기

    @GetMapping(value = "/login/password-finder")
    public String passwordFinderForm(Model model) {
        model.addAttribute("passwordFinderForm", new passwordFinderForm());
        return "password-finder";
    }

    @PostMapping("/login/password-finder")
    public String passwordFinder(@ModelAttribute("passwordFinderForm") passwordFinderForm passwordFinderForm, Model model, HttpSession session) {
        String loginId = passwordFinderForm.getLoginId();
        String name = passwordFinderForm.getName();
        String email = passwordFinderForm.getEmail();

        try {
            Member member = memberService.findPasswordByLoginIdAndNameAndEmail(loginId, name, email);
            if (member == null) {
                throw new IllegalArgumentException("해당하는 회원이 없습니다.");
            }

            session.setAttribute("loggedInMember", member);

            model.addAttribute("loginId", loginId);
            return "password-found";
        } catch (IllegalArgumentException e) {

            model.addAttribute("error", true);
            return "password-finder";
        }
    }

    // 비밀번호 변경

    @GetMapping(value = "/login/password-found")
    public String passwordFoundForm1(Model model) {
        model.addAttribute("passwordFoundForm", new passwordFoundForm());
        return "password-found";
    }

    @PostMapping("/login/password-found")
    public String changePassword(HttpSession session,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", true);
            return "password-found";
        }

        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/login";
        }

        member.setPassword(newPassword);
        memberService.save(member);
        return "home";
    }


}


