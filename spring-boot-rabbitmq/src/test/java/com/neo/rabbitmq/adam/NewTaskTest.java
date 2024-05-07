package com.neo.rabbitmq.adam;

import com.neo.adam.NewTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewTaskTest {
    @Autowired
    private NewTask newTask;

    @Test
    public void newTaskSend() throws IOException {
        newTask.send();
    }
}
