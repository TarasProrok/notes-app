package com.ratatui.notes.note;

import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Value("${note.page.size}")
    private int defaultPageSize = 10;

    @PostMapping("/create")
    public RedirectView createNote(@RequestParam(value = "title") String title,
                                   @RequestParam(value = "content") String content,
                                   @RequestParam(value = "publicNote", required = false) String publicNote) {
        String accessType = "private";
        if (publicNote != null) {
            accessType = "public";
        }
        NoteDto noteDto = new NoteDto();
        noteDto.setContent(content);
        noteDto.setTitle(title);
        noteDto.setNoteAccessType(accessType);
        NoteDto saveDto = noteService.add(noteDto);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/view?id=" + saveDto.getId());
        return redirect;
    }

    @GetMapping("/create")
    public ModelAndView createNoteViewPage() {
        ModelAndView result = new ModelAndView("/note/create");
        return result;
    }

    @GetMapping("/list")
    public ModelAndView getNotes(
            @RequestParam(required = false, name = "page") Optional<Integer> page,
            @RequestParam(required = false, name = "size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(defaultPageSize);
        ModelAndView result = new ModelAndView("/note/note");
        Page<NoteDto> notePage = noteService.findAll(PageRequest.of(currentPage - 1, pageSize));
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
    public String edit(Model model, @RequestParam UUID id) {
        try {
            NoteDto noteDto = noteService.getById(id);
            model.addAttribute("note", noteDto);
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
    public RedirectView editNote(
            @RequestParam(value = "id") UUID id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "publicNote", required = false) String publicNote) {


        String accessType = "private";
        if (publicNote != null) {
            accessType = "public";
        }
        NoteDto noteDto = new NoteDto();
        noteDto.setId(id);
        noteDto.setContent(content);
        noteDto.setTitle(title);
        noteDto.setNoteAccessType(accessType);

        noteService.update(noteDto);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/view?id=" + id);
        return redirect;
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
            //TODO перевірка на групу користувача
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
}