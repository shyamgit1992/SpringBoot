package com.alpha.demo.security.main;

import javax.validation.Valid;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RestController
public class MySecureController {
	
//	@RequestMapping("/")
//	public ModelAndView main() {
//		ModelAndView mv = new ModelAndView("index.html");
//		return mv;
//	}
	@RequestMapping("/")
    public ModelAndView  index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
        	ModelAndView mv = new ModelAndView("home.html");
    		return mv;
        }
           

        // if it is not authenticated, then go to the index...
        // other things ...
        ModelAndView mv = new ModelAndView("index.html");
		return mv;
    }
	@RequestMapping("/login")
   public  ModelAndView login(){
		ModelAndView mv = new ModelAndView("login.html");
		return mv;
		}
	@RequestMapping("/accessdenied")
	public ModelAndView accessdenied(Model model) {
    	String accessDenied = "Access Denied"; 
    	model.addAttribute("accessDenied",accessDenied);
		return new ModelAndView("accessdenied.html");
	}
   

}
