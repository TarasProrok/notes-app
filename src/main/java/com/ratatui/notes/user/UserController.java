package com.ratatui.notes.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import com.ratatui.notes.utils.Helper;

import java.sql.Date;
import java.util.Objects;


@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/account")
    public RedirectView updateAccount(@RequestParam(value = "oldEmail") String oldEmail,
                                      @RequestParam(value = "email") String email,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "nickname") String nickname,
                                      @RequestParam(value = "birthDate") String birthDate,
                                      @RequestParam(value = "gender") int gender){
        RedirectView redirect = new RedirectView();
        User currentUser = userService.getCurrentUser();
        UserDTO userDTO = userMapper.mapEntityToDto(currentUser);
        userDTO.setEmail(email);
        if (!birthDate.isBlank()){
            userDTO.setBirthDate(Date.valueOf(Helper.getLocalDateFromString(birthDate)));
        }
        userDTO.setNickname(nickname);
        userDTO.setGenderId(gender);

        if (!password.isBlank()){
            if (!passwordEncoder.matches(password, currentUser.getPassword())){
                userDTO.setPassword(passwordEncoder.encode(password));
            }
        }

        userService.updateUser(userDTO);
        redirect.setUrl("/account");
        if (!Objects.equals(oldEmail, email)){
            redirect.setUrl("/logout");
        }

        return redirect;
    }
}
