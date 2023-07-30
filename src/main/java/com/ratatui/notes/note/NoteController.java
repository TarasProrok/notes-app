package com.ratatui.notes.note;

import com.ratatui.notes.note.Note;
import com.ratatui.notes.note.NoteDto;
import com.ratatui.notes.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;
@RequestMapping("/note")
@RequiredArgsConstructor
@Controller
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/create")
    public RedirectView createNote(@ModelAttribute NoteDto noteDto){
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/list");
        noteService.add(noteDto);
        return redirect;
    }

    @GetMapping("/list")
    public ModelAndView getNotes(){
        ModelAndView modelAndView = new ModelAndView("/note/note");
        modelAndView.addObject("notes", noteService.listAll());
        return modelAndView;
    }

    @GetMapping("/update")
    public String edit(Model model, @RequestParam UUID id){
        NoteDto noteDto = noteService.getById(id);
        model.addAttribute("note", noteDto);
        return ("/note/update");
    }

    @PostMapping("/update")
    public RedirectView editNote(@ModelAttribute NoteDto noteDto){
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/list");
        noteService.update(noteDto);
        return redirect;
    }

    @GetMapping("/delete")
    public RedirectView delete(@RequestParam UUID id){
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/list");
        noteService.deleteById(id);
        return redirect;
    }
}