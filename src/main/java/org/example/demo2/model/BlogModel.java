package org.example.demo2.model;

public class BlogModel {
    private int id;
    private String name;
    private String description;
    private String content;

    // Constructors
    public BlogModel() {}

    public BlogModel(int id, String name, String description, String link) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.content = content;
    }

    // Getters and Setters
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

    public void setLink(String link) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BlogModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
