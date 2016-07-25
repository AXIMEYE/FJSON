package com.android.aximeye.fjson;

import java.util.ArrayList;
import java.util.List;

public class RedditPosts {
    List<Post> posts;

    public RedditPosts() {
        posts = new ArrayList<Post>();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
