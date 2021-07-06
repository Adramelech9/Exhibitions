package com.exhibitions.first.controllers;

import com.exhibitions.first.models.Post;
import com.exhibitions.first.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class firstController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/first")
    public String first(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "first";
    }

    @GetMapping("/first/add")
    public String add(Model model) {
        return "add";
    }

    @PostMapping("/first/add")
    public String PostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/first";
    }

    @GetMapping("/first/{id}")
    public String firstDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:first";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "firstDetails";
    }

    @GetMapping("/first/{id}/edit")
    public String firstEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:first";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "firstEdit";
    }

    @PostMapping("/first/{id}/edit")
    public String PostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/first";
    }

    @PostMapping("/first/{id}/remove")
    public String PostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/first";
    }
}
