package com.ratatui.notes.note;

import com.ratatui.notes.errors.ErrorMessages;
import com.ratatui.notes.errors.NoteValidationException;
import org.springframework.stereotype.Component;

@Component
public class NoteValidator {

    protected ErrorMessages errorMessages = new ErrorMessages();

    public void validate(NoteDto noteDto) {
        errorMessages.clear();
        checkTitle(noteDto.getTitle());

        if (!errorMessages.getErrors().isEmpty()){
            throw new NoteValidationException(errorMessages);
        }
    }

    private void checkTitle(String title) {
        if (title == null || title.isEmpty()) {
            errorMessages.addError("Заголовок не може бути порожній.");

        }
        if (title.length() > 100 || title.length() < 5) {
            errorMessages.addError("Довжина заголовка повинна бути від 5 до 100 символів");
        }
    }
}
