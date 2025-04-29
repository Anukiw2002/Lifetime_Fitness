package org.example.demo2.model;

public class BlogModel {
    private int id;
    private String name;
    private String description;
    private String content;
    private byte[] image;


    public BlogModel() {}

    public BlogModel(int id, String name, String description, String content, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.content = content;
        this.image = image;
    }


    public BlogModel(int id, String name, String description, String content) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "BlogModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", image=" + (image != null ? "[image present]" : "null") +
                '}';
    }
}
