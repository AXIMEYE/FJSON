package com.android.aximeye.fjson;

public class Post {
    String title;
    String creator;
    // add variable to hold thumbnail

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return title + "\n" + "Author: " + creator;
    }
}
