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

import java.util.UUID;

import static com.ratatui.notes.utils.Constants.URL_ACCOUNT;

/**
 * @author Andriy Gaponov
 */
@RequestMapping("/family")
@RequiredArgsConstructor
@Controller
public class FamilyController {

    private final FamilyService familyService;
    private final UserService userService;

    @GetMapping("/edit")
    public ModelAndView editUserFamilyShowPage() {
        User user = userService.getCurrentUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("family/edit");
        modelAndView.addObject("family", user.getFamily());
        return modelAndView;
    }

    @PostMapping("/edit")
    public RedirectView editUserFamily(@RequestParam(value = "id") UUID id,
                                       @RequestParam(value = "title") String title) {
        familyService.updateFamily(id, title);
        RedirectView redirect = new RedirectView();
        redirect.setUrl(URL_ACCOUNT);
        return redirect;
    }

    @GetMapping("/leave")
    public RedirectView leaveFamily() {
        familyService.leaveFamily();
        RedirectView redirect = new RedirectView();
        redirect.setUrl(URL_ACCOUNT);
        return redirect;
    }

    @PostMapping("/add")
    public RedirectView addFamily(@RequestParam(value = "code") String code) {
        RedirectView redirect = new RedirectView();
        try {
            Family familyByCode = familyService.getFamilyByCode(code);
            familyService.addFamily(familyByCode);
            redirect.setUrl(URL_ACCOUNT);
        } catch (IllegalArgumentException e) {
            redirect.setUrl("/error/404");
        }
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
        redirect.setUrl(URL_ACCOUNT);
        return redirect;
    }
}
