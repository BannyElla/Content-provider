package org.levelup.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false)
    private String text;

    @Temporal(TemporalType.DATE)
    private Date creationDate = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    private Image image;

    @ManyToOne(optional = false)
    private Category category;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return this.header;
    }

    public String getText() {
        return this.text;
    }

    public Image getImage() {
        return this.image;
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

    public void setImage(Image image) {
        this.image = image;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
