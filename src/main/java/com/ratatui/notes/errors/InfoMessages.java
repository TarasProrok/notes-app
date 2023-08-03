package com.ratatui.notes.errors;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InfoMessages {

    private List<String> messages = new ArrayList<>();

    public void addMessage(String message){
        messages.add(message);
    }
}