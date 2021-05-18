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

import com.alpha.demo.Repository.DealerRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.CreditDealerAmo;
import com.alpha.demo.model.Dealers;
import com.alpha.demo.Repository.CreditDealerAmoRepository;

@RestController
@RequestMapping("/creditDealerAmo")
public class CreditDealerAmoController {
	@Autowired
	private DealerRepository dealerRepository;
	@Autowired
	private CreditDealerAmoRepository creditDealerAmoRepository;
	@GetMapping("/showDealerCr")
	public List<CreditDealerAmo> showDealerCr() {
		return creditDealerAmoRepository.findAll();
		 
	}
	@GetMapping("/getCreditDealerAmoView/{id}")
	public ModelAndView GetCreditDealerAmo(@PathVariable("id") long id,  @ModelAttribute @Valid @RequestBody CreditDealerAmo dAmo,Model model) {
		ModelAndView mv = new ModelAndView("creditDealerAmo.html");
		Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + id));
		long count_CreditDealerAmo = ds.getCreditDealerAmo().size();
		model.addAttribute("count_CreditDealerAmo", count_CreditDealerAmo);
		model.addAttribute("ds", ds);
		model.addAttribute("creditDealerAmo", dAmo);
		List<CreditDealerAmo> d = creditDealerAmoRepository.findByDealersId(id);
		model.addAttribute("d", d);
		return mv;
	}
	@PostMapping(value="/AddCreditDealerAmo/{id}",produces="application/json",consumes="application/json")
	@ResponseBody
	public ModelAndView AddCreditDealerAmo(@PathVariable Long id,@Valid @RequestBody CreditDealerAmo dAmo) {
		ModelAndView mv = new ModelAndView("redirect:/creditDealerAmo/getCreditDealerAmoView/{id}");
		return dealerRepository.findById(id).map(dealers -> {
			dAmo.setDealers(dealers);
			creditDealerAmoRepository.save(dAmo); 
			return mv;
			}).orElseThrow(() -> new NotFoundException("Dealer not found!"));
		
	    }
	@GetMapping("/getDealerAmo/{id}")
	public List<CreditDealerAmo> GetDealerAmo(@PathVariable("id") long id,Model model) {
		List<CreditDealerAmo> c = creditDealerAmoRepository.findByDealersId(id);
		return c;
	}
	@GetMapping("/GetCreditDealerAmo/cr/{crId}")
	public Optional<CreditDealerAmo> GetCreditDealerAmo(@PathVariable Long crId,Model model) {
		Optional<CreditDealerAmo> cr = creditDealerAmoRepository.findById(crId);
		return cr;
		
	}
	@GetMapping("/editCreditDealerAmo/{id}/cr/{crId}")
	public ModelAndView editCreditDealerAmo(@PathVariable("id") long id,@PathVariable Long crId,Model model) {
		ModelAndView mv = new ModelAndView("editCreditDealerAmo.html");
		Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("ds", ds);
		model.addAttribute("cr", crId);
		return mv;
	}
	@PostMapping(value="/updateCreditDealerAmo/{id}/cr/{crId}",produces="application/json",consumes="application/json")
	public ModelAndView updateCreditDealerAmo(@PathVariable("id") long id,@PathVariable Long crId,Model model,@Valid @RequestBody CreditDealerAmo dAmo) {
		ModelAndView mv = new ModelAndView("redirect:/creditDealerAmo/getCreditDealerAmoView/{id}");
		return creditDealerAmoRepository.findById(crId).map(creditDealerAmo -> {
			creditDealerAmo.setCreditAmount(dAmo.getCreditAmount());
			creditDealerAmo.setStatus(dAmo.getStatus());
			creditDealerAmo.setNote(dAmo.getNote());
			creditDealerAmoRepository.save(creditDealerAmo); 
			return mv;
			}).orElseThrow(() -> new NotFoundException("Dealer not found!"));
	}
	@GetMapping("/deleteCreditDealerAmo/{id}/cr/{crId}")
	public ModelAndView deleteCreditDealerAmo(@PathVariable("id") long id,@PathVariable Long crId,Model model) {
		creditDealerAmoRepository.deleteById(crId);
		ModelAndView mv = new ModelAndView("redirect:/creditDealerAmo/getCreditDealerAmoView/{id}");
		return mv;
	}


}
