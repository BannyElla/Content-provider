package org.levelup.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String login;
    @Column
    private String password;

    /* TODO: потом указать nullable = false */
    @ManyToOne
    private Role role;

    public int getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }

    public void setLogin(String login) {
        if (this.login != null) {
            throw new IllegalArgumentException("Login has been already assigned: " + this.login + ". You can't change it.");
        }
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
