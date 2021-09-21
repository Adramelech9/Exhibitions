package com.exhibitions.first.models.dto;

import com.exhibitions.first.models.Post;
import com.exhibitions.first.models.User;
import com.exhibitions.first.models.util.PostHelper;

public class PostDto {
    private Long id;
    private String title;
    private String full_text;
    private String filename, anons;
    private User author;
    private Long orders;
    private boolean meOrdered;

    public String getAuthorName(){
        return PostHelper.getAuthorName(author);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFull_text() {
        return full_text;
    }

    public String getFilename() {
        return filename;
    }

    public String getAnons() {
        return anons;
    }

    public User getAuthor() {
        return author;
    }

    public Long getOrders() {
        return orders;
    }

    public boolean isMeOrdered() {
        return meOrdered;
    }

    public PostDto(Post post, Long orders, boolean meOrdered) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.full_text = post.getFull_text();
        this.filename = post.getFilename();
        this.anons = post.getAnons();
        this.author = post.getAuthor();
        this.orders = orders;
        this.meOrdered = meOrdered;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", author=" + author +
                ", orders=" + orders +
                ", meOrdered=" + meOrdered +
                '}';
    }
}
