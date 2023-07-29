package com.ratatui.notes.note;

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
    @BeforeEach
    void init() {
    }
    @Order(1)
    @Test
    void addNote() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expected = "Моя страва № " + sdf.format(new Date().getTime());
        Note note = new Note();
        note.setTitle(expected);
        note.setContent("Сіль Перець Вода Котлети Риба...");
        note.setNoteOwner(UUID.fromString("2D1EBC5B-7D27-4197-9CF0-E84451C5AAA1"));
        note.setNoteAccessType("private");
        Note actualNote = noteService.add(note);
        String actual = actualNote.getTitle();
        Assertions.assertEquals(expected, actual);
    }
}