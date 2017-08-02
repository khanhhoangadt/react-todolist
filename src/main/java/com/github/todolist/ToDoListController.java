package com.github.todolist;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import java.security.Principal;
import java.util.List;

@Controller
public class ToDoListController {

    @Autowired
    @Transient
    ToDoListRepository listRepo;

    @Autowired
    @Transient
    ToDoRepository todoRepo;

    @RequestMapping(value = "/api/lists", method=RequestMethod.GET)
    public @ResponseBody List<ToDoList> getLists(Principal principal) {
        String username = principal.getName();
        List<ToDoList> lists = listRepo.findByUserUsername(username);
        return lists;
    }

    @RequestMapping(value = "/api/lists/{id}", method = RequestMethod.GET)
    public @ResponseBody List<ToDo> showList(@PathVariable("id") long id, Principal principal) {
        ToDoList list = listRepo.findById(id);

        if (isAuthorized(list, principal)) {
            List<ToDo> todos = todoRepo.findByListId(list.getId());
            return todos;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/api/lists/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deleteList(@PathVariable("id") long id, Principal principal) {
        ToDoList list = listRepo.findById(id);
        if (isAuthorized(list, principal)) {
            List<ToDo> todos = todoRepo.findByListId(list.getId());
            for (ToDo todo : todos) {
                todoRepo.delete(todo);
            }
            listRepo.delete(list);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isAuthorized(ToDoList list, Principal principal) {
        String username = principal.getName();
        if (list == null || !list.getUser().getUsername().equals(username)) {
            return false;
        } else {
            return true;
        }
    }
}
