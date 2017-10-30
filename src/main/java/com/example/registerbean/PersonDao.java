package com.example.registerbean;


import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * @author 李佳明 https://github.com/pkpk1234/
 * @date 2017-10-30
 */
@Component
public class PersonDao {
    private static final Random RANDOM = new Random();
    public Person createDemoPerson() {
        String name = UUID.randomUUID().toString();
        int age = RANDOM.nextInt(100);
        return new Person(name,age);
    }
}
