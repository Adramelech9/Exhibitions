package com.exhibitions.first.repository;

import com.exhibitions.first.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByTitle(String title);
}
