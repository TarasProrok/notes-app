package com.ratatui.notes.note;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("/note")
@RequiredArgsConstructor
@Controller
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/create")
    public RedirectView createNote(@RequestParam(value = "title") String title,
                                   @RequestParam(value = "content") String content,
                                   @RequestParam(value = "publicNote", required = false) String publicNote){
        String accessType = "private";
        if (publicNote != null){
            accessType = "public";
        }
        NoteDto noteDto = new NoteDto();
        noteDto.setContent(content);
        noteDto.setTitle(title);
        noteDto.setNoteAccessType(accessType);
        noteService.add(noteDto);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/note/list");
        return redirect;
    }

    @GetMapping("/create")
    public ModelAndView createNoteViewPage(){
        ModelAndView result = new ModelAndView("/note/create");
        return result;
    }

    @GetMapping("/list")
    public ModelAndView getNotes(
            @RequestParam(required=false, name="page") Optional<Integer> page,
            @RequestParam(required=false, name="size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);
        ModelAndView result = new ModelAndView("/note/note");
        Page<NoteDto> notePage = noteService.findAll(PageRequest.of(currentPage - 1, pageSize));
        int totalPages = notePage.getTotalPages();
        result.addObject("notePage", notePage);
        result.addObject("previousPage", currentPage>1?currentPage-1:1);
        result.addObject("currentPage", currentPage);
        result.addObject("nextPage", currentPage<totalPages?currentPage+1:totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            result.addObject("pageNumbers", pageNumbers);
        }
        return result;
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam UUID id){
        NoteDto noteDto = noteService.getById(id);
        model.addAttribute("note", noteDto);
        return ("/note/update");
    }

    @PostMapping("/edit")
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