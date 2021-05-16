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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alpha.demo.Repository.SBillRepository;
import com.alpha.demo.Repository.SellBillRepository;
import com.alpha.demo.Repository.CustomerRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.SBill;
import com.alpha.demo.model.SellBill;
import com.alpha.demo.model.Bill;
import com.alpha.demo.model.BuyBill;
import com.alpha.demo.model.Customer;


@RestController
@RequestMapping("/Scust")
public class SBillController {
	@Autowired
	private SBillRepository SBillRepository;
	

	@Autowired
	private SellBillRepository sellBillRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/showSBill")
	public List<SBill> showSBill() {
		return SBillRepository.findAll();
	}
	@GetMapping("/showSellBill")
	public List<SellBill> showSellBill() {
		return sellBillRepository.findAll();
	}

	@GetMapping("/customersSellBill/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody SBill sbill,
			@ModelAttribute @Valid @RequestBody SellBill sellBill, Model model) {
		ModelAndView mv = new ModelAndView("sellBillFormss.html");
		Customer ct = customerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("ct", ct);
		model.addAttribute("sbill", sbill);
		model.addAttribute("sellBill", sellBill);
		//BillRepository.getNextSeriesId();
		if(SBillRepository.getNextSeriesId()==null) {
			SBillRepository.getNext();
			Long InvoiceNo = (long) 1;
			model.addAttribute("invoiceNo", InvoiceNo);
		}
		if(SBillRepository.getNextSeriesId()!=null) {
			SBillRepository.getNext();
			Long InvoiceNo = SBillRepository.getNextSeriesId();
			model.addAttribute("invoiceNo", InvoiceNo);
		
		}
		return mv;
	}
	
	@PostMapping(value="/customers/{customerId}/sbills/{invoiceNo}/sbills",produces="application/json",consumes="application/json")
	@ResponseBody
	public SBill addSBillRequest(@PathVariable Long customerId, @PathVariable Long invoiceNo,@Valid @RequestBody SBill sbill) {
		return customerRepository.findById(customerId).map(customer -> {
			sbill.setCustomer(customer);  
			return SBillRepository.save(sbill); 
			}).orElseThrow(() -> new NotFoundException("Customer not found!"));
		
	    }
	
