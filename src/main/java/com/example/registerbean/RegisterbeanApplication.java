package com.example.registerbean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 李佳明 https://github.com/pkpk1234/
 * @date 2017-10-30
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class RegisterbeanApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterbeanApplication.class, args);
	}
}
