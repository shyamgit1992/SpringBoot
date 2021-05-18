package com.alpha.demo.security.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alpha.demo.security.model.User;
import com.alpha.demo.security.repository.UserRepository;

@Controller
@RequestMapping("/g")
public class HomeResource {
	private UserRepository userRepository;

    public HomeResource(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping("login")
    public String login(){return "login";}
    
    @GetMapping("/user")
    public String user() {
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("/admin")
    public String admin() {
        return ("<h1>Welcome Admin</h1>");
    }
    @GetMapping("users")
    public List<User> users(){
        return this.userRepository.findAll();
    }
}

