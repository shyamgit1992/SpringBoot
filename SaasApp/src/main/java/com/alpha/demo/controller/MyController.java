package com.alpha.demo.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alpha.demo.model.Customer;
import com.alpha.demo.model.Dealers;

@Controller
@RequestMapping("home")
public class MyController {
	
	@RequestMapping("/homes")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home.html");
		return mv;
	}
	@RequestMapping("/c")
	public ModelAndView c() {
		ModelAndView mv = new ModelAndView("c.html");
		return mv;
	}
	
	
	@RequestMapping("/AddCustomerForm")
	public ModelAndView AddCustomertForm(@ModelAttribute @Valid @RequestBody Customer customer, Model model) {
		ModelAndView mv = new ModelAndView("customerForm.html");
		model.addAttribute("customer", customer);
		return mv;
	}
	@RequestMapping("/AddDealersForm")
	public ModelAndView AddDealersForm(@ModelAttribute @Valid @RequestBody Dealers dealers, Model model) {
		ModelAndView mv = new ModelAndView("dealersForm.html");
		model.addAttribute("dealers", dealers);
		return mv;
	}
	
	@RequestMapping("/AddStudentAssignmentForm")
	public ModelAndView AddStudentAssignmentForm() {
		ModelAndView mv = new ModelAndView("assignmentForm.html");
		return mv;
	}
	
	@RequestMapping("/AddCustomerBuyBillForm")
	public ModelAndView AddCustomerBuyBillForm() {
		ModelAndView mv = new ModelAndView("BuyBillForm.html");
		return mv;
	}
	
	 
	 
	/*
	 * @RequestMapping("/SearchBuyBillById") public ModelAndView
	 * SearchBuyBillById(@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model
	 * model) { ModelAndView mv = new ModelAndView("searchBuyBillById.html");
	 * model.addAttribute("buyBill", buyBill); return mv; }
	 */
	 
	 @RequestMapping("/notFound")
		public ModelAndView notFound() {
		 ModelAndView mv = new ModelAndView("notFound.html");
			return mv;
		}
		
		
     
     @RequestMapping("/AddCustomerBuyBillForms")
 	public ModelAndView AddCustomerBuyBillForms(Model model) {
 		ModelAndView mv = new ModelAndView("BuyBillForms.html");
 		//Bill bill = new Bill();
 		model.addAttribute("invoiceNo", "1");
 		return mv;
 	}	 
     
     @RequestMapping("/Bill")
  	public ModelAndView Bill() {
  		ModelAndView mv = new ModelAndView("Bill.html");
  		//Bill bill = new Bill();
  		//model.addAttribute("invoiceNo", "1");
  		return mv;
  	}	 
     @RequestMapping("/sBill")
   	public ModelAndView sBill() {
   		ModelAndView mv = new ModelAndView("sBills.html");
   		//Bill bill = new Bill();
   		//model.addAttribute("invoiceNo", "1");
   		return mv;
   	}	 
     
     @RequestMapping("/check")
   	public ModelAndView check() {
   		ModelAndView mv = new ModelAndView("check.html");
   		//Bill bill = new Bill();
   		//model.addAttribute("invoiceNo", "1");
   		return mv;
   	}	 
     @RequestMapping("/checkDir")
    	public ModelAndView checkDir() {
    		ModelAndView mv = new ModelAndView("dirPagination.tpl.html");
    		//Bill bill = new Bill();
    		//model.addAttribute("invoiceNo", "1");
    		return mv;
    	}	 
	 
	

}
