package org.levelup.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    private UserRole name;

    @OneToMany
    private List<User> users;

    public Role(UserRole name) {
        this.name = name;
    }
    public Role() {
    }

    public int getId() {
        return this.id;
    }

    public UserRole getName() {
        return this.name;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setName(UserRole name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
