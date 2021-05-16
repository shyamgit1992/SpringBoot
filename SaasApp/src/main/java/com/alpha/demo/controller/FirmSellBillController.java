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
import com.alpha.demo.Repository.FsbRepository;
import com.alpha.demo.Repository.FsbiRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.BuyBill;
import com.alpha.demo.model.Customer;
import com.alpha.demo.model.Dealers;
import com.alpha.demo.model.FirmSellBill;
import com.alpha.demo.model.FirmSellBillItem;
import com.alpha.demo.model.Product;

@RestController
@RequestMapping("/firmSell")
public class FirmSellBillController {
	@Autowired
	private DealerRepository dealerRepository;
	
	@Autowired
	private FsbRepository fsbRepository;
	
	@Autowired
	private FsbiRepository fsbiRepository;
	@GetMapping("/getSellBillList")
    public List<FirmSellBill> getSellBillList() {
    	return fsbRepository.findAll();
   }
	@GetMapping("/getSellBillItemList")
    public List<FirmSellBillItem> getSellBillListItem() {
    	return fsbiRepository.findAll();
   }
	@GetMapping("/dealers/getStock/{stockValue}")
    public List<FirmSellBillItem> getDealersStock(@PathVariable String stockValue,@ModelAttribute @Valid @RequestBody FirmSellBillItem firmSellBillItem) {
		List<FirmSellBillItem> li = fsbiRepository.findByName(stockValue);
    	//return fpbiRepository.findAllCementStock();
		return li;
    }
	
