package com.ratatui.notes.auth;


import com.ratatui.notes.errors.ErrorMessages;
import com.ratatui.notes.errors.InfoMessages;
import com.ratatui.notes.user.UserDTO;
import com.ratatui.notes.user.UserMapper;
import com.ratatui.notes.user.UserRoles;
import com.ratatui.notes.user.UserService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLoginPage() {
        return "/user/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "/user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("errorsMessages") ErrorMessages errorsMessages,
        @ModelAttribute("infoMessages") InfoMessages infoMessages,
        @RequestParam(value = "username") String username,
        @RequestParam(value = "password") String password,
        @RequestParam(value = "nickname") String nickname) {
        try {
            userService.findUserByName(username);
            errorsMessages.addError("Користувач вже зареєстрований. Ви можете увійти.");
            return "user/login";
        } catch (NoSuchElementException e) {
            UserDTO userDTO = UserDTO.builder()
                .email(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .isEnable(true)
                .genderId(0)
                .role(UserRoles.ROLE_USER)
                .build();

            userService.createNewUser(userDTO);
            infoMessages.addMessage("Ви успішно зареєструвалися. Можете увійти.");
            return "user/login";
        }
    }
}
