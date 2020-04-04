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

    @OneToMany(mappedBy = "image")
    private List<Article> articles;

    public Image() {
    }

    public Image(String path) {
        this.path = path;
    }

    public int getId() {
        return this.id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
