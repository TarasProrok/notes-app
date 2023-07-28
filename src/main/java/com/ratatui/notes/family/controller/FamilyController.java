package com.ratatui.notes.family.controller;

import com.ratatui.notes.family.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Andriy Gaponov
 */
@RequestMapping("/family")
@RequiredArgsConstructor
@Controller
public class FamilyController {

    private final FamilyService familyService;

    @GetMapping("/")
    public ModelAndView getUserFamily(){
        ModelAndView modelAndView = new ModelAndView("/family/list");

        modelAndView.addObject("family", familyService.getFamilyById(null)); //TODO get family id from user
        return modelAndView;
    }
}
