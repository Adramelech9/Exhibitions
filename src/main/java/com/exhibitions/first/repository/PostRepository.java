package com.exhibitions.first.repository;

import com.exhibitions.first.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
