package com.ratatui.notes.web;

import com.ratatui.notes.user.UserOptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class AppController {

    private final UserOptionsService userOptionsService;

    @GetMapping("/")
    public String getMainPage() {
        return "redirect:note/list";
    }

    @GetMapping("/contacts")
    public ModelAndView getContactsPage() {
        ModelAndView result = new ModelAndView("pages/contacts");
        result.addObject("options", userOptionsService.getOptions());
        return result;
    }

    @GetMapping("/about")
    public ModelAndView getAboutPage() {
        ModelAndView result = new ModelAndView("pages/about");
        result.addObject("options", userOptionsService.getOptions());
        return result;
    }
}
