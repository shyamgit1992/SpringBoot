package com.alpha.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alpha.demo.controller.CustomerController;



@SpringBootApplication
public class SaasAppApplication {

	public static void main(String[] args) {
		new File(CustomerController.UPLOAD_DIR).mkdir();
		SpringApplication.run(SaasAppApplication.class, args);
	}

}
