package org.levelup.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "articles")
@ToString
public class Article {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false)
    private String text;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date creationDate = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    private Image image;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Category category;

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
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
