package com.github.todolist;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String password;
    private boolean enabled;

    public String getUsername() {
        return this.username;
    }

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
    }
}
