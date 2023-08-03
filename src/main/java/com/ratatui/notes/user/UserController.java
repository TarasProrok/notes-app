package com.ratatui.notes.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andriy Gaponov
 */
@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/login")
    public String getLoginPage(){
        return "/user/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "/user/register";
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
