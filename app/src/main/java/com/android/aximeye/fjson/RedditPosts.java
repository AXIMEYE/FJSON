package com.android.aximeye.fjson;

import java.util.ArrayList;

public class RedditPosts {
    ArrayList<Post> posts;

    public RedditPosts() {
        posts = new ArrayList<Post>();
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
}
