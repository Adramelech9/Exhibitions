package com.exhibitions.first.controllers;

import com.exhibitions.first.models.Post;
import com.exhibitions.first.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("title", "Аккаунт");
        return "account";
    }

    /*@PostMapping("filter")
    public String filter(@RequestParam String title, Model model) {
        Iterable<Post> posts;
        if (title != null && !title.isEmpty()) {
            posts = postRepository.findByTitle(title);
        } else posts = postRepository.findAll();
        model.addAttribute("messages", posts);
        return "first";
    }*/

    /*@GetMapping("/greeting")  //работа исключительно с "Гет" запросом
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }*/

}