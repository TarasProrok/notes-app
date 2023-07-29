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
        TagDto tagDto = new TagDto();
        tagDto.setTitle(expected);
        Tag actualTag = tagService.addIfNotExists(tagDto);
        String actual = actualTag.getTitle();
        Assertions.assertEquals(expected, actual);
    }
    @Order(2)
    @Test
    void addIfNotExistsIfAlreadyExists() {
        String dish = "Котлета";
        TagDto tagDto1 = new TagDto();
        tagDto1.setTitle(dish);
        tagService.addIfNotExists(tagDto1);
        long expected = tagService.listAll().size();
        TagDto tagDto2 = new TagDto();
        tagDto2.setTitle(dish.toUpperCase());
        tagService.addIfNotExists(tagDto2);
        long actual = tagService.listAll().size();
        Assertions.assertEquals(expected, actual);
    }
}