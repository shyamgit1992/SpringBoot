package com.alpha.demo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alpha.demo.Repository.BillRepository;
import com.alpha.demo.Repository.BuyBillRepository;
import com.alpha.demo.Repository.CustomerRepository;
import com.alpha.demo.Repository.TaxRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.Bill;
import com.alpha.demo.model.BuyBill;
import com.alpha.demo.model.Customer;
import com.alpha.demo.model.Tax;



@RestController
@RequestMapping("/tax")
public class TaxController {
	@Autowired
	private TaxRepository taxRepository;
	@PostMapping("/addTax")
	public ModelAndView addTax(@ModelAttribute @Valid @RequestBody Tax tax) {
		taxRepository.save(tax);
		ModelAndView mv = new ModelAndView("redirect:/tax/showTaxes");
		return mv;
	}
	@GetMapping("/taxes")
	public List<Tax> getTax(@ModelAttribute @Valid @RequestBody Tax tax) {
		return taxRepository.findAll();
		 
	}
	@GetMapping("/deleteTax/{id}")
	public ModelAndView DeleteTax(@PathVariable Long id,@ModelAttribute @Valid @RequestBody Tax tax) {
		taxRepository.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/tax/showTaxes");
		return mv;
	}
	@GetMapping("/showTaxes")
	public ModelAndView showTax(@ModelAttribute @Valid @RequestBody Tax tax,Model model) {
		ModelAndView mv = new ModelAndView("showTaxes.html");
		long count_Tax = taxRepository.findAll().size();
		model.addAttribute("count_Tax", count_Tax);
		model.addAttribute("tax",tax);
		return mv;
		}
	@GetMapping("/editTax/{id}")
	public ModelAndView Editax(@PathVariable Long id,Model model) {
		ModelAndView mv = new ModelAndView("editTaxes.html");
		Optional<Tax> tax = taxRepository.findById(id);
		model.addAttribute("tax",tax);
		return mv;
	}
	@PostMapping("/updateTax/{id}")
	public ModelAndView UpdateTax(@PathVariable Long id,@ModelAttribute @Valid @RequestBody Tax tax,Model model) {
		ModelAndView mv = new ModelAndView("redirect:/tax/showTaxes");
		Tax tx = taxRepository.getOne(id);
		System.out.println(tx);
		tx.setGst(tax.getGst());
		taxRepository.save(tx);
		return mv;
	}
}
	
	


