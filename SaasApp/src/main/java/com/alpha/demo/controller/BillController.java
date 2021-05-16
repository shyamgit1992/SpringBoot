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

import com.alpha.demo.Repository.BillRepository;
import com.alpha.demo.Repository.BuyBillRepository;
import com.alpha.demo.Repository.CustomerRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.Bill;
import com.alpha.demo.model.BuyBill;
import com.alpha.demo.model.Customer;



@RestController
@RequestMapping("/cust")
public class BillController {
	@Autowired
	private BillRepository BillRepository;
	

	@Autowired
	private BuyBillRepository buyBillRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/showBill")
	public List<Bill> showBill() {
		return BillRepository.findAll();
	}
	@GetMapping("/showBuyBill")
	public List<BuyBill> showBuyBill() {
		return buyBillRepository.findAll();
	}

	@GetMapping("/customersBuyBill/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model) {
		ModelAndView mv = new ModelAndView("buyBillFormss.html");
		Customer ct = customerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("ct", ct);
		model.addAttribute("bill", bill);
		model.addAttribute("buyBill", buyBill);
		//BillRepository.getNextSeriesId();
		if(BillRepository.getNextSeriesId()==null) {
			BillRepository.getNext();
			Long InvoiceNo = (long) 1;
			model.addAttribute("invoiceNo", InvoiceNo);
		}
		if(BillRepository.getNextSeriesId()!=null) {
			BillRepository.getNext();
			Long InvoiceNo = BillRepository.getNextSeriesId();
			model.addAttribute("invoiceNo", InvoiceNo);
		
		}
		return mv;
	}
	
	@PostMapping(value="/customers/{customerId}/bills/{invoiceNo}/bills",produces="application/json",consumes="application/json")
	@ResponseBody
	public Bill addBillRequest(@PathVariable Long customerId, @PathVariable Long invoiceNo,@Valid @RequestBody Bill bill) {
		return customerRepository.findById(customerId).map(customer -> {
			bill.setCustomer(customer);  
			return BillRepository.save(bill); 
			}).orElseThrow(() -> new NotFoundException("Customer not found!"));
		
	    }
	
	@PostMapping(value="/customers/{customerId}/bills/{invoiceNo}/buyBills",produces="application/json",consumes="application/json")
	@ResponseBody
	public ModelAndView addBuyBillRequest(@PathVariable Long customerId, @PathVariable Long invoiceNo,@Valid @RequestBody BuyBill buyBill){
		boolean s  = BillRepository.existsById(invoiceNo);
		while(s==false) {
			if (BillRepository.existsById(invoiceNo)==true) {
				break;
			}
				}
		System.out.println(BillRepository.findById(invoiceNo));		
		
		
		return BillRepository.findById(invoiceNo).map(bills -> {
			buyBill.setBill(bills);
			 buyBillRepository.save(buyBill);
				ModelAndView mv = new ModelAndView("redirect:/customersBillss/{customerId}");
				return mv;
				}).orElseThrow(() -> new NotFoundException("Invoice No not found!"));
		
		}
	@GetMapping("/customersBills/{id}")
	public Page<Bill> showCustomerBills(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model, Pageable pageable) {
		return BillRepository.findByCustomerId(id, pageable);
		 
	}
	@GetMapping("/customersBillss/{id}")
	public ModelAndView showCustomerBillss(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model, Pageable pageable) {
		Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		long count_CustomersBill = ct.getBill().size();
		model.addAttribute("ct", ct);
		model.addAttribute("count_CustomersBill", count_CustomersBill);
		//BillRepository.findByCustomerId(id);
		ModelAndView mv = new ModelAndView("showCustomerBills.html");
		//model.addAttribute("bill", BillRepository.findByCustomerId(id));
		return mv;
		 
	}
	
	@GetMapping("/viewBuyBill/{id}/bill/{invoiceNo}")
	public ModelAndView ViewCustomerBuyBills(@PathVariable("id") long id,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model, Pageable pageable) {
		Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		Bill ctb = BillRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid Invoice Id:" + invoiceNo));
		long count_CustomersBuyBill = ctb.getBuyBill().size();
		model.addAttribute("ct", ct);
		model.addAttribute("ctb", ctb);
		model.addAttribute("count_CustomersBuyBill", count_CustomersBuyBill);
		//BillRepository.findByCustomerId(id);
		ModelAndView mv = new ModelAndView("showCustomerBuyBills.html");
		//model.addAttribute("bill", BillRepository.findByCustomerId(id));
		return mv;
		 
	}
	@GetMapping("/CustomerViewBuyBill/{id}/bill/{invoiceNo}")
	public Bill showCustomersBuyBills(@PathVariable("id") long id,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model, Pageable pageable) {
		Optional<Bill> optBill =BillRepository.findById(invoiceNo);
    	if(optBill.isPresent()) {
    		return optBill.get();
    	}else {
    		throw new NotFoundException("Customer not found with id " + id);
    	}
		 
	}
	
