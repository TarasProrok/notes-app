package com.ratatui.notes.errors;

public class NoteValidationException extends RuntimeException {
    private ErrorMessages errorMessages;

    public NoteValidationException(String message) {
        super(message);
    }

    public NoteValidationException(ErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }
}
