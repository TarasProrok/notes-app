package com.ratatui.notes.user;

import com.ratatui.notes.errors.ErrorMessages;
import com.ratatui.notes.errors.InfoMessages;
import com.ratatui.notes.note.NoteDto;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Andriy Gaponov
 */
@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLoginPage(){
        return "/user/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
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
        } catch (NoSuchElementException e){
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

    @GetMapping("/account")
    public ModelAndView getAccountPage(){
        ModelAndView result = new ModelAndView("/user/account");

        User currentUser = userService.getCurrentUser();
        UserDTO userDTO = userMapper.mapEntityToDto(currentUser);
        List<User> familyUsers = userService.getFamilyUsers(currentUser.getFamily());
        List<UserDTO> usersFamilyDtos = userMapper.mapEntityToDto(familyUsers);

        Map<Integer, String> gender = new HashMap<>();
        gender.put(0, "Не відомо");
        gender.put(1, "Чоловіча");
        gender.put(2, "Жіноча");
        gender.put(9, "Не застосовується");

        result.addObject("user", userDTO);
        result.addObject("family", userDTO.getFamily());
        result.addObject("gender", gender);
        result.addObject("usersFamilyDtos", usersFamilyDtos);
        return result;
    }
}
