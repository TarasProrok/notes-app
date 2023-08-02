package com.ratatui.notes.note;

import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("/note")
@RequiredArgsConstructor
@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    private final NoteDtoValidator noteDtoValidator;

    @Value("${note.page.size}")
    private int defaultPageSize = 10;

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") NoteDto note, BindingResult bindingResult) {
        noteDtoValidator.validate(note, bindingResult);
        if (bindingResult.hasErrors()) return "note/create";
        NoteDto saveDto = noteService.add(note);
        return "redirect:/note/view?id=" + saveDto.getId();
    }

    @GetMapping("/create")
    public String createNoteViewPage(@ModelAttribute("note") NoteDto note) {
        return "/note/create";
    }

    @GetMapping("/list")
    public ModelAndView getNotes(
            @RequestParam(required = false, name = "page") Optional<Integer> page,
            @RequestParam(required = false, name = "size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(defaultPageSize);
        ModelAndView result = new ModelAndView("/note/note");
        Page<NoteDto> notePage = noteService.findAllByNoteOwnerFamily(PageRequest.of(currentPage - 1, pageSize));
        int totalPages = notePage.getTotalPages();
        result.addObject("notePage", notePage);
        result.addObject("previousPage", currentPage > 1 ? currentPage - 1 : 1);
        result.addObject("currentPage", currentPage);
        result.addObject("nextPage", currentPage < totalPages ? currentPage + 1 : totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            result.addObject("pageNumbers", pageNumbers);
        }
        return result;
    }

    @GetMapping("/edit")
    public String edit(@ModelAttribute("note") NoteDto note, @RequestParam UUID id) {
        note.setId(id);
        try {
            NoteDto noteDto = noteService.getById(id);
        } catch (EntityNotFoundException e) {
            return "redirect:/error/404";
        }
        return "/note/update";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam UUID id) {
        try {
            NoteDto noteDto = noteService.getById(id);
            model.addAttribute("note", noteDto);
        } catch (EntityNotFoundException e) {
            return "redirect:/error/404";
        }
        return "/note/view";
    }

    @PostMapping("/edit")
    public String editNote(
            @RequestParam(value = "id") UUID id,
            @ModelAttribute("note") NoteDto note, BindingResult bindingResult) {
        noteDtoValidator.validate(note, bindingResult);
        if (bindingResult.hasErrors()) return "note/edit";

        String accessType = "private";
        if (note.getNoteAccessType()==null) {
            accessType = "public";
        }

        NoteDto noteDto = new NoteDto();
        noteDto.setTitle(note.getTitle());
        noteDto.setContent(note.getContent());
        noteDto.setNoteAccessType(accessType);
        noteService.update(noteDto);
        return "redirect:/note/view?id=" + id;
    }

    @GetMapping("/delete")
    public RedirectView delete(@RequestParam UUID id) {
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/list");
        noteService.deleteById(id);
        return redirect;
    }

    @GetMapping("/share/{id}")
    public String shareNote(@PathVariable(name = "id") UUID id, Model model) {
        NoteDto noteDto = null;
        try {
            noteDto = noteService.getById(id);
            model.addAttribute("note", noteDto);
        } catch (EntityNotFoundException e) {
            return "redirect:/error/404";
        }

        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser.getFamily()!=null){
                List<User> familyUsers = userService.getFamilyUsers(currentUser.getFamily());
                if (familyUsers.contains(noteDto.getNoteOwner())){
                    return "note/share";
                }
            }
            if (Objects.equals(noteDto.getNoteAccessType(), "private")) {
                return "redirect:/error/404";
            }
        } catch (Exception e) {
            if (Objects.equals(noteDto.getNoteAccessType(), "private")) {
                return "redirect:/error/404";
            }
        }
        return "note/share";
    }

    @PostMapping("/share/{id}")
    public String getLink(@PathVariable("id") UUID id, Model model, HttpServletRequest request) {
        NoteDto noteDto = noteService.getById(id);
        model.addAttribute("note", noteDto);
        String fullUrl = request.getRequestURL().toString();
        noteService.copyLink(fullUrl);
        return "redirect:/note/view?id=" + id;
    }

    @PostMapping("/tag/add")
    public String addTag(Model model, @RequestParam UUID id, @RequestParam String tagTitle) {
        NoteDto noteDto = noteService.addTag(id, tagTitle);
        model.addAttribute("note", noteDto);
        return ("/note/update");
    }

    @GetMapping("/tag/delete")
    public String deleteTag(Model model, @RequestParam UUID noteId, @RequestParam UUID tagId) {
        NoteDto noteDto = noteService.deleteTag(noteId, tagId);
        model.addAttribute("note", noteDto);
        return ("/note/update");
    }

}