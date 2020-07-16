package ru.gonch.spring.rest;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.service.AuthorService;

import javax.validation.Valid;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    private final MeterRegistry meterRegistry;

    public AuthorController(AuthorService authorService, MeterRegistry meterRegistry) {
        this.authorService = authorService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/author")
    public String listPage(Model model) {
        meterRegistry.counter("visits.author").increment();
        return prepareListPage(model);
    }

    @GetMapping("/author/add")
    public String addPage(Author author) {
        return prepareAddPage();
    }

    @GetMapping("/author/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        return prepareEditPage(id, model, true);
    }

    @PostMapping("/author/add")
    public String addObject(@Valid Author author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareAddPage();
        } else {
            authorService.save(author);
            return prepareListPage(model);
        }
    }

    @PostMapping("/author/edit")
    public String editObject(@Valid Author author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareEditPage(author.getId(), model, false);
        } else {
            authorService.save(author);
            return prepareListPage(model);
        }
    }

    @GetMapping("/author/delete")
    public String deleteObject(@RequestParam("id") long id, Model model) {
        authorService.deleteById(id);
        return prepareListPage(model);
    }

    private String prepareListPage(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "author-list";
    }

    private String prepareAddPage() {
        return "author-add";
    }

    private String prepareEditPage(long id, Model model, boolean addObjectById) {
        if (addObjectById) {
            model.addAttribute("author", authorService.getById(id).orElseThrow(IllegalArgumentException::new));
        }
        return "author-edit";
    }
}
