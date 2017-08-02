package com.github.todolist;

import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;

@RestController
@Entity
@Table(name = "todos")
public class ToDo {

    @Id
    @GeneratedValue
    private long id;
    private String description;
    private boolean completed = false;
    @ManyToOne
    @JoinColumn(name = "list_id")
    private ToDoList list;

    public ToDo() {}

    public ToDo(String description) {
        this.description = description;
        this.completed = false;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) { this.description = description; }

    public long getId() { return this.id; }

    public User getOwner() { return this.list.getUser(); }

    public void setList(ToDoList list) { this.list = list; }
}
