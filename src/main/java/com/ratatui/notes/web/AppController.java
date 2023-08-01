package com.ratatui.notes.web;

import com.ratatui.notes.note.NoteService;
import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserDTO;
import com.ratatui.notes.user.UserMapper;
import com.ratatui.notes.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class AppController {

    private final NoteService service;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/")
    public String getMainPage() {
        return "redirect:/note/list";
    }

    @GetMapping("/contacts")
    public String getContactsPage() {
        return "/pages/contacts";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "/pages/about";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "/user/login";
    }

    @GetMapping("/account")
    public ModelAndView getAccountPage(){
        ModelAndView result = new ModelAndView("/user/account");

        UserDTO userDTO = userMapper.mapEntityToDto(userService.getCurrentUser());

        Map<Integer, String> gender = new HashMap<>();
        gender.put(0, "Не відомо");
        gender.put(1, "Чоловіча");
        gender.put(2, "Жіноча");
        gender.put(9, "Не застосовується");

        result.addObject("user", userDTO);
        result.addObject("family", userDTO.getFamily());
        result.addObject("gender", gender);
        return result;
    }
}