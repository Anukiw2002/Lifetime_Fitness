package org.example.demo2.model;

public class VideoModel {
    private int id;
    private String name;
    private String description;
    private String url;
    private byte[] image;

    public VideoModel(int id, String name, String description, String url,byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
