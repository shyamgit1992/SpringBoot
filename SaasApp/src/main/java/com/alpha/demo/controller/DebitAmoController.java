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
import com.alpha.demo.Repository.DebitAmoRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.Customer;
import com.alpha.demo.model.DebitAmo;

@RestController
@RequestMapping("/debitAmo")
public class DebitAmoController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private DebitAmoRepository debitAmoRepository;
	@GetMapping("/showDr")
	public List<DebitAmo>showDr() {
		return debitAmoRepository.findAll();
		 
	}
	@GetMapping("/getDebitAmoView/{id}")
	public ModelAndView GetDebitAmo(@PathVariable("id") long id,  @ModelAttribute @Valid @RequestBody DebitAmo dAmo,Model model) {
		ModelAndView mv = new ModelAndView("debitAmo.html");
		Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		long count_DebitAmo = ct.getDebitAmo().size();
		model.addAttribute("ct", ct);
		model.addAttribute("debitAmo", dAmo);
		model.addAttribute("count_DebitAmo", count_DebitAmo);
		List<DebitAmo> d = debitAmoRepository.findByCustomerId(id);
		System.out.println(d);
		model.addAttribute("d", d);
		return mv;
	}
	@PostMapping(value="/AddDebitAmo/{id}",produces="application/json",consumes="application/json")
	@ResponseBody
	public ModelAndView AddDebitAmo(@PathVariable Long id,@Valid @RequestBody DebitAmo dAmo) {
		ModelAndView mv = new ModelAndView("redirect:/DebitAmo/getDebitAmoView/{id}");
		return customerRepository.findById(id).map(customer -> {
			dAmo.setCustomer(customer);
			debitAmoRepository.save(dAmo); 
			return mv;
			}).orElseThrow(() -> new NotFoundException("Customer not found!"));
		
	    }
	@GetMapping("/getAmo/{id}")
	public List<DebitAmo> GetAmo(@PathVariable("id") long id,Model model) {
		List<DebitAmo> d = debitAmoRepository.findByCustomerId(id);
		return d;
	}
	@GetMapping("/GetDebitAmo/db/{dbId}")
	public Optional<DebitAmo> GetDebitAmo(@PathVariable Long dbId,Model model) {
		Optional<DebitAmo> db = debitAmoRepository.findById(dbId);
		return db;
		
	}
	@GetMapping("/editDebitAmo/{id}/db/{dbId}")
	public ModelAndView editDebitAmo(@PathVariable("id") long id,@PathVariable Long dbId,Model model) {
		ModelAndView mv = new ModelAndView("editDebitAmo.html");
		Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("ct", ct);
		model.addAttribute("db", dbId);
		return mv;
	}
	@PostMapping(value="/updateDebitAmo/{id}/db/{dbId}",produces="application/json",consumes="application/json")
	public ModelAndView updateDebitAmo(@PathVariable("id") long id,@PathVariable Long dbId,Model model,@Valid @RequestBody DebitAmo dAmo) {
		ModelAndView mv = new ModelAndView("redirect:/debitAmo/getDebitAmoView/{id}");
		return debitAmoRepository.findById(dbId).map(debitAmo -> {
			debitAmo.setDebitAmount(dAmo.getDebitAmount());
			debitAmo.setDebitInt(dAmo.getDebitInt());
			debitAmo.setStatus(dAmo.getStatus());
			debitAmo.setNote(dAmo.getNote());
			debitAmoRepository.save(debitAmo); 
			return mv;
			}).orElseThrow(() -> new NotFoundException("Customer not found!"));
	}
	@GetMapping("/deleteDebitAmo/{id}/db/{dbId}")
	public ModelAndView deleteDebitAmo(@PathVariable("id") long id,@PathVariable Long dbId,Model model) {
        debitAmoRepository.deleteById(dbId);
		ModelAndView mv = new ModelAndView("redirect:/debitAmo/getDebitAmoView/{id}");
		return mv;
	}



}
