package com.ratatui.notes.errors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoteValidationException.class)
    public ModelAndView handleValidateException(HttpServletRequest req, NoteValidationException ex) {
        ModelAndView result = new ModelAndView();
        result.addObject("errorsMessages", ex.getErrorMessages());
        result.setViewName("error/400-bad-request");
        return result;
    }
}
