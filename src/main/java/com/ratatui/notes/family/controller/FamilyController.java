package com.ratatui.notes.family.controller;

import com.ratatui.notes.family.entity.Family;
import com.ratatui.notes.family.service.FamilyService;
import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Andriy Gaponov
 */
@RequestMapping("/family")
@RequiredArgsConstructor
@Controller
public class FamilyController {

    private final FamilyService familyService;
    private final UserService userService;

    @GetMapping
    public ModelAndView getUserFamily() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userService.findUserByName(username);


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("family/list");

       // modelAndView.addObject("family", familyService.getFamilyById(user.get)); //TODO get family id from user
        Family family = new Family();
        family.setTitle("Моя сім'я");
        family.setCode("125і48");

        //family = null;

        modelAndView.addObject("family", family);
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView editUserFamily() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("family/edit");

        //modelAndView.addObject("family", familyService.getFamilyById(null)); //TODO get family id from user
        Family family = new Family();
        family.setTitle("Моя сім'я");
        family.setCode("125і48");

        modelAndView.addObject("family", family);
        return modelAndView;
    }

    @GetMapping("/leave")
    public RedirectView leaveFamily() {
        familyService.leaveFamily();

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/family");
        return redirect;
    }

    @GetMapping("/add")
    public RedirectView addFamily() {
        familyService.addFamily();

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/family");
        return redirect;
    }
}
