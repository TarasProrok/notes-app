package com.ratatui.notes.web;


import com.ratatui.notes.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class AppController {

    private final NoteService service;

    @GetMapping("/")
    public ModelAndView getMainPage() {
        ModelAndView modelAndView = new ModelAndView("/note/note");
        modelAndView.addObject("notes", service.listAll());
        return modelAndView;
    }

    @GetMapping("/contacts")
    public String getContactsPage(){
        return "/pages/contacts";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "/pages/about";
    }


}