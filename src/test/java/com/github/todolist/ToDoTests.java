package com.github.todolist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoTests {

    @Autowired
    private ToDoRepository toDoRepo;

    @Autowired
    private ToDoListRepository toDoListRepo;

    private User testUser;
    private ToDoList testList;

    @Before
    public void setup() {
        testList = toDoListRepo.findOne(1L);
        testUser = testList.getUser();
    }

    @Test
    public void newToDo() {
        ToDo toDo = new ToDo("Test ToDo");
        toDo.setList(testList);
        assertThat(toDo.getId(), is(notNullValue()));
        assertThat(toDo.getDescription(), is("Test ToDo"));

        toDoRepo.save(toDo);
    }

    @Test
    public void lookupToDo() {
        ToDo toDo = toDoRepo.findById(2L);

        assertThat(toDo, is(notNullValue()));

        assertThat(toDo.getId(), is(2L));
        assertThat(toDo.getDescription(), is(equalTo("Do another thing")));
        assertThat(toDo.getCompleted(), is(true));
    }

    @Test
    public void updateToDo() {
        ToDo toDo = new ToDo("This will be updated");
        toDo.setList(testList);
        toDo.setCompleted(false);
        toDoRepo.save(toDo);

        toDo.setCompleted(true);
        toDo.setDescription("This has been updated");
        toDoRepo.save(toDo);

        ToDo lookupToDo = toDoRepo.findById(toDo.getId());
        assertThat(lookupToDo.getDescription(), is(equalTo("This has been updated")));
        assertThat(lookupToDo.getCompleted(), is(true));
    }

    @Test
    public void deleteToDo() {
        ToDo toDo = new ToDo("This will be deleted");
        toDo.setList(testList);
        toDoRepo.save(toDo);
        Long toDoID = toDo.getId();

        ToDo lookupToDo = toDoRepo.findById(toDoID);
        assertThat(lookupToDo, is(notNullValue()));

        toDoRepo.delete(toDoID);
        lookupToDo = toDoRepo.findById(toDoID);
        assertThat(lookupToDo, is(nullValue()));
    }
}
