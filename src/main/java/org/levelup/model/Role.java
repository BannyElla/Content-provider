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
    private RoleName name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> users;

    public Role(RoleName name) {
        this.name = name;
    }
    public Role() {
    }

    public int getId() {
        return this.id;
    }

    public RoleName getName() {
        return this.name;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
