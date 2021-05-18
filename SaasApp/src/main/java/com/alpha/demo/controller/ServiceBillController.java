package com.alpha.demo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

import com.alpha.demo.Repository.ServiceSellRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.ServiceSell;
import com.alpha.demo.model.ServiceBill;
import com.alpha.demo.Repository.ServiceBillRepository;

@RestController
@RequestMapping("/service")
public class ServiceBillController {
	@Autowired
	private ServiceSellRepository ServiceSellRepository;
	
	@Autowired
	private ServiceBillRepository ServiceBillRepository;
	
	@GetMapping("/showServiceSell")
	public List<ServiceSell> showServiceSell() {
		return ServiceSellRepository.findAll();
	}
	@GetMapping("/showServiceSellById/{id}")
	public ServiceSell showServiceSellById(@PathVariable Long id) {
		Optional<ServiceSell> optServiceSell =ServiceSellRepository.findById(id);
    	if(optServiceSell.isPresent()) {
    		return optServiceSell.get();
    	}else {
    		throw new NotFoundException("Customer not found with id " + id);
    	}
	}
	@GetMapping("/showServiceBill")
	public List<ServiceBill> showServiceBill() {
		return ServiceBillRepository.findAll();
	}
	@GetMapping("/addService")
	public ModelAndView addService(Model model,@ModelAttribute @Valid ServiceBill serviceBill,@ModelAttribute @Valid ServiceSell serviceSell) {
		ModelAndView mv = new ModelAndView("AddService.html");
		if(ServiceSellRepository.getNextSeriesId()==null) {
			ServiceSellRepository.getNext();
			Long InvoiceNo = (long) 1;
			model.addAttribute("billNo", InvoiceNo);
		}
		if(ServiceSellRepository.getNextSeriesId()!=null) {
			ServiceSellRepository.getNext();
			Long InvoiceNo = ServiceSellRepository.getNextSeriesId();
			model.addAttribute("billNo", InvoiceNo);
		
		}
		
		return mv;
	}
	@GetMapping("/showService")
	public ModelAndView showService(Model model,@ModelAttribute @Valid ServiceBill serviceBill,@ModelAttribute @Valid ServiceSell serviceSell) {
		ModelAndView mv = new ModelAndView("showServices.html");
		long count_ServiceSell = ServiceSellRepository.findAll().size();
		model.addAttribute("count_ServiceSell", count_ServiceSell);
		return mv;
	}
	@PostMapping(value="/AddServiceSell",produces="application/json",consumes="application/json")
	public ServiceSell AddServiceSell(@Valid @RequestBody ServiceSell serviceSell,Model model) {
		return ServiceSellRepository.save(serviceSell); 
		}
	@PostMapping(value="/AddServiceBill/{id}",produces="application/json",consumes="application/json")
	public ServiceBill AddServiceBill(@PathVariable Long id,@Valid @RequestBody ServiceBill serviceBill,Model model) {
		boolean s  = ServiceSellRepository.existsById(id);
		while(s==false) {
			if (ServiceSellRepository.existsById(id)==true) {
				break;
			}
				}
		System.out.println(ServiceSellRepository.findById(id));		
		return ServiceSellRepository.findById(id).map(serviceSell -> {
			serviceBill.setServiceSell(serviceSell);
			return ServiceBillRepository.save(serviceBill);
				
				}).orElseThrow(() -> new NotFoundException("Invoice No not found!"));
		
	}
	@GetMapping("/editService/{id}")
	public ModelAndView editService(@PathVariable Long id, Model model) {
			ModelAndView mv = new ModelAndView("editServiceSell.html");
			ServiceSell ss = ServiceSellRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
			model.addAttribute("ss", ss);
			return mv;
	}
	@PostMapping(value="/updateServiceSell/{id}",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public ServiceSell updateServiceSell(@PathVariable Long id,@Valid @RequestBody ServiceSell serviceSell,Model model) {
		return ServiceSellRepository.findById(id).map(serSell -> {
			serSell.setFullName(serviceSell.getFullName());
			serSell.setAddress(serviceSell.getAddress());
			serSell.setMobileNo(serviceSell.getMobileNo());
			serSell.setTransporterName(serviceSell.getTransporterName());
			serSell.setTransporterAddress(serviceSell.getTransporterAddress());
			serSell.setTransporterNumber(serviceSell.getTransporterNumber());
			serSell.setItemTotalAmount(serviceSell.getItemTotalAmount());
			serSell.setItemTotalPaidAmount(serviceSell.getItemTotalPaidAmount());
			serSell.setItemTotalDueAmount(serviceSell.getItemTotalDueAmount());
			serSell.setNote(serviceSell.getNote());
			serSell.setStatus(serviceSell.getStatus());
			return ServiceSellRepository.save(serSell); 
			}).orElseThrow(() -> new NotFoundException("Service not found!"));
		
	}
	@PostMapping(value="/updateServiceBill/{id}",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public ServiceBill updateServiceBill(@PathVariable Long id,@Valid @RequestBody ServiceBill serviceBill,Model model) {
		List<ServiceBill> arr = new ArrayList<ServiceBill>();
		System.out.println(arr);
		arr.add(serviceBill);
    	for (ServiceBill d : arr) {
		Long I =  d.getId();
			System.out.println(I);
			Optional<ServiceBill> itembills = ServiceBillRepository.findById(d.getId());
			ServiceBill b = itembills.get();
			b.setProId(serviceBill.getProId());	
		    b.setName(serviceBill.getName());
		    b.setDescription(serviceBill.getDescription());
		    b.setQty(serviceBill.getQty());
		    b.setUnit(serviceBill.getUnit());
		    b.setPrice(serviceBill.getPrice());
		    b.setAmount(serviceBill.getAmount());
		    ServiceBillRepository.save(b);
			
		}
	return null;
	}
	@GetMapping("/deleteServiceSell/{id}")
	public ModelAndView deleteServiceSell(@PathVariable Long id) {
		ServiceSellRepository.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/service/showService");
		return mv;
	}
	@GetMapping("/deleteServiceBill/{id}")
	public ModelAndView deleteServiceBill(@PathVariable Long id) {
		ServiceBillRepository.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/service/showService");
		return mv;
	}
	@GetMapping("/AddServiceSell/{id}")
	public ModelAndView AddService(@PathVariable Long id,Model model,@ModelAttribute @Valid ServiceBill serviceBill,@ModelAttribute @Valid ServiceSell serviceSell) {
		ModelAndView mv = new ModelAndView("AddServiceSell.html");
		model.addAttribute("billNo", id);
		return mv;
	}
	@PostMapping(value="/updateAddServiceSell/{id}",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public ServiceSell updateAddServiceSell(@PathVariable Long id,@Valid @RequestBody ServiceSell serviceSell,Model model) {
		return ServiceSellRepository.findById(id).map(serSell -> {
			serSell.setFullName(serviceSell.getFullName());
			serSell.setAddress(serviceSell.getAddress());
			serSell.setMobileNo(serviceSell.getMobileNo());
			serSell.setTransporterName(serviceSell.getTransporterName());
			serSell.setTransporterAddress(serviceSell.getTransporterAddress());
			serSell.setTransporterNumber(serviceSell.getTransporterNumber());
			Optional<ServiceSell> s = ServiceSellRepository.findById(id);
			float totalAmoF = Float.parseFloat(s.get().getItemTotalAmount());
			float totalPaidAmoF = Float.parseFloat(s.get().getItemTotalPaidAmount());
			float totalDueAmoF = Float.parseFloat(s.get().getItemTotalDueAmount());
			
			float totalAmoS = Float.parseFloat(serviceSell.getItemTotalAmount());
			float totalPaidAmoS = Float.parseFloat(serviceSell.getItemTotalPaidAmount());
			float totalDueAmoS = Float.parseFloat(serviceSell.getItemTotalDueAmount());
			
			String totalAmo = Float.toString(totalAmoF+totalAmoS);
			String totalPaidAmo =Float.toString(totalPaidAmoF+ totalPaidAmoS);
			String totalDueAmo = Float.toString(totalDueAmoF+totalDueAmoS);
			
			serSell.setItemTotalAmount(totalAmo);
			serSell.setItemTotalPaidAmount(totalPaidAmo);
			serSell.setItemTotalDueAmount(totalDueAmo);
			serSell.setNote(serviceSell.getNote());
			serSell.setStatus(serviceSell.getStatus());
			return ServiceSellRepository.save(serSell); 
			}).orElseThrow(() -> new NotFoundException("Service not found!"));
		
	}

	
}
	
	


