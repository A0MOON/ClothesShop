package study.clothesshop.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import study.clothesshop.domain.Member;
import study.clothesshop.dto.ItemDTO;
import study.clothesshop.service.MemberService;
import study.clothesshop.loginweb.*;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @GetMapping(value = "/users/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signupForm", new SignUpForm());
        return "users/signup";
    }

    @PostMapping(value = "/users/signup")
    public String signUp(@Validated MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "users/signup";
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
    @GetMapping(value = "/users/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "users/login";
    }

    @PostMapping("users/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model, HttpSession session) {
        String loginId = loginForm.getLoginId();
        String password = loginForm.getPassword();
        boolean login = memberService.login(loginId, password);

        if (!login) {

            model.addAttribute("error", true);
            return "users/login";
        } else {
            session.setAttribute("loggedInUserId", loginId); // 사용자 아이디를 세션에 저장
            return "redirect:/";
        }
    }

    // 아이디 찾기
    @GetMapping(value = "/users/id/search")
    public String idFinderForm(Model model) {
        model.addAttribute("idFinderForm", new IdFinderForm());
        return "users/id/search"; // 확실해
    }

    @PostMapping("/users/id/search")
    public String idFinder(@ModelAttribute("idFinderForm") IdFinderForm idFinderForm, Model model) {
        String name = idFinderForm.getName();
        String email = idFinderForm.getEmail();
        try {
            String loginId = memberService.findLoginIdByNameAndEmail(name, email);
            model.addAttribute("loginId", loginId);
            return "users/id/id";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "해당하는 회원이 없습니다.");
            return "users/id/search";
        }
    }


    // 비밀번호 변경을 위한 회원 찾기

    @GetMapping(value = "/users/password/search")
    public String passwordFinderForm(Model model) {
        model.addAttribute("passwordFinderForm", new passwordFinderForm());
        return "users/password/search";
    }

    @PostMapping("/users/password/search")
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
            return "users/password/password";
        } catch (IllegalArgumentException e) {

            model.addAttribute("error", true);
            return "users/password/search";
        }
    }

    // 비밀번호 변경
    @GetMapping(value = "/users/password/newpassword")
    public String passwordFoundForm1(Model model) {
        model.addAttribute("passwordFoundForm", new passwordFoundForm());
        return "/users/password/newpassword";
    }

    @PostMapping("/users/password/newpassword")
    public String changePassword(HttpSession session,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", true);
            return "/users/password/newpassword";
        }

        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/users/login";
        }

        member.setPassword(newPassword);
        memberService.save(member);
        return "home";
    }

    // cart page
    @GetMapping(value = "/users/cart") // url
    public String CartForm(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "cart/cart"; // html
    }


}