	@PostMapping(value="/customers/{customerId}/sbills/{invoiceNo}/sellBills",produces="application/json",consumes="application/json")
	@ResponseBody
	public SellBill addSellBillRequest(@PathVariable Long customerId, @PathVariable Long invoiceNo,@Valid @RequestBody SellBill sellBill){
		boolean s  = SBillRepository.existsById(invoiceNo);
		while(s==false) {
			if (SBillRepository.existsById(invoiceNo)==true) {
				break;
			}
				}
		System.out.println(SBillRepository.findById(invoiceNo));		
		
		
		return SBillRepository.findById(invoiceNo).map(bills -> {
			sellBill.setSbill(bills);;
			 return sellBillRepository.save(sellBill);
				
				}).orElseThrow(() -> new NotFoundException("Invoice No not found!"));
		
		}
	@GetMapping("/customersSBills/{id}")
	public Page<SBill> showCustomerSBills(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody SBill sbill,
			@ModelAttribute @Valid @RequestBody SellBill sellBill, Model model, Pageable pageable) {
		return SBillRepository.findByCustomerId(id, pageable);
		 
	}
	@GetMapping("/customersSBillss/{id}")
	public ModelAndView showCustomerBillss(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody SBill sbill,
			@ModelAttribute @Valid @RequestBody SellBill sellBill, Model model, Pageable pageable) {
		Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)); 
		long count_CustomersSBill = ct.getSBill().size(); model.addAttribute("ct", ct);
		model.addAttribute("count_CustomersSBill", count_CustomersSBill);
		ModelAndView mv = new ModelAndView("showCustomerSbills.html");
		return mv;
		 
	}
	
	@GetMapping("/viewSellBill/{id}/sbill/{invoiceNo}")
	public ModelAndView ViewCustomerSellBills(@PathVariable("id") long id,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody SBill sbill,
			@ModelAttribute @Valid @RequestBody SellBill sellBill, Model model, Pageable pageable) {
		Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		SBill ctb = SBillRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid Invoice Id:" + invoiceNo));
		long count_CustomersSellBill = ctb.getSellBill().size();
		model.addAttribute("ct", ct);
		model.addAttribute("ctb", ctb);
		model.addAttribute("count_CustomersSellBill", count_CustomersSellBill);
		//BillRepository.findByCustomerId(id);
		ModelAndView mv = new ModelAndView("showCustomerSellBills.html");
		//model.addAttribute("bill", BillRepository.findByCustomerId(id));
		return mv;
		 
	}
	@GetMapping("/CustomerViewSellBill/{id}/sbill/{invoiceNo}")
	public SBill showCustomersSellBills(@PathVariable("id") long id,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody SBill sbill,
			@ModelAttribute @Valid @RequestBody SellBill sellBill, Model model, Pageable pageable) {
		Optional<SBill> optSBill =SBillRepository.findById(invoiceNo);
    	if(optSBill.isPresent()) {
    		return optSBill.get();
    	}else {
    		throw new NotFoundException("Customer not found with id " + invoiceNo);
    	}
		 
	}
	
	@GetMapping("/SBillDelete/{CustomerId}/sbill/{invoiceNo}")
	public ModelAndView DeleteSBill(@PathVariable Long CustomerId,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody SBill sbill,
			@ModelAttribute @Valid @RequestBody SellBill sellBill, Model model, Pageable pageable) {
		SBillRepository.deleteById(invoiceNo);
		ModelAndView mv = new ModelAndView("redirect:/Scust/customersSBillss/{CustomerId}");
		return mv;
		 
	}

	/*
	 * @GetMapping("/credState/{CustomerId}/sbill/{invoiceNo}/{state}") public
	 * ModelAndView credState(@PathVariable Long CustomerId,@PathVariable Long
	 * invoiceNo, @PathVariable String state, @ModelAttribute @Valid @RequestBody
	 * SBill sbill,
	 * 
	 * @ModelAttribute @Valid @RequestBody SellBill buyBill, Model model, Pageable
	 * pageable) { System.out.println(state); SBill sbills =
	 * SBillRepository.findById(invoiceNo).orElseThrow(() -> new
	 * IllegalArgumentException("Invalid user Id:" + invoiceNo));
	 * sbills.setCredibilityStatus(state); SBillRepository.save(sbills);
	 * ModelAndView mv = new
	 * ModelAndView("redirect:/Scust/customersBillss/{CustomerId}"); return mv;
	 * 
	 * }
	 */
	@GetMapping("/editSBill/{CustomerId}/sbill/{invoiceNo}")
	public ModelAndView editSBill(@PathVariable Long CustomerId,@PathVariable Long invoiceNo,@ModelAttribute @Valid @RequestBody SBill sbill,
			@ModelAttribute @Valid @RequestBody SellBill sellBills, Model model, Pageable pageable) {
		ModelAndView mv = new ModelAndView("editSbill.html");
		Customer ct = customerRepository.findById(CustomerId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + CustomerId));
		SBill sbills = SBillRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + invoiceNo));
		List<SellBill> sellBill = sellBillRepository.findAll();
		model.addAttribute("ct", ct);
		model.addAttribute("sbills", sbills);
		model.addAttribute("sellBill", sellBill);
		return mv;
		 
	}
	@GetMapping("/deleteSellBill/{CustomerId}/sbill/{invoiceNo}/sellBill/{sellBillId}")
	public ModelAndView deleteSellBill(@PathVariable Long CustomerId,@PathVariable Long invoiceNo,@PathVariable Long sellBillId,@ModelAttribute @Valid @RequestBody SBill sbill,
			@ModelAttribute @Valid @RequestBody SellBill sellBills, Model model, Pageable pageable) {
		//Customer ct = customerRepository.findById(CustomerId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + CustomerId));
		sellBillRepository.deleteById(sellBillId);
		System.out.println("Delete");
		ModelAndView mv = new ModelAndView("redirect:cust/viewSellBill/{CustomerId}/sbill/{invoiceNo}");
		return mv;
		 
	}
	@PostMapping(value="/update/customers/{customerId}/sbills/{invoiceNo}/sbills",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public SBill updateSBillRequest(@PathVariable Long customerId, @PathVariable Long invoiceNo,@Valid @RequestBody SBill sbill) {
		return SBillRepository.findById(invoiceNo).map(sbills -> {
			sbills.setBillingDate(sbill.getBillingDate());
			sbills.setDueAmount(sbill.getDueAmount());
			sbills.setNote(sbill.getNote());
			sbills.setPaidAmount(sbill.getPaidAmount());
			sbills.setStatus(sbill.getStatus());
			sbills.setSubtotalAmount(sbill.getSubtotalAmount());
			sbills.setTotalAmount(sbill.getTotalAmount());
			sbills.setTotalDiscountAmount(sbill.getTotalDiscountAmount());
			sbills.setTotalGSTAmount(sbill.getTotalGSTAmount());
			return SBillRepository.save(sbills); 
			}).orElseThrow(() -> new NotFoundException("Customer not found!"));
		
	    }
	
	@PostMapping(value="/update/customers/{customerId}/sbills/{invoiceNo}/sellBills",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public List<SellBill> updateSellBillRequest(@PathVariable Long customerId, @PathVariable Long invoiceNo,@Valid @RequestBody SellBill sellBill){
		List<SellBill> arr = new ArrayList<SellBill>();
    	arr.add(sellBill);
    	for (SellBill d : arr) {
		Long I =  d.getId();
			System.out.println(I);
			Optional<SellBill> sellbills = sellBillRepository.findById(d.getId());
			SellBill b = sellbills.get();
			b.setProId(sellBill.getProId());	
		    b.setName(sellBill.getName());
		    b.setDescription(sellBill.getDescription());
		    b.setQty(sellBill.getQty());
		    b.setUnit(sellBill.getUnit());
		    b.setPrice(sellBill.getPrice());
		    b.setGst(sellBill.getGst());
		    b.setDis(sellBill.getDis());
		    b.setAmount(sellBill.getAmount());
		    sellBillRepository.save(b);
			
		}
		return null;
		
		}
	@GetMapping("/printSBill/{CustomerId}/sbill/{invoiceNo}")
	public ModelAndView printBill(@PathVariable Long CustomerId,@PathVariable Long invoiceNo,@ModelAttribute @Valid @RequestBody SBill sbill,
			@ModelAttribute @Valid @RequestBody SellBill sellBills, Model model, Pageable pageable) {
		System.out.println("print");
		ModelAndView mv = new ModelAndView("sBills.html");
		boolean s  = SBillRepository.existsById(invoiceNo);
		Customer ct = customerRepository.findById(CustomerId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + CustomerId));
		while(s==false) {
			if (SBillRepository.existsById(invoiceNo)==true) {
				break;
			}
				}
		//Customer ct = customerRepository.findById(CustomerId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + CustomerId));
		SBill sbills = SBillRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + invoiceNo));
		List<SellBill> sellBill = sellBillRepository.findBySbillId(invoiceNo);
		model.addAttribute("ct", ct);
		model.addAttribute("sbills", sbills);
		model.addAttribute("sellBill", sellBill);
		return mv;
		 
	}
	
	
	}
	
	


