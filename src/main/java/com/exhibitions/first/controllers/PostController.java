package com.exhibitions.first.controllers;

import com.exhibitions.first.models.Post;
import com.exhibitions.first.models.User;
import com.exhibitions.first.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/post")
    public String postMain(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Post> post;
        if (filter != null && !filter.isEmpty()) {
            post = postRepository.findByTitle(filter);
        } else {
            post = postRepository.findAll();
        }
        model.addAttribute("post", post);
        model.addAttribute("filter", filter);
        return "post-main";
    }

    @GetMapping("/post/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String postAdd(Model model) {
        return "post-add";
    }

    @PostMapping("/post/add")
    public String postPostAdd(
            @AuthenticationPrincipal User user,
            @Valid Post post,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file")MultipartFile file
    ) throws IOException {
        post.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", post);

        } else {
            if (file == null || file.isEmpty()) {
                post.setFilename("default.png");
            } else if (file != null && !file.getOriginalFilename().isEmpty()) {  //&& !=... если убрать дефолтное значение
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                post.setFilename(resultFilename);
            }
            model.addAttribute("message", null);
            postRepository.save(post);
        }
        return "redirect:/post";
    }

    @GetMapping("/post/{id}")
    public String postDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/post";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "post-details";
    }

    @GetMapping("/post/{id}/edit")
    public String postEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/post";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "post-edit";
    }

    @PostMapping("/post/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String postUpdate(@PathVariable(value = "id") long id,@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/post";
    }

    @PostMapping("/post/{id}/remove")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String postDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/post";
    }
}