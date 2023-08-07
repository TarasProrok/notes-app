package com.ratatui.notes.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Objects;

import static com.ratatui.notes.utils.Constants.GENDERS;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/account")
    public ModelAndView getAccountPage() {
        ModelAndView result = new ModelAndView("user/account");

        User currentUser = userService.getCurrentUser();
        UserDTO userDTO = userMapper.mapEntityToDto(currentUser);
        List<User> familyUsers = userService.getFamilyUsers(currentUser.getFamily());
        List<UserDTO> usersFamilyDtos = userMapper.mapEntityToDto(familyUsers);

        result.addObject("user", userDTO);
        result.addObject("family", userDTO.getFamily());
        result.addObject("gender", GENDERS);
        result.addObject("usersFamilyDtos", usersFamilyDtos);
        return result;
    }

    @PostMapping("/account")
    public RedirectView updateAccount(@RequestParam(value = "oldEmail") String oldEmail,
                                      @RequestParam(value = "email") String email,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "nickname") String nickname,
                                      @RequestParam(value = "birthDate") String birthDate,
                                      @RequestParam(value = "gender") int gender) {
        RedirectView redirect = new RedirectView();

        userService.updateUser(email, password, nickname, birthDate, gender);

        redirect.setUrl("/account");
        if (!Objects.equals(oldEmail, email)) {
            redirect.setUrl("/logout");
        }
        return redirect;
    }
}
