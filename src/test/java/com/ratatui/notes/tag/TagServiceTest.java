package com.ratatui.notes.tag;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class TagServiceTest {
    @Autowired
    TagService tagService;
    @BeforeEach
    void init() {
    }
    @Order(1)
    @Test
    void addIfNotExistsIfNotExists() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expected = "Борщ " + sdf.format(new Date().getTime());
        Tag tag = new Tag();
        tag.setTitle(expected);
        Tag actualTag = tagService.addIfNotExists(tag);
        String actual = actualTag.getTitle();
        Assertions.assertEquals(expected, actual);
    }
    @Order(2)
    @Test
    void addIfNotExistsIfAlreadyExists() {
        String dish = "Котлета";
        Tag tag1 = new Tag();
        tag1.setTitle(dish);
        tagService.addIfNotExists(tag1);
        long expected = tagService.listAll().size();
        Tag tag2 = new Tag();
        tag2.setTitle(dish.toUpperCase());
        tagService.addIfNotExists(tag2);
        long actual = tagService.listAll().size();
        Assertions.assertEquals(expected, actual);
    }
}