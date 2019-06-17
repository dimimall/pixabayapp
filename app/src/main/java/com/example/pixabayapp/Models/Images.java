package com.example.pixabayapp.Models;

import java.io.Serializable;

public class Images implements Serializable {

    private String url;
    private String id;
    private String large_url;

    public Images(String url, String id, String large_url)
    {
        this.url = url;
        this.id = id;
        this.large_url = large_url;
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getId()
    {
        return this.id;
    }

    public String getLarge_url()
    {
        return this.large_url;
    }
}
