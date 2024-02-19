package study.clothesshop.loginweb;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordForm {
    private String loginId;
    private String newPassword;
    private String confirmPassword;
    private String name;
    private String email;

}
