package com.github.todolist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import java.security.Principal;

@Controller
public class ToDoController {

    @Autowired
    @Transient
    ToDoRepository todoRepo;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/api/todos/{id}", method = RequestMethod.GET)
    public @ResponseBody ToDo getTodo(@PathVariable("id") long id, Principal principal) {
        log.info("Get ToDo " + id);
        ToDo todo = todoRepo.findById(id);
        if (isAuthorized(todo, principal)) {
            return todo;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/api/todos/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deleteToDo(@PathVariable("id") long id, Principal principal) {
        log.info("Delete ToDo" + id);
        ToDo todo = todoRepo.findById(id);
        if (isAuthorized(todo, principal)) {
            todoRepo.delete(todo);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/todos/{id}", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity updateToDo(@PathVariable("id") long id, @RequestBody ToDo input, Principal principal) {
        log.info("Updating ToDo " + id);
        ToDo todo = todoRepo.findById(id);
        if (isAuthorized(todo, principal)) {
            log.info(input.toString());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isAuthorized(ToDo todo, Principal principal) {
        String username = principal.getName();
        log.info("Authorizing " + username);
        if (todo == null || todo.getOwner() == null || !todo.getOwner().getUsername().equals(username)) {
            log.info("Not Authorized");
            return false;
        }

        log.info("Authorized");
        return true;
    }
}
