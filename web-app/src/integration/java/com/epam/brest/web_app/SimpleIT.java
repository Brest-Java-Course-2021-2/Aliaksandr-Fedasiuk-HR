package com.epam.brest.web_app;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class SimpleIT {

    private final Logger logger = LoggerFactory.getLogger(SimpleIT.class);

    @Autowired
    private ApplicationContext context;

    @Test
    void exampleTest(){
        logger.info("Hi, this is an integration test");
    }
}
