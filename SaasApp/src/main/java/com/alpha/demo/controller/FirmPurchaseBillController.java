package com.alpha.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.alpha.demo.Repository.DealerRepository;
import com.alpha.demo.Repository.FpbRepository;
import com.alpha.demo.Repository.FpbiRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.BuyBill;
import com.alpha.demo.model.Customer;
import com.alpha.demo.model.Dealers;
import com.alpha.demo.model.FirmPurchaseBill;
import com.alpha.demo.model.FirmPurchaseBillItem;
import com.alpha.demo.model.Product;

@RestController
@RequestMapping("/firm")
public class FirmPurchaseBillController {
	@Autowired
	private DealerRepository dealerRepository;
	
	@Autowired
	private FpbRepository fpbRepository;
	
	@Autowired
	private FpbiRepository fpbiRepository;
	@GetMapping("/getPurchaseBillList")
    public List<FirmPurchaseBill> getPurchaseBillList() {
    	return fpbRepository.findAll();
   }
	@GetMapping("/getPurchaseBillItemList")
    public List<FirmPurchaseBillItem> getPurchaseBillListItem() {
    	return fpbiRepository.findAll();
   }
	@GetMapping("/dealers/getStock/{stockValue}")
    public List<FirmPurchaseBillItem> getDealersStock(@PathVariable String stockValue,@ModelAttribute @Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem) {
		List<FirmPurchaseBillItem> li = fpbiRepository.findByName(stockValue);
    	//return fpbiRepository.findAllCementStock();
		return li;
    }
	
	  @GetMapping("/dealers/getStock/{DealerId}/{stockValue}")
	  public HashMap<String, String> getDealersStockById(@PathVariable Long DealerId,@PathVariable String stockValue,@ModelAttribute @Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem,Model model) { 
		  ArrayList<Integer> pr = new ArrayList<Integer>();
		  ArrayList<Integer> qt = new ArrayList<Integer>();
		  HashMap<String, String> map = new HashMap<>();
		  List<FirmPurchaseBill> fpb =  fpbRepository.findByDealersId(DealerId);
		  for (FirmPurchaseBill i : fpb) {
			  Set<FirmPurchaseBillItem> fpbi =  i.getFirmPurchaseBillItem();
			  
			  for (FirmPurchaseBillItem c : fpbi) {
				  System.out.println(c.getId());
				  if(c.getName().equals(stockValue)) {
					  int p=Integer.parseInt(c.getPrice()); 
					  int q=Integer.parseInt(c.getQty()); 
					  pr.add(p);
					  qt.add(q);
					  System.out.println(pr.size());
					  //long intSum = ints.stream().mapToLong(Integer::longValue).sum();
					  long intPrSum = pr.stream().mapToLong(Integer::longValue).sum();
					  long intQtSum = qt.stream().mapToLong(Integer::longValue).sum();
					  long intPrNum = pr.size();
					  long AvgPr = intPrSum/intPrNum;
					  System.out.println(AvgPr);
					  System.out.println(intQtSum);
					  String qty=String.valueOf(intQtSum);
					  String AvgPrice=String.valueOf(AvgPr);
					    map.put("avgPr", AvgPrice);
					    map.put("intQtSum", qty);
					    
				  }
				  
			  }
		  }
		  return map; 
	  }
	
	@GetMapping("/purchase/bills")
    public ModelAndView getAllCustomers(Customer customer, Model model) {
    	ModelAndView mv = new ModelAndView("showFirmPurchaseBill.html");
    	long count_fpb = fpbRepository.count();
    	model.addAttribute("fpb", fpbRepository.findAll());
    	model.addAttribute("count_fpb", count_fpb);
    	return mv;
    }
	
