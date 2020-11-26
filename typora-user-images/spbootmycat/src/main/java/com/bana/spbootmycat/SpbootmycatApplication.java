package com.bana.spbootmycat;

import com.bana.spbootmycat.entity.User;
import com.bana.spbootmycat.service.Userservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpbootmycatApplication {

    public static void main(String[] args) {

       ApplicationContext ac =SpringApplication.run(SpbootmycatApplication.class, args);
        Userservice service=(Userservice) ac.getBean("userservice");
        //service.addUser(new User(2,"lisi"));
        service.selectAll();
    }

}
