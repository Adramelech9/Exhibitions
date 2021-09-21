package com.exhibitions.first.models.util;

import com.exhibitions.first.models.User;

public abstract class PostHelper {
    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "_";
    }
}
