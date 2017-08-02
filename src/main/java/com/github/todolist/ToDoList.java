package com.github.todolist;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "todolists")
public class ToDoList {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
    private List<ToDo> todos;

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) { this.user = user; }

    public List<ToDo> getToDos() { return this.todos; }

    public ToDoList() {}

    public ToDoList(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