	  @GetMapping("/dealers/getStock/{DealerId}/{stockValue}")
	  public HashMap<String, String> getDealersStockById(@PathVariable Long DealerId,@PathVariable String stockValue,@ModelAttribute @Valid @RequestBody FirmSellBillItem firmSellBillItem,Model model) { 
		  ArrayList<Integer> pr = new ArrayList<Integer>();
		  ArrayList<Integer> qt = new ArrayList<Integer>();
		  HashMap<String, String> map = new HashMap<>();
		  List<FirmSellBill> fpb =  fsbRepository.findByDealersId(DealerId);
		  for (FirmSellBill i : fpb) {
			  Set<FirmSellBillItem> fsbi =  i.getFirmSellBillItem();
			  
			  for (FirmSellBillItem c : fsbi) {
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
	
	@GetMapping("/sell/bills")
    public ModelAndView getAllCustomers(Customer customer, Model model) {
    	ModelAndView mv = new ModelAndView("showFirmSellBill.html");
    	long count_fsb = fsbRepository.count();
    	model.addAttribute("fsb", fsbRepository.findAll());
    	model.addAttribute("count_fsb", count_fsb);
    	return mv;
    }
	
	@GetMapping("/firmSellBill/{id}")
	public ModelAndView firmSellBill(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill,
			@ModelAttribute @Valid @RequestBody FirmSellBillItem firmSellBillItem, Model model) {
		ModelAndView mv = new ModelAndView("firmSellBillItemForm.html");
		Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + id));
		model.addAttribute("ds", ds);
		model.addAttribute("firmSellBill", firmSellBill);
		model.addAttribute("firmSellBillItem", firmSellBillItem);
		if(fsbRepository.getNextSeriesId()==null) {
			fsbRepository.getNext();
			Long InvoiceNo = (long) 1;
			model.addAttribute("invoiceNo", InvoiceNo);
		}
		if(fsbRepository.getNextSeriesId()!=null) {
			fsbRepository.getNext();
			Long InvoiceNo = fsbRepository.getNextSeriesId();
			model.addAttribute("invoiceNo", InvoiceNo);
		
		}
		return mv;
	}
	@PostMapping(value="/dealers/{dealerId}/bills/{invoiceNo}/bills",produces="application/json",consumes="application/json")
	@ResponseBody
	public FirmSellBill addFirmSellBillRequest(@PathVariable Long dealerId, @PathVariable Long invoiceNo,@Valid @RequestBody FirmSellBill firmSellBill) {
		return dealerRepository.findById(dealerId).map(dealers -> {
			firmSellBill.setDealers(dealers);
			return fsbRepository.save(firmSellBill); 
			}).orElseThrow(() -> new NotFoundException("Dealer not found!"));
		
	    }
	
	@PostMapping(value="/dealers/{dealerId}/bills/{invoiceNo}/itemBills",produces="application/json",consumes="application/json")
	@ResponseBody
	public ModelAndView addItemBillRequest(@PathVariable Long dealerId, @PathVariable Long invoiceNo,@Valid @RequestBody FirmSellBillItem firmSellBillItem){
		boolean s  = fsbRepository.existsById(invoiceNo);
		while(s==false) {
			if (fsbRepository.existsById(invoiceNo)==true) {
				break;
			}
				}
		return fsbRepository.findById(invoiceNo).map(firmSellBill -> {
			firmSellBillItem.setFirmSellBill(firmSellBill);
			 fsbiRepository.save(firmSellBillItem);
				ModelAndView mv = new ModelAndView("redirect:/dealerSellBillss/{dealerId}");
				return mv;
				}).orElseThrow(() -> new NotFoundException("Firm Invoice No not found!"));
		
		}
	
	@GetMapping("/dealerSellBills/{id}")
	public Page<FirmSellBill> showDealerSellBills(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill,
			@ModelAttribute @Valid @RequestBody FirmSellBillItem firmSellBillItem, Model model, Pageable pageable) {
		return fsbRepository.findByDealersId(id, pageable);
		 
	}
	@GetMapping("/dealerSellBillss/{id}")
	public ModelAndView showDealerSellBillss(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill,@ModelAttribute @Valid @RequestBody Product product,
			@ModelAttribute @Valid @RequestBody FirmSellBillItem firmPurchaseBillItem, Model model, Pageable pageable) {
		Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + id));
		long count_DealerBill = ds.getFirmSellBill().size();
		model.addAttribute("ds", ds);
		model.addAttribute("product", product);
		model.addAttribute("count_DealerBill", count_DealerBill);
		ModelAndView mv = new ModelAndView("showDealerSellBills.html");
		return mv;
		 
	}
	@GetMapping("/DealerViewFirmBill/{id}/bill/{invoiceNo}")
	public FirmSellBill showDealerItems(@PathVariable("id") long id,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill,
			@ModelAttribute @Valid @RequestBody FirmSellBillItem firmSellBillItem, Model model, Pageable pageable) {
		Optional<FirmSellBill> optFirmSellBill =fsbRepository.findById(invoiceNo);
    	if(optFirmSellBill.isPresent()) {
    		return optFirmSellBill.get();
    	}else {
    		throw new NotFoundException("Dealer not found with id " + id);
    	}
		 
	}
	@GetMapping("/printFirmBill/{DealerId}/bill/{invoiceNo}")
	public ModelAndView printBill(@PathVariable Long DealerId,@PathVariable Long invoiceNo,@ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill,
			@ModelAttribute @Valid @RequestBody FirmSellBillItem firmSellBillItem, Model model, Pageable pageable) {
		System.out.println("print");
		ModelAndView mv = new ModelAndView("printFirmSellBill.html");
		boolean s  = fsbRepository.existsById(invoiceNo);
		Dealers ds = dealerRepository.findById(DealerId).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + DealerId));
		while(s==false) {
			if (fsbRepository.existsById(invoiceNo)==true) {
				break;
			}
				}
		FirmSellBill firmSellBills = fsbRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + invoiceNo));
		List<FirmSellBillItem> itemBill = fsbiRepository.findByFirmSellBillId(invoiceNo);
		model.addAttribute("ds", ds);
		model.addAttribute("firmSellBills", firmSellBills);
		model.addAttribute("itemBill", itemBill);
		return mv;
		 
	}
	
	@GetMapping("/viewFirmBill/{id}/bill/{invoiceNo}")
	public ModelAndView ViewCustomerSellBills(@PathVariable("id") long id,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill,
			@ModelAttribute @Valid @RequestBody FirmSellBillItem firmSellBillItem, Model model, Pageable pageable) {
		Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dealer Id:" + id));
		FirmSellBill dfb = fsbRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid Invoice Id:" + invoiceNo));
		long count_DealerItemBill = dfb.getFirmSellBillItem().size();
		model.addAttribute("ds", ds);
		model.addAttribute("dfb", dfb);
		model.addAttribute("count_DealerItemBill", count_DealerItemBill);
		ModelAndView mv = new ModelAndView("showDealerSellItemBills.html");
		return mv;
		 
	}
	@GetMapping("/deleteFirmBill/{DealerId}/bill/{invoiceNo}")
	public ModelAndView DeleteFirmBill(@PathVariable Long DealerId,@PathVariable Long invoiceNo, @ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill, Model model, Pageable pageable) {
		Set<FirmSellBillItem> firmSellBillsItem = fsbRepository.findById(invoiceNo).get().getFirmSellBillItem();
		for (FirmSellBillItem d : firmSellBillsItem) {
			fsbiRepository.deleteById(d.getId());
			}
		fsbRepository.deleteById(invoiceNo);
		ModelAndView mv = new ModelAndView("redirect:/firmSell/dealerSellBillss/{DealerId}");
		return mv;
		}
	
	@GetMapping("/accStatePaid/{DealerId}/bill/{invoiceNo}/{state}")
	public ModelAndView accStatePaid(@PathVariable Long DealerId,@PathVariable Long invoiceNo, @PathVariable String state, @ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill, Model model, Pageable pageable) {
		FirmSellBill firmSellBills = fsbRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + invoiceNo));
		if(firmSellBills.getStatus().equals("Unpaid")) {
			firmSellBills.setPaidAmount(firmSellBills.getDueAmount());
			firmSellBills.setDueAmount("0");
			firmSellBills.setStatus(state);
			}
		fsbRepository.save(firmSellBills);
		ModelAndView mv = new ModelAndView("redirect:/firmSell/dealerSellBillss/{DealerId}");
		return mv;
		 
	}

	@GetMapping("/editFirmBill/{DealerId}/bill/{invoiceNo}")
	public ModelAndView editFirmBill(@PathVariable Long DealerId,@PathVariable Long invoiceNo,@ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill,
			@ModelAttribute @Valid @RequestBody FirmSellBillItem firmSellBillItem, Model model, Pageable pageable) {
		ModelAndView mv = new ModelAndView("editFirmSellBill.html");
		Dealers ds = dealerRepository.findById(DealerId).orElseThrow(() -> new IllegalArgumentException("Invalid Dealer Id:" + DealerId));
		FirmSellBill firmSellBills = fsbRepository.findById(invoiceNo).orElseThrow(() -> new IllegalArgumentException("Invalid Invoice Id:" + invoiceNo));
		List<FirmSellBillItem> firmSellBillItems = fsbiRepository.findAll();
		model.addAttribute("ds", ds);
		model.addAttribute("firmSellBills", firmSellBills);
		model.addAttribute("firmSellBillItems", firmSellBillItems);
		return mv;
		 
	}
	@GetMapping("/deleteItemBill/dealers/{DealerId}/bill/{invoiceNo}/itemBill/{itemBillId}")
	public ModelAndView deleteItemBill(@PathVariable Long DealerId,@PathVariable Long invoiceNo,@PathVariable Long itemBillId,@ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBills, Model model, Pageable pageable) {
		fsbiRepository.deleteById(itemBillId);
		System.out.println("Delete");
		ModelAndView mv = new ModelAndView("redirect:firmSell/viewFirmBill/{DealerId}/bill/{invoiceNo}");
		return mv;
		 
	}
	@PostMapping(value="/update/dealers/{DealerId}/bills/{invoiceNo}/bills",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public FirmSellBill updateFirmBillRequest(@PathVariable Long DealerId, @PathVariable Long invoiceNo,@Valid @RequestBody FirmSellBill firmSellBill) {
		return fsbRepository.findById(invoiceNo).map(firmSellBills -> {
			firmSellBills.setSubTotalAmount(firmSellBill.getSubTotalAmount());
			firmSellBills.setTotalItem(firmSellBill.getTotalItem());
			firmSellBills.setTotalWeight(firmSellBill.getTotalWeight());
			firmSellBills.setTotalNetWeight(firmSellBill.getTotalNetWeight());
			firmSellBills.setTotalCut(firmSellBill.getTotalCut());
			firmSellBills.setTotalCutAmo(firmSellBill.getTotalCutAmo());
			firmSellBills.setTotalOtherCut(firmSellBill.getTotalOtherCut());
			firmSellBills.setBrokerage(firmSellBill.getBrokerage());
			firmSellBills.setFare(firmSellBill.getFare());
			firmSellBills.setTotalAmount(firmSellBill.getTotalAmount());
			firmSellBills.setPaidAmount(firmSellBill.getPaidAmount());
			firmSellBills.setDueAmount(firmSellBill.getDueAmount());
			firmSellBills.setStatus(firmSellBill.getStatus());
			firmSellBills.setNote(firmSellBill.getNote());
			firmSellBills.setBillingDate(firmSellBill.getBillingDate());
			return fsbRepository.save(firmSellBills); 
			}).orElseThrow(() -> new NotFoundException("Dealer not found!"));
		
	    }
	
	@PostMapping(value="/update/dealers/{DealerId}/bills/{invoiceNo}/itemBills",produces="application/json",consumes="application/json")
	@ResponseBody
	@Transactional
	public List<FirmSellBillItem> updateItemBillRequest(@PathVariable Long DealerId, @PathVariable Long invoiceNo,@Valid @RequestBody FirmSellBillItem firmSellBillItem){
		List<FirmSellBillItem> arr = new ArrayList<FirmSellBillItem>();
    	arr.add(firmSellBillItem);
    	for (FirmSellBillItem d : arr) {
		Long I =  d.getId();
			System.out.println(I);
			Optional<FirmSellBillItem> itembills = fsbiRepository.findById(d.getId());
			FirmSellBillItem b = itembills.get();
			b.setProId(firmSellBillItem.getProId());	
		    b.setName(firmSellBillItem.getName());
		    b.setDescription(firmSellBillItem.getDescription());
		    b.setQty(firmSellBillItem.getQty());
		    b.setUnit(firmSellBillItem.getUnit());
		    b.setPrice(firmSellBillItem.getPrice());
		    b.setItemno(firmSellBillItem.getItemno());
		    b.setCut(firmSellBillItem.getCut());
		    b.setOtherCut(firmSellBillItem.getOtherCut());
		    b.setQtyCut(firmSellBillItem.getQtyCut());
		    b.setAmount(firmSellBillItem.getAmount());
			fsbiRepository.save(b);
			
		}
		return null;
		
		}
	@GetMapping("/view/{id}")
	public ModelAndView ViewCustomer(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody FirmSellBill firmSellBill,
			@ModelAttribute @Valid @RequestBody BuyBill buyBill, Model model, Pageable pageable) {
        Dealers ds = dealerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Dealer Id:" + id));
		model.addAttribute("ds", ds);
		ModelAndView mv = new ModelAndView("dealerView.html");
		return mv;
		 
	}

}
