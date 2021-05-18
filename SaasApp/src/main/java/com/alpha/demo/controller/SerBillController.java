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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alpha.demo.Repository.CustomerRepository;
import com.alpha.demo.Repository.SerBillRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.ServiceSell;
import com.alpha.demo.model.Customer;
import com.alpha.demo.model.SerBill;
import com.alpha.demo.model.ServiceBill;

@RestController
@RequestMapping("/serBill")
public class SerBillController {
	@Autowired
	private SerBillRepository SerBillRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/showSerBill")
	public List<SerBill> showSerBill() {
		return SerBillRepository.findAll();
	}
	@GetMapping("/showAllSerBill")
	public ModelAndView showAllSerBill(Model model) {
		ModelAndView mv = new ModelAndView("showAllSerBill.html");
		long count_SerBill = SerBillRepository.findAll().size();
		model.addAttribute("count_SerBill", count_SerBill);
		List<SerBill> li = SerBillRepository.findAllOrderByDateAsc();
		model.addAttribute("li", li);
		return mv;
	}
	@GetMapping("/showSerBill/{id}")
	public List<SerBill> showSerBillByCustomerId(@PathVariable Long id) {
		
		return SerBillRepository.findByCustomerId(id);
	}
	@GetMapping("/showSerBillss/{id}")
	public Optional<SerBill> showSerBillByIds(@PathVariable Long id) {
		
		return SerBillRepository.findById(id);
	}
//	@GetMapping("/showServiceSellById/{id}")
//	public ServiceSell showServiceSellById(@PathVariable Long id) {
//		Optional<ServiceSell> optServiceSell =ServiceSellRepository.findById(id);
//    	if(optServiceSell.isPresent()) {
//    		return optServiceSell.get();
//    	}else {
//    		throw new NotFoundException("Customer not found with id " + id);
//    	}
//	}
//	@GetMapping("/showServiceBill")
//	public List<ServiceBill> showServiceBill() {
//		return ServiceBillRepository.findAll();
//	}
	@GetMapping("/showSerBills/{id}")
	public ModelAndView showService(@PathVariable Long id,Model model,@ModelAttribute @Valid ServiceBill serviceBill,@ModelAttribute @Valid ServiceSell serviceSell) {
		ModelAndView mv = new ModelAndView("showSerBill.html");
		Customer ct = customerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("ct", ct);
		if(SerBillRepository.getNextSeriesId()==null) {
			SerBillRepository.getNext();
			Long InvoiceNo = (long) 1;
			model.addAttribute("billNo", InvoiceNo);
		}
		if(SerBillRepository.getNextSeriesId()!=null) {
			SerBillRepository.getNext();
			Long InvoiceNo = SerBillRepository.getNextSeriesId();
			model.addAttribute("billNo", InvoiceNo);
		
		}

		
		//long count_ServiceSell = SerBillRepository.findAll().size();
		Customer ctb = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Invoice Id:" + id));
		long count_CustomersSerBill = ctb.getSerBill().size();
		model.addAttribute("count_SerBill", count_CustomersSerBill);
		return mv;
	}
	@PostMapping(value="/AddSerBill/{customerId}",produces="application/json",consumes="application/json")
	public SerBill AddSerBill(@PathVariable Long customerId,@Valid @RequestBody SerBill serBill,Model model) {
		return customerRepository.findById(customerId).map(customer -> {
			serBill.setCustomer(customer);  
			return SerBillRepository.save(serBill); 
			}).orElseThrow(() -> new NotFoundException("Customer not found!"));
		}

	@GetMapping("/deleteSerBill/cust/{customerId}/serBill/{id}")
	public ModelAndView deleteSerBill(@PathVariable Long id, @PathVariable Long customerId) {
		SerBillRepository.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/serBill/showSerBills/{customerId}");
		return mv;
	}
	@GetMapping("/editSerBill/cust/{cid}/serBill/{id}")
	public ModelAndView editSerBill(@PathVariable Long id,@PathVariable Long cid, Model model) {
			ModelAndView mv = new ModelAndView("editSerBill.html");
			Optional<SerBill> ss = SerBillRepository.findById(id);
			model.addAttribute("id", id);
			model.addAttribute("cid", cid);
			model.addAttribute("ss", ss);
			return mv;
	}	
	@PostMapping(value="/UpdateSerBill/{id}",produces="application/json",consumes="application/json")
	public SerBill updateSerBill(@PathVariable Long id,@Valid @RequestBody SerBill serBill,Model model) {
		return SerBillRepository.findById(id).map(serBills -> {
			serBills.setItemName(serBill.getItemName());
			serBills.setItemDescription(serBill.getItemDescription());
			serBills.setItemQty(serBill.getItemQty());
			serBills.setItemUnit(serBill.getItemUnit());
			serBills.setItemPrice(serBill.getItemPrice());
			serBills.setItemTotalAmount(serBill.getItemTotalAmount());
			serBills.setNote(serBill.getNote());
			serBills.setStatus(serBill.getStatus());
			return SerBillRepository.save(serBills); 
			}).orElseThrow(() -> new NotFoundException("Service not found!"));
		
	}
}
	
	


