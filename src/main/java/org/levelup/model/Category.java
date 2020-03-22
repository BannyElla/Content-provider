package org.levelup.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private VisibilityType visible = VisibilityType.PRIVATE;

    @OneToMany
    private List<Article> articles;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public VisibilityType getVisibility() { return this.visible; }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisibility(VisibilityType visible) { this.visible = visible; }
}
