package com.ratatui.notes.utils;

import java.util.HashMap;
import java.util.Map;

public final class Constants {

    private Constants() {
    }

    public static final int FAMILY_CODE_LENGTH = 10;

    public static final String REDIRECT_URL_404 = "redirect:error/404";
    public static final String URL_ACCOUNT = "/account";

    public static Map<Integer, String> GENDERS = new HashMap<>();

    static {
        GENDERS.put(0, "Не відомо");
        GENDERS.put(1, "Чоловіча");
        GENDERS.put(2, "Жіноча");
        GENDERS.put(9, "Не застосовується");
    }
}
