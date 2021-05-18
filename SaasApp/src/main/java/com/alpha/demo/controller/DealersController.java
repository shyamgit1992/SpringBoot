package com.alpha.demo.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import com.alpha.demo.Repository.DealerRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.Dealers;

@RestController
@RequestMapping("/deal")
public class DealersController {
	@Autowired
	private DealerRepository dealerRepository;
	@GetMapping("/dealer")
    public List<Dealers> getAllDealers() {
    	return dealerRepository.findAll();
   }
	@GetMapping("/dealers")
    public ModelAndView getAllDealers(Dealers dealer, Model model) {
    	ModelAndView mv = new ModelAndView("showDealers.html");
    	long count_Deal = dealerRepository.count();
    	model.addAttribute("customer", dealerRepository.findAll());
    	model.addAttribute("count_Deal", count_Deal);
    	return mv;
    }
	
	 @GetMapping("/dealers/{id}")
	    public Dealers getDealersByID(@PathVariable Long id) {
	    	Optional<Dealers> optDealer = dealerRepository.findById(id);
	    	if(optDealer.isPresent()) {
	    		return optDealer.get();
	    	}else {
	    		throw new NotFoundException("Dealer not found with id " + id);
	    	}
	    }
	 
	 @PostMapping("/dealersAdd")
	    public ModelAndView createCustomer(@ModelAttribute @Valid @RequestBody Dealers dealer, Model model) throws IOException {
		 ModelAndView mv = new ModelAndView("redirect:/deal/dealers");
		 if(dealer.getAadhaarNo().isEmpty()) {
        	 dealer.setAadhaarNo("None");
        	
         }
         if( dealer.getDealerShipDetails().isEmpty()) {
        	 dealer.setDealerShipDetails("None");
         } 
         if( dealer.getGstnNo().isEmpty()) {
        	 dealer.setGstnNo("None");
         } 
         if( dealer.getBankName().isEmpty()) {
        	 dealer.setBankName("None");
         } 
         if( dealer.getBankAccNo().isEmpty()) {
        	 dealer.setBankAccNo("None");
         } 
         if( dealer.getBankAccOnName().isEmpty()) {
        	 dealer.setBankAccOnName("None");
         } 
			/*
			 * if( dealer.getBankAccType()=="") { dealer.setBankAccType("None"); }
			 */
         if( dealer.getBankIfscCode().isEmpty()) {
        	 dealer.setBankIfscCode("None");
         } 
         if(dealer.getMobileNo().isEmpty()) {
        	 dealer.setMobileNo(Integer.toString(0));
         } 
         if(dealer.getNote().isEmpty()) {
        	 dealer.setNote("None");
         }
		 dealerRepository.save(dealer);
		 model.addAttribute("dealer", dealer);
		 return mv;
		 }
	 @GetMapping("/edit/{id}")
	    public ModelAndView  showDealerUpdateForm(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody Dealers dealers,Model model) {
	    	ModelAndView mv = new ModelAndView("dealerUpdateForm.html");
	    	Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + id));
	    	System.out.println(ds.getId());
	    	System.out.println(ds.getFullName());
	    	model.addAttribute("ds", ds);
	        return mv;
	    }
	 @PostMapping("/dealersUpdate/{id}")
	    public ModelAndView updateCustomer(@PathVariable Long id, @Valid Dealers dealerUpdated) {
	        return dealerRepository.findById(id)
	                .map(dealer -> {
	                	dealer.setFullName(dealerUpdated.getFullName());
	                	dealer.setAadhaarNo(dealerUpdated.getAadhaarNo());
	                	dealer.setBankAccNo(dealerUpdated.getBankAccNo());
	                	dealer.setBankAccOnName(dealerUpdated.getBankAccOnName());
	                	dealer.setBankAccType(dealerUpdated.getBankAccType());
	                	dealer.setBankIfscCode(dealerUpdated.getBankIfscCode());
	                	dealer.setBankName(dealerUpdated.getBankName());
	                	dealer.setDealerShipDetails(dealerUpdated.getDealerShipDetails());
	                	dealer.setGstnNo(dealerUpdated.getGstnNo());
	                	dealer.setMobileNo(dealerUpdated.getMobileNo());
	                	dealer.setAddress(dealerUpdated.getAddress());
	                	dealer.setNote(dealerUpdated.getNote());
	                    ModelAndView mv = new ModelAndView("redirect:/deal/dealers");
	                    dealerRepository.save(dealer);
	                    return mv;
	                }).orElseThrow(() -> new NotFoundException("Dealer not found with id " + id));
	    }
	 @RequestMapping(value = "/dealersDel/{id}", method = RequestMethod.GET)
	    public ModelAndView deleteDealer(@PathVariable Long id) {
	        return dealerRepository.findById(id)
	                .map(dealer -> {
	                    dealerRepository.delete(dealer);
	                    ModelAndView mv = new ModelAndView("redirect:/deal/dealers");
	                    return mv;
	                }).orElseThrow(() -> new NotFoundException("Dealer not found with id " + id));
	    }
	    	 

}
