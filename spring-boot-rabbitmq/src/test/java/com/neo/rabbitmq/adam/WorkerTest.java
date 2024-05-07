package com.neo.rabbitmq.adam;

import com.neo.adam.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkerTest {

    @Autowired
    private Worker worker;

    @Test
    public void workerTest() throws IOException, TimeoutException {
        worker.receiver();
    }
}
