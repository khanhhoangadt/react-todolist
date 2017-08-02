package com.github.todolist;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToDoListRepository extends CrudRepository<ToDoList, Long> {

    public ToDoList findById(Long id);
    public List<ToDoList> findByUserUsername(String username);
}
