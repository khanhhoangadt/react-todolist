package com.github.todolist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoListTests {

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
    public void newList() {
        ToDoList newList = new ToDoList("Test ToDo List", testUser);
        toDoListRepo.save(newList);

        assertThat(newList.getId(), is(notNullValue()));
        assertThat(newList.getUser().getUsername(), is(equalTo(testUser.getUsername())));
    }
}
