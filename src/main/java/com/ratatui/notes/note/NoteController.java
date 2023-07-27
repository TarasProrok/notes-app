package com.ratatui.notes.note;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/note")
@RequiredArgsConstructor
@Controller
public class NoteController {
    private final NoteService service;

    @GetMapping("/create")
    public String create(){
        return ("/note/create");
    }

    @PostMapping("/create")
    public RedirectView createNote(@ModelAttribute Note note){
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/list");
        service.add(note);
        return redirect;
    }

    @GetMapping("/list")
    public ModelAndView getNotes(){
        ModelAndView modelAndView = new ModelAndView("/note/note");
        modelAndView.addObject("notes", service.listAll());
        return modelAndView;
    }

    @GetMapping("/update")
    public String edit(Model model, @RequestParam String id){
        Note note = service.getById(id);
        model.addAttribute("note", note);
        return ("/note/update");
    }

    @PostMapping("/update")
    public RedirectView editNote(@ModelAttribute Note note){
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/list");
        service.update(note);
        return redirect;
    }

    @GetMapping("/delete")
    public RedirectView delete(@RequestParam long id){
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/list");
        service.deleteById(String.valueOf(id));
        return  redirect;
    }
}