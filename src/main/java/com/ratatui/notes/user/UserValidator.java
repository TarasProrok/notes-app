package com.ratatui.notes.user;

import com.ratatui.notes.errors.ErrorMessages;
import com.ratatui.notes.errors.NoteValidationException;
import com.ratatui.notes.utils.Helper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Component
public class UserValidator {

    protected ErrorMessages errorMessages = new ErrorMessages();
    @Setter
    private UserService userService;


    public void validate(UserDTO userDTO, boolean newUser) {
        errorMessages.clear();
        checkPassword(userDTO.getPassword());
        checkEmail(userDTO.getEmail(), newUser);

        if (!errorMessages.getErrors().isEmpty()){
            throw new NoteValidationException(errorMessages);
        }
    }

    private void checkEmail(String email, boolean newUser){
        if (StringUtils.isBlank(email)) {
            errorMessages.addError("Пошта не може бути порожнюю!");
        } else {
            if (newUser){
                try {
                    userService.findUserByName(email);
                    errorMessages.addError("Користувач з поштою " + email + " вже зареєстрований");
                } catch (NoSuchElementException ex){
                    //nop
                }
            }
        }
    }

    private void checkPassword(String password) {
        if (StringUtils.isBlank(password)) {
            errorMessages.addError("Пароль не може бути порожнім!");
        }

        String passwordRegexPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";
        if (!Helper.patternMatches(password, passwordRegexPattern)) {
            errorMessages.addError("Не вірний формат паролю! Довжина паролю повинна бути не меньше 8 символів, латинські літери та цифри. Мінімум одна велика літера.");
        }
    }
}
