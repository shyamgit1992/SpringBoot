package com.alpha.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alpha.demo.Repository.CustomerRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.CreditAmo;
import com.alpha.demo.model.Customer;
import com.alpha.demo.Repository.CreditAmoRepository;

@RestController
@RequestMapping("/creditAmo")
public class CreditAmoController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CreditAmoRepository creditAmoRepository;
	@GetMapping("/showCr")
	public List<CreditAmo> showCr() {
		return creditAmoRepository.findAll();
		 
	}
	@GetMapping("/getCreditAmoView/{id}")
	public ModelAndView GetCreditAmo(@PathVariable("id") long id,  @ModelAttribute @Valid @RequestBody CreditAmo cAmo,Model model) {
		ModelAndView mv = new ModelAndView("creditAmo.html");
		Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		long count_CreditAmo = ct.getCreditAmo().size();
		model.addAttribute("ct", ct);
		model.addAttribute("creditAmo", cAmo);
		model.addAttribute("count_CreditAmo", count_CreditAmo);
		List<CreditAmo> c = creditAmoRepository.findByCustomerId(id);
		System.out.println(c);
		model.addAttribute("c", c);
		return mv;
	}
	@PostMapping(value="/AddCreditAmo/{id}",produces="application/json",consumes="application/json")
	@ResponseBody
	public ModelAndView AddCreditAmo(@PathVariable Long id,@Valid @RequestBody CreditAmo cAmo) {
		ModelAndView mv = new ModelAndView("redirect:/creditAmo/getCreditAmoView/{id}");
		return customerRepository.findById(id).map(customer -> {
			cAmo.setCustomer(customer);
			creditAmoRepository.save(cAmo); 
			return mv;
			}).orElseThrow(() -> new NotFoundException("Customer not found!"));
		
 }
//	@PostMapping("/AddCreditAmo/{id}")
//	public ModelAndView AddCreditAmo(@PathVariable Long id,@ModelAttribute @Valid @RequestBody CreditAmo cAmo) {
//		ModelAndView mv = new ModelAndView("redirect:/creditAmo/getCreditAmoView/{id}");
//		Customer myCustomer =  customerRepository.findById(id).get();
//	    return mv;
//		
//	    }
//	@PostMapping("/AddCreditAmo/{id}")
//	//@RequestMapping(value = "/AddCreditAmo/{id}", method = RequestMethod.POST)
//	@ResponseBody
//    public CreditAmo AddCreditAmo(@PathVariable Long id,
//    		@RequestBody CreditAmo cAmo) {
//     customerRepository.findById(id).get();
//     return null;
//      
//    }
	@GetMapping("/getAmo/{id}")
	public List<CreditAmo> GetAmo(@PathVariable("id") long id,Model model) {
		List<CreditAmo> c = creditAmoRepository.findByCustomerId(id);
		return c;
	}
	@GetMapping("/GetCreditAmo/cr/{crId}")
	public Optional<CreditAmo> GetCreditAmo(@PathVariable Long crId,Model model) {
		//ModelAndView mv = new ModelAndView("editCreditAmo.html");
		//Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		Optional<CreditAmo> cr = creditAmoRepository.findById(crId);
		return cr;
		
	}
	@GetMapping("/editCreditAmo/{id}/cr/{crId}")
	public ModelAndView editCreditAmo(@PathVariable("id") long id,@PathVariable Long crId,Model model) {
		ModelAndView mv = new ModelAndView("editCreditAmo.html");
		Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("ct", ct);
		model.addAttribute("cr", crId);
		return mv;
	}
	@PostMapping(value="/updateCreditAmo/{id}/cr/{crId}",produces="application/json",consumes="application/json")
	public ModelAndView updateCreditAmo(@PathVariable("id") long id,@PathVariable Long crId,Model model,@Valid @RequestBody CreditAmo cAmo) {
		ModelAndView mv = new ModelAndView("redirect:/creditAmo/getCreditAmoView/{id}");
		return creditAmoRepository.findById(crId).map(creditAmo -> {
			creditAmo.setCreditAmount(cAmo.getCreditAmount());
			creditAmo.setStatus(cAmo.getStatus());
			creditAmo.setNote(cAmo.getNote());
			creditAmoRepository.save(creditAmo); 
			return mv;
			}).orElseThrow(() -> new NotFoundException("Customer not found!"));
	}
	@GetMapping("/deleteCreditAmo/{id}/cr/{crId}")
	public ModelAndView deleteCreditAmo(@PathVariable("id") long id,@PathVariable Long crId,Model model) {
		creditAmoRepository.deleteById(crId);
		ModelAndView mv = new ModelAndView("redirect:/creditAmo/getCreditAmoView/{id}");
		return mv;
	}


}
