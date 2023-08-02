package com.ratatui.notes.note;

import com.ratatui.notes.user.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class NoteServiceTest {
    @Autowired
    NoteService noteService;
    @Autowired
    UserService userService;
    @BeforeEach
    void init() {
    }
    @Order(1)
    @Test
    void addNote() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String expected = "Моя страва № " + sdf.format(new Date().getTime());
//        NoteDto noteDto = new NoteDto();
//        noteDto.setTitle(expected);
//        noteDto.setContent("Сіль Перець Вода Котлети Риба...");
//        noteDto.setNoteOwner( userService.findUserById(UUID.fromString("2D1EBC5B-7D27-4197-9CF0-E84451C5AAA1")));
//        noteDto.setNoteAccessType("private");
//        NoteDto actualNoteDto = noteService.add(noteDto);
//        String actual = actualNoteDto.getTitle();
//        Assertions.assertEquals(expected, actual);
    }
}