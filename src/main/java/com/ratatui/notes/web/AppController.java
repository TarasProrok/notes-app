package com.ratatui.notes.web;

import com.ratatui.notes.note.NoteService;
import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserDTO;
import com.ratatui.notes.user.UserMapper;
import com.ratatui.notes.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}