	@GetMapping("/firmPurchaseBill/{id}")
	public ModelAndView firmPurchaseBill(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill,
			@ModelAttribute @Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem, Model model) {
		ModelAndView mv = new ModelAndView("firmPurchaseBillItemForm.html");
		Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + id));
		model.addAttribute("ds", ds);
		model.addAttribute("firmPurchaseBill", firmPurchaseBill);
		model.addAttribute("firmPurchaseBillItem", firmPurchaseBillItem);
		//BillRepository.getNextSeriesId();
		if(fpbRepository.getNextSeriesId()==null) {
			fpbRepository.getNext();
			Long InvoiceNo = (long) 1;
			model.addAttribute("invoiceNo", InvoiceNo);
		}
		if(fpbRepository.getNextSeriesId()!=null) {
			fpbRepository.getNext();
			Long InvoiceNo = fpbRepository.getNextSeriesId();
			model.addAttribute("invoiceNo", InvoiceNo);
		
		}
		return mv;
	}
	@PostMapping(value="/dealers/{dealerId}/bills/{invoiceNo}/bills",produces="application/json",consumes="application/json")
	@ResponseBody
	public FirmPurchaseBill addFirmPurchaseBillRequest(@PathVariable Long dealerId, @PathVariable Long invoiceNo,@Valid @RequestBody FirmPurchaseBill firmPurchaseBill) {
		return dealerRepository.findById(dealerId).map(dealers -> {
			firmPurchaseBill.setDealers(dealers);
			return fpbRepository.save(firmPurchaseBill); 
			}).orElseThrow(() -> new NotFoundException("Dealer not found!"));
		
	    }
	
	@PostMapping(value="/dealers/{dealerId}/bills/{invoiceNo}/itemBills",produces="application/json",consumes="application/json")
	@ResponseBody
	public ModelAndView addItemBillRequest(@PathVariable Long dealerId, @PathVariable Long invoiceNo,@Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem){
		boolean s  = fpbRepository.existsById(invoiceNo);
		while(s==false) {
			if (fpbRepository.existsById(invoiceNo)==true) {
				break;
			}
				}
		return fpbRepository.findById(invoiceNo).map(firmPurchaseBill -> {
			firmPurchaseBillItem.setFirmPurchaseBill(firmPurchaseBill);
			 fpbiRepository.save(firmPurchaseBillItem);
				ModelAndView mv = new ModelAndView("redirect:/dealerPurchaseBillss/{dealerId}");
				return mv;
				}).orElseThrow(() -> new NotFoundException("Firm Invoice No not found!"));
		
		}
	
	@GetMapping("/dealerPurchaseBills/{id}")
	public Page<FirmPurchaseBill> showDealerPurchaseBills(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill,
			@ModelAttribute @Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem, Model model, Pageable pageable) {
		return fpbRepository.findByDealersId(id, pageable);
		 
	}
	@GetMapping("/dealerPurchaseBillss/{id}")
	public ModelAndView showDealerPurchaseBillss(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill,@ModelAttribute @Valid @RequestBody Product product,
			@ModelAttribute @Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem, Model model, Pageable pageable) {
		Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + id));
		long count_DealerBill = ds.getFirmPurchaseBill().size();
		model.addAttribute("ds", ds);
		model.addAttribute("product", product);
		model.addAttribute("count_DealerBill", count_DealerBill);
		ModelAndView mv = new ModelAndView("showDealerPurchaseBills.html");
		return mv;
		 
	}
	@GetMapping("/DealerViewFirmBill/{id}/bill/{invoiceNo}")
	public FirmPurchaseBill showDealerItems(@PathVariable("id") long id,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill,
			@ModelAttribute @Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem, Model model, Pageable pageable) {
		Optional<FirmPurchaseBill> optFirmPurchaseBill =fpbRepository.findById(invoiceNo);
    	if(optFirmPurchaseBill.isPresent()) {
    		return optFirmPurchaseBill.get();
    	}else {
    		throw new NotFoundException("Dealer not found with id " + id);
    	}
		 
	}
	@GetMapping("/printFirmBill/{DealerId}/bill/{invoiceNo}")
	public ModelAndView printBill(@PathVariable Long DealerId,@PathVariable Long invoiceNo,@ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill,
			@ModelAttribute @Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem, Model model, Pageable pageable) {
		System.out.println("print");
		ModelAndView mv = new ModelAndView("printFirmBill.html");
		boolean s  = fpbRepository.existsById(invoiceNo);
		Dealers ds = dealerRepository.findById(DealerId).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + DealerId));
		while(s==false) {
			if (fpbRepository.existsById(invoiceNo)==true) {
				break;
			}
				}
		FirmPurchaseBill firmPurchaseBills = fpbRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + invoiceNo));
		List<FirmPurchaseBillItem> itemBill = fpbiRepository.findByFirmPurchaseBillId(invoiceNo);
		model.addAttribute("ds", ds);
		model.addAttribute("firmPurchaseBills", firmPurchaseBills);
		model.addAttribute("itemBill", itemBill);
		return mv;
		 
	}
	
	@GetMapping("/viewFirmBill/{id}/bill/{invoiceNo}")
	public ModelAndView ViewCustomerBuyBills(@PathVariable("id") long id,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill,
			@ModelAttribute @Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem, Model model, Pageable pageable) {
		Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + id));
		FirmPurchaseBill dfb = fpbRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid Invoice Id:" + invoiceNo));
		long count_DealerItemBill = dfb.getFirmPurchaseBillItem().size();
		model.addAttribute("ds", ds);
		model.addAttribute("dfb", dfb);
		model.addAttribute("count_DealerItemBill", count_DealerItemBill);
		//BillRepository.findByCustomerId(id);
		ModelAndView mv = new ModelAndView("showDealerItemBills.html");
		//model.addAttribute("bill", BillRepository.findByCustomerId(id));
		return mv;
		 
	}
	@GetMapping("/deleteFirmBill/{DealerId}/bill/{invoiceNo}")
	public ModelAndView DeleteFirmBill(@PathVariable Long DealerId,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill, Model model, Pageable pageable) {
		Set<FirmPurchaseBillItem> firmPurchaseBillsItem = fpbRepository.findById(invoiceNo).get().getFirmPurchaseBillItem();
		for (FirmPurchaseBillItem d : firmPurchaseBillsItem) {
			fpbiRepository.deleteById(d.getId());
			}
		fpbRepository.deleteById(invoiceNo);
		ModelAndView mv = new ModelAndView("redirect:/firm/dealerPurchaseBillss/{DealerId}");
		return mv;
		}
	
	@GetMapping("/accStatePaid/{DealerId}/bill/{invoiceNo}/{state}")
	public ModelAndView accStatePaid(@PathVariable Long DealerId,@PathVariable Long invoiceNo, @PathVariable String state, @ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill, Model model, Pageable pageable) {
		FirmPurchaseBill firmPurchaseBills = fpbRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + invoiceNo));
		if(firmPurchaseBills.getStatus().equals("Unpaid")) {
			firmPurchaseBills.setPaidAmount(firmPurchaseBills.getDueAmount());
			firmPurchaseBills.setDueAmount("0");
			firmPurchaseBills.setStatus(state);
			}
		fpbRepository.save(firmPurchaseBills);
		ModelAndView mv = new ModelAndView("redirect:/firm/dealerPurchaseBillss/{DealerId}");
		return mv;
		 
	}

	@GetMapping("/editFirmBill/{DealerId}/bill/{invoiceNo}")
	public ModelAndView editFirmBill(@PathVariable Long DealerId,@PathVariable Long invoiceNo,@ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill,
			@ModelAttribute @Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem, Model model, Pageable pageable) {
		ModelAndView mv = new ModelAndView("editFirmBill.html");
		Dealers ds = dealerRepository.findById(DealerId).orElseThrow(() -> new IllegalArgumentException("Invalid Dealer Id:" + DealerId));
		FirmPurchaseBill firmPurchaseBills = fpbRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid Invoice Id:" + invoiceNo));
		List<FirmPurchaseBillItem> firmPurchaseBillItems = fpbiRepository.findAll();
		model.addAttribute("ds", ds);
		model.addAttribute("firmPurchaseBills", firmPurchaseBills);
		model.addAttribute("firmPurchaseBillItems", firmPurchaseBillItems);
		return mv;
		 
	}
	@GetMapping("/deleteItemBill/dealers/{DealerId}/bill/{invoiceNo}/itemBill/{itemBillId}")
	public ModelAndView deleteItemBill(@PathVariable Long DealerId,@PathVariable Long invoiceNo,@PathVariable Long itemBillId,@ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBills, Model model, Pageable pageable) {
		fpbiRepository.deleteById(itemBillId);
		System.out.println("Delete");
		ModelAndView mv = new ModelAndView("redirect:firm/viewFirmBill/{DealerId}/bill/{invoiceNo}");
		return mv;
		 
	}
	@PostMapping(value="/update/dealers/{DealerId}/bills/{invoiceNo}/bills",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public FirmPurchaseBill updateFirmBillRequest(@PathVariable Long DealerId, @PathVariable Long invoiceNo,@Valid @RequestBody FirmPurchaseBill firmPurchaseBill) {
		return fpbRepository.findById(invoiceNo).map(firmPurchaseBills -> {
			firmPurchaseBills.setBillingDate(firmPurchaseBill.getBillingDate());
			firmPurchaseBills.setDueAmount(firmPurchaseBill.getDueAmount());
			firmPurchaseBills.setNote(firmPurchaseBill.getNote());
			firmPurchaseBills.setPaidAmount(firmPurchaseBill.getPaidAmount());
			firmPurchaseBills.setStatus(firmPurchaseBill.getStatus());
			firmPurchaseBills.setSubtotalAmount(firmPurchaseBill.getSubtotalAmount());
			firmPurchaseBills.setTotalAmount(firmPurchaseBill.getTotalAmount());
			firmPurchaseBills.setTotalDiscountAmount(firmPurchaseBill.getTotalDiscountAmount());
			firmPurchaseBills.setTotalGSTAmount(firmPurchaseBill.getTotalGSTAmount());
			firmPurchaseBills.setDinvoice(firmPurchaseBill.getDinvoice());
			
			return fpbRepository.save(firmPurchaseBills); 
			}).orElseThrow(() -> new NotFoundException("Dealer not found!"));
		
	    }
	
	@PostMapping(value="/update/dealers/{DealerId}/bills/{invoiceNo}/itemBills",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public List<FirmPurchaseBillItem> updateItemBillRequest(@PathVariable Long DealerId, @PathVariable Long invoiceNo,@Valid @RequestBody FirmPurchaseBillItem firmPurchaseBillItem){
		List<FirmPurchaseBillItem> arr = new ArrayList<FirmPurchaseBillItem>();
    	arr.add(firmPurchaseBillItem);
    	for (FirmPurchaseBillItem d : arr) {
		Long I =  d.getId();
			System.out.println(I);
			Optional<FirmPurchaseBillItem> itembills = fpbiRepository.findById(d.getId());
			FirmPurchaseBillItem b = itembills.get();
			b.setProId(firmPurchaseBillItem.getProId());	
		    b.setName(firmPurchaseBillItem.getName());
		    b.setDescription(firmPurchaseBillItem.getDescription());
		    b.setQty(firmPurchaseBillItem.getQty());
		    b.setUnit(firmPurchaseBillItem.getUnit());
		    b.setPrice(firmPurchaseBillItem.getPrice());
		    b.setGst(firmPurchaseBillItem.getGst());
		    b.setDis(firmPurchaseBillItem.getDis());
		    b.setAmount(firmPurchaseBillItem.getAmount());
			fpbiRepository.save(b);
			
		}
		return null;
		
		}
	@GetMapping("/view/{id}")
	public ModelAndView ViewCustomer(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody FirmPurchaseBill firmPurchaseBill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model, Pageable pageable) {
        Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Dealer Id:" + id));
		model.addAttribute("ds", ds);
		ModelAndView mv = new ModelAndView("dealerView.html");
		return mv;
		 
	}

}
