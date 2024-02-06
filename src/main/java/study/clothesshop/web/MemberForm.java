package study.clothesshop.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    private String loginId;
    private String email;
    private String name;
    private String password;
    private String password2;
    private String phone;

}