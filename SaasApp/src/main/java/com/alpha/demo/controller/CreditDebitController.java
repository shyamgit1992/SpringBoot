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

import com.alpha.demo.model.Credit;
import com.alpha.demo.model.Debit;
import com.alpha.demo.model.Product;
import com.alpha.demo.Repository.CreditRepository;
import com.alpha.demo.Repository.DebitRepository;

@RestController
@RequestMapping("/creditDebitAmo")
public class CreditDebitController {
	@Autowired
	private CreditRepository creditRepository;
	@Autowired
	private DebitRepository debitRepository;
	@GetMapping("/showCredit")
	public List<Credit> showCredit() {
		return creditRepository.findAll();
		 
	}
	@GetMapping("/showDebit")
	public List<Debit> showDebit() {
		return debitRepository.findAll();
		 
	}
	@GetMapping("/getCreditView")
	public ModelAndView GetCreditAmo(Model model, @ModelAttribute @Valid @RequestBody Credit credit) {
		ModelAndView mv = new ModelAndView("showCredit.html");
		long count_Credit = creditRepository.findAll().size();
		model.addAttribute("count_Credit", count_Credit);
		model.addAttribute("credit",credit);
		return mv;
	}
	@GetMapping("/getDebitView")
	public ModelAndView GetDebitAmo(Model model, @ModelAttribute @Valid @RequestBody Debit debit) {
		ModelAndView mv = new ModelAndView("showDebit.html");
		long count_Debit = debitRepository.findAll().size();
		model.addAttribute("count_Debit", count_Debit);
		model.addAttribute("debit",debit);
		return mv;
	}
	@PostMapping(value="/AddCreditAmo")
	public ModelAndView AddCredit(@ModelAttribute @Valid @RequestBody Credit cAmo) {
		creditRepository.save(cAmo);
		ModelAndView mv = new ModelAndView("redirect:/creditDebitAmo/getCreditView");
		return mv;
	    }
	@PostMapping(value="/AddDebitAmo")
	public ModelAndView AddDebit(@ModelAttribute @Valid  @RequestBody Debit dAmo) {
		debitRepository.save(dAmo);
		ModelAndView mv = new ModelAndView("redirect:/creditDebitAmo/getDebitView");
		return mv;
	    }

	@GetMapping("/editCreditAmo/{id}")
	public ModelAndView editCreditAmo(@PathVariable("id") long id,Model model) {
		ModelAndView mv = new ModelAndView("editCredit.html");
		//Credit cr = creditRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		//model.addAttribute("cr", cr);
		Optional<Credit> credit = creditRepository.findById(id);
		model.addAttribute("credit",credit);
		return mv;
	}
	@GetMapping("/editDebitAmo/{id}")
	public ModelAndView editDebitAmo(@PathVariable("id") long id,Model model) {
		ModelAndView mv = new ModelAndView("editDebit.html");
		//Debit dr = debitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		//model.addAttribute("dr", dr);
		Optional<Debit> debit = debitRepository.findById(id);
		model.addAttribute("debit",debit);
		return mv;
	}
	@PostMapping(value="/updateCreditAmo/{id}")
	public ModelAndView updateCreditAmo(@PathVariable("id") long id,Model model,@ModelAttribute @Valid  @RequestBody Credit cAmo) {
		ModelAndView mv = new ModelAndView("redirect:/creditDebitAmo/getCreditView");
		Credit cr = creditRepository.getOne(id);
		System.out.println(cr);
		cr.setCredit(cAmo.getCredit());
		cr.setNote(cAmo.getNote());
		creditRepository.save(cr);
		return mv;
	}
	@PostMapping(value="/updateDebitAmo/{id}")
	public ModelAndView updateDebitAmo(@PathVariable("id") long id,Model model,@ModelAttribute @Valid  @RequestBody Debit dAmo) {
		ModelAndView mv = new ModelAndView("redirect:/creditDebitAmo/getDebitView");
		Debit dr = debitRepository.getOne(id);
		System.out.println(dr);
		dr.setDebit(dAmo.getDebit());
		dr.setNote(dAmo.getNote());
		debitRepository.save(dr);
		return mv;
	}
	@GetMapping("/deleteCreditAmo/{id}")
	public ModelAndView deleteCreditAmo(@PathVariable("id") long id,Model model) {
		creditRepository.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/creditDebitAmo/getCreditView");
		return mv;
	}
	@GetMapping("/deleteDebitAmo/{id}")
	public ModelAndView deleteDebitAmo(@PathVariable("id") long id,Model model) {
		debitRepository.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/creditDebitAmo/getDebitView");
		return mv;
	}


}
