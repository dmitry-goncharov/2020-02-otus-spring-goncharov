package ru.gonch.spring.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.service.GenreService;

import javax.validation.Valid;

@Controller
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping({"/", "/genre"})
    public String listPage(Model model) {
        return prepareListPage(model);
    }

    @GetMapping("/genre/add")
    public String addPage(Genre genre) {
        return prepareAddPage();
    }

    @GetMapping("/genre/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        return prepareEditPage(id, model, true);
    }

    @PostMapping("/genre/add")
    public String addObject(@Valid Genre genre, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareAddPage();
        } else {
            genreService.save(genre);
            return prepareListPage(model);
        }
    }

    @PostMapping("/genre/edit")
    public String editObject(@Valid Genre genre, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareEditPage(genre.getId(), model, false);
        } else {
            genreService.save(genre);
            return prepareListPage(model);
        }
    }

    @GetMapping("/genre/delete")
    public String deleteObject(@RequestParam("id") long id, Model model) {
        genreService.deleteById(id);
        return prepareListPage(model);
    }

    private String prepareListPage(Model model) {
        model.addAttribute("genres", genreService.getAll());
        return "genre-list";
    }

    private String prepareAddPage() {
        return "genre-add";
    }

    private String prepareEditPage(long id, Model model, boolean addObjectById) {
        if (addObjectById) {
            model.addAttribute("genre", genreService.getById(id).orElseThrow(IllegalArgumentException::new));
        }
        return "genre-edit";
    }
}
