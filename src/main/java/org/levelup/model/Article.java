package org.levelup.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false)
    private String text;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Image> images;

    /* TODO: потом указать nullable = false */
    @ManyToOne
    private Category category;

    public int getId() {
        return this.id;
    }

    public String getHeader() {
        return this.header;
    }

    public String getText() {
        return this.text;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
