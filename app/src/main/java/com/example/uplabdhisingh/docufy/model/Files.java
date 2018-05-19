package com.example.uplabdhisingh.docufy.model;

public class Files
{
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Files() {
    }

    public Files(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