	@GetMapping("/BillDelete/{CustomerId}/bill/{invoiceNo}")
	public ModelAndView DeleteBill(@PathVariable Long CustomerId,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model, Pageable pageable) {
		BillRepository.deleteById(invoiceNo);
		ModelAndView mv = new ModelAndView("redirect:/cust/customersBillss/{CustomerId}");
		return mv;
		 
	}
//	@GetMapping("/credState/{CustomerId}/bill/{invoiceNo}/{state}")
//	public ModelAndView credState(@PathVariable Long CustomerId,@PathVariable Long invoiceNo, @PathVariable String state, @ModelAttribute @Valid @RequestBody Bill bill,
//			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model, Pageable pageable) {
//		System.out.println(state);
//		Bill bills = BillRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + invoiceNo));
//		bills.setCredibilityStatus(state);
//		BillRepository.save(bills);
//		ModelAndView mv = new ModelAndView("redirect:/cust/customersBillss/{CustomerId}");
//		return mv;
//		 
//	}
	@GetMapping("/editBill/{CustomerId}/bill/{invoiceNo}")
	public ModelAndView editBill(@PathVariable Long CustomerId,@PathVariable Long invoiceNo,@ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBills, Model model, Pageable pageable) {
		ModelAndView mv = new ModelAndView("editBill.html");
		Customer ct = customerRepository.findById(CustomerId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + CustomerId));
		Bill bills = BillRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + invoiceNo));
		List<BuyBill> buyBill = buyBillRepository.findAll();
		model.addAttribute("ct", ct);
		model.addAttribute("bills", bills);
		model.addAttribute("buyBill", buyBill);
		return mv;
		 
	}
	@GetMapping("/deleteBuyBill/customers/{CustomerId}/bill/{invoiceNo}/buyBill/{buyBillId}")
	public ModelAndView deleteBuyBill(@PathVariable Long CustomerId,@PathVariable Long invoiceNo,@PathVariable Long buyBillId,@ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBills, Model model, Pageable pageable) {
		buyBillRepository.deleteById(buyBillId);
		System.out.println("Delete");
		ModelAndView mv = new ModelAndView("redirect:cust/viewBuyBill/{CustomerId}/bill/{invoiceNo}");
		return mv;
		 
	}
	@PostMapping(value="/update/customers/{customerId}/bills/{invoiceNo}/bills",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public Bill updateBillRequest(@PathVariable Long customerId, @PathVariable Long invoiceNo,@Valid @RequestBody Bill bill) {
		return BillRepository.findById(invoiceNo).map(bills -> {
			bills.setBillingDate(bill.getBillingDate());
			bills.setDueAmount(bill.getDueAmount());
			bills.setGuarantorName(bill.getGuarantorName());
			bills.setInterestRate(bill.getInterestRate());
			bills.setNote(bill.getNote());
			bills.setPaidAmount(bill.getPaidAmount());
			bills.setStatus(bill.getStatus());
			bills.setSubtotalAmount(bill.getSubtotalAmount());
			bills.setTotalAmount(bill.getTotalAmount());
			bills.setTotalDiscountAmount(bill.getTotalDiscountAmount());
			bills.setTotalGSTAmount(bill.getTotalGSTAmount());
			return BillRepository.save(bills); 
			}).orElseThrow(() -> new NotFoundException("Customer not found!"));
		
	    }
	
	@PostMapping(value="/update/customers/{customerId}/bills/{invoiceNo}/buyBills",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public List<BuyBill> updateBuyBillRequest(@PathVariable Long customerId, @PathVariable Long invoiceNo,@Valid @RequestBody BuyBill buyBill){
		List<BuyBill> arr = new ArrayList<BuyBill>();
    	arr.add(buyBill);
    	for (BuyBill d : arr) {
		Long I =  d.getId();
			System.out.println(I);
			Optional<BuyBill> buybills = buyBillRepository.findById(d.getId());
			BuyBill b = buybills.get();
			b.setProId(buyBill.getProId());	
		    b.setName(buyBill.getName());
		    b.setDescription(buyBill.getDescription());
		    b.setQty(buyBill.getQty());
		    b.setUnit(buyBill.getUnit());
		    b.setPrice(buyBill.getPrice());
		    b.setGst(buyBill.getGst());
		    b.setDis(buyBill.getDis());
		    b.setAmount(buyBill.getAmount());
			buyBillRepository.save(b);
			
		}
		return null;
		
		}
	@GetMapping("/printBill/{CustomerId}/bill/{invoiceNo}")
	public ModelAndView printBill(@PathVariable Long CustomerId,@PathVariable Long invoiceNo,@ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBills, Model model, Pageable pageable) {
		System.out.println("print");
		ModelAndView mv = new ModelAndView("printBill.html");
		boolean s  = BillRepository.existsById(invoiceNo);
		Customer ct = customerRepository.findById(CustomerId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + CustomerId));
		while(s==false) {
			if (BillRepository.existsById(invoiceNo)==true) {
				break;
			}
				}
		Bill bills = BillRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + invoiceNo));
		List<BuyBill> buyBill = buyBillRepository.findByBillId(invoiceNo);
		model.addAttribute("ct", ct);
		model.addAttribute("bills", bills);
		model.addAttribute("buyBill", buyBill);
		return mv;
		 
	}
	@GetMapping("/view/{id}")
	public ModelAndView ViewCustomer(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody Bill bill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model, Pageable pageable) {
		Customer ct = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("ct", ct);
		ModelAndView mv = new ModelAndView("customerView.html");
		return mv;
		 
	}
	
	
	
	}
	
	


