package com.myTwitter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.myTwitter.model.Message;
import com.myTwitter.model.Role;
import com.myTwitter.model.User;
import com.myTwitter.repository.MessageRepository;
import com.myTwitter.repository.RoleRepository;
import com.myTwitter.repository.UserRepository;

@SpringBootApplication
public class MyTwitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTwitterApplication.class, args);
	}
}
