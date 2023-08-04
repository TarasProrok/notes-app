package com.ratatui.notes.note;

import static com.ratatui.notes.utils.Constants.REDIRECT_URL_404;

import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/note")
@RequiredArgsConstructor
@Controller
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;
    public static final String NOTE_UPDATE_TEMPLATE = "/note/update";
    @Value("${note.page.size}")
    private static final int DEFAULT_PAGE_SIZE = 10;

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
        return new ModelAndView("/note/create");
    }

    @GetMapping("/list")
    public ModelAndView getNotes(
            @RequestParam(required = false, name = "page") Optional<Integer> page,
            @RequestParam(required = false, name = "size") Optional<Integer> size,
            @RequestParam(required = false, name = "searchText", defaultValue = "") String searchText) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        ModelAndView result = new ModelAndView("/note/note");
        Page<NoteDto> notePage = noteService.findAllByNoteOwnerFamily(PageRequest.of(currentPage - 1, pageSize), searchText);
        int totalPages = notePage.getTotalPages();
        result.addObject("notePage", notePage);
        result.addObject("previousPage", currentPage > 1 ? currentPage - 1 : 1);
        result.addObject("currentPage", currentPage);
        result.addObject("nextPage", currentPage < totalPages ? currentPage + 1 : totalPages);
        result.addObject("searchText", searchText);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
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
            return REDIRECT_URL_404;
        }
        return NOTE_UPDATE_TEMPLATE;
    }

    @GetMapping("/view")
    public String view(Model model, UriComponentsBuilder uriComponentsBuilder,
                       @RequestParam UUID id) {
        try {
            NoteDto noteDto = noteService.getById(id);
            model.addAttribute("note", noteDto);
            model.addAttribute("sharedLink", noteService.getSharedLink(id, uriComponentsBuilder));
        } catch (EntityNotFoundException e) {
            return REDIRECT_URL_404;
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
            return REDIRECT_URL_404;
        }

        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser.getFamily() != null) {
                List<User> familyUsers = userService.getFamilyUsers(currentUser.getFamily());
                if (familyUsers.contains(noteDto.getNoteOwner())) {
                    return "note/share";
                }
            }
            if (Objects.equals(noteDto.getNoteAccessType(), "private")) {
                return REDIRECT_URL_404;
            }
        } catch (Exception e) {
            if (Objects.equals(noteDto.getNoteAccessType(), "private")) {
                return REDIRECT_URL_404;
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
        return (NOTE_UPDATE_TEMPLATE);
    }

    @GetMapping("/tag/delete")
    public String deleteTag(Model model, @RequestParam UUID noteId, @RequestParam UUID tagId) {
        NoteDto noteDto = noteService.deleteTag(noteId, tagId);
        model.addAttribute("note", noteDto);
        return (NOTE_UPDATE_TEMPLATE);
    }
}