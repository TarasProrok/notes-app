package com.ratatui.notes.note;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author Andriy Gaponov
 */
@RequiredArgsConstructor
@Component
public class NoteDtoValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return NoteDtoValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NoteDto dto = (NoteDto) target;
        if (StringUtils.isBlank(dto.getTitle())) {
            errors.rejectValue("title", "", "Заголовок не може бути пустим!");
        }
        if(dto.getTitle().length() < 5 || dto.getTitle().length() > 100){
            errors.rejectValue("title", "", "Довжина заголовка від 5 до 100 символів!");
        }
        if(dto.getContent().length() < 12 || dto.getContent().length() > 10000){
            errors.rejectValue("content", "", "Довжина тексту від 5 до 10000 символів!");
        }
    }
}
