package com.exhibitions.first.controllers;

import com.exhibitions.first.models.Post;
import com.exhibitions.first.models.User;
import com.exhibitions.first.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public String postMain(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> page;
        if (filter != null && !filter.isEmpty()) {
            page = postRepository.findByTitle(filter, pageable);
        } else {
            page = postRepository.findAll(pageable);
        }
        int[] sizeList = {5, 10, 15, 20};
        int[] body;
        if (page.getTotalPages() > 7) {
            int totalPages = page.getTotalPages();
            int pageNumber = page.getNumber()+1;
            int[] head = (pageNumber > 4) ? new int[]{1, -1} : new int[]{1,2,3};
            int[] bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1) ? new int[]{pageNumber-2, pageNumber-1} : new int[]{};
            int[] bodyCenter = (pageNumber > 3 && pageNumber < totalPages - 2) ? new int[]{pageNumber} : new int[]{};
            int[] bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3) ? new int[]{pageNumber+1, pageNumber+2} : new int[]{};
            int[] tail = (pageNumber < totalPages - 3) ? new int[]{-1, totalPages} : new int[] {totalPages-2, totalPages-1, totalPages};
            body = ControllerUtils.merge(head, bodyBefore, bodyCenter, bodyAfter, tail);

        } else {
            body = new int[page.getTotalPages()];
            for (int i = 0; i < page.getTotalPages(); i++) {
                body[i] = 1+i;
            }
        }

        model.addAttribute("sizeList", sizeList);
        model.addAttribute("body", body);
        model.addAttribute("page", page);
        model.addAttribute("url", "/post");
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