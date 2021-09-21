package com.exhibitions.first.service;

import com.exhibitions.first.models.Post;
import com.exhibitions.first.models.User;
import com.exhibitions.first.models.dto.PostDto;
import com.exhibitions.first.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Page<PostDto> postList(Pageable pageable, String filter, User user) {
        if (filter != null && !filter.isEmpty()) {
            return postRepository.findByTitle(filter, pageable, user);
        } else {
            return postRepository.findAll(pageable, user);
        }
    }
}
