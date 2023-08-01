package com.ratatui.notes.family;

import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        User user = userService.getCurrentUser();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("family/list");
        modelAndView.addObject("family", user.getFamily());
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView editUserFamily() {
        User user = userService.getCurrentUser();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("family/edit");
        modelAndView.addObject("family", user.getFamily());

        return modelAndView;
    }

    @GetMapping("/leave")
    public RedirectView leaveFamily() {
        familyService.leaveFamily();

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/account");
        return redirect;
    }

    @PostMapping("/add")
    public RedirectView addFamily(@RequestParam(value = "code") String code) {
        Family familyByCode = familyService.getFamilyByCode(code);
        familyService.addFamily(familyByCode);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/account");
        return redirect;
    }

    @GetMapping("/create")
    public ModelAndView createFamilyShowPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("family/create");

        return modelAndView;
    }

    @PostMapping("/create")
    public RedirectView createFamily(@RequestParam(value = "title") String title) {
        FamilyResponseDto familyResponseDto = familyService.createFamily(title);
        Family familyByCode = familyService.getFamilyByCode(familyResponseDto.getCode());
        familyService.addFamily(familyByCode);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/account");
        return redirect;
    }
}
