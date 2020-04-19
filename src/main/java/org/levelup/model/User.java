package org.levelup.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false, length = 25)
    @Size(min = 4, max = 25)
    @Pattern(regexp = "[a-zA-Z-_.0-9]*",
            message = "You can use only letters, digits, underscore, minus sign and dots")
    private String login;

    @Size(min = 3, max = 70)
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date creationDate = new Date();

    @ManyToOne(optional = false)
    private Role role;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User() {}

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
