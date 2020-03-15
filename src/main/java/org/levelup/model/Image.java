package org.levelup.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String path;

    @ManyToMany
    private List<Article> articles;

    public int getId() {
        return this.id;
    }

    public String getPath() {
        return this.path;
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
