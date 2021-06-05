package com.alpha.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.alpha.demo.Repository.CustomerRepository;
import com.alpha.demo.exception.NotFoundException;
import com.alpha.demo.model.Customer;

@RestController
@RequestMapping("/cust")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	public static String  UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/webapp/imagedata";
	//private final Path DIR = Paths.get("uploads");
	//String UPLOAD_DIR=new ClassPathResource("static/image/").getFile().getAbsolutePath();
	@GetMapping("/customer")
    public List<Customer> getAllCustomer() {
    	return customerRepository.findAll();
   }
    

    @GetMapping("/customers")
    public ModelAndView getAllCustomers(Customer customer, Model model) {
    	ModelAndView mv = new ModelAndView("showCustomers.html");
    	long count_Customers = customerRepository.count();
		/*
		 * for (int i = 0; i < count_Customers; i++) { System.out.println(i+1); long no
		 * = i+1; model.addAttribute("no", no); }
		 */
    	//System.out.println(count_Customers);
    	model.addAttribute("customer", customerRepository.findAll());
    	model.addAttribute("count_Customers", count_Customers);
    	return mv;
    }
    
	/*
	 * @GetMapping("/student") public List<Student> getAllStudentsJson() {
	 * 
	 * return studentRepository.findAll(); }
	 */
    @GetMapping("/credState/{CustomerId}/{state}")
	public ModelAndView credState(@PathVariable Long CustomerId, @PathVariable String state, Model model, Pageable pageable) {
		System.out.println(state);
		Customer customer = customerRepository.findById(CustomerId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + CustomerId));
		customer.setCredibilityStatus(state);
		customerRepository.save(customer);
		ModelAndView mv = new ModelAndView("redirect:/cust/customers");
		return mv;
		 
	}
    @GetMapping("/settlePaidState/{CustomerId}/{state}")
   	public String SettlePaidState(@PathVariable Long CustomerId, @PathVariable String state, Model model, Pageable pageable) {
   		System.out.println(state);
   		Customer customer = customerRepository.findById(CustomerId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + CustomerId));
   		customer.setSettlePaidStatus(state);
   		customerRepository.save(customer);
   		//ModelAndView mv = new ModelAndView("redirect:/cust/customers");
   		return null;
   		 
   	}
    
    @GetMapping("/customers/{id}")
    public Customer getCustomerByID(@PathVariable Long id) {
    	Optional<Customer> optCustomer = customerRepository.findById(id);
    	if(optCustomer.isPresent()) {
    		return optCustomer.get();
    	}else {
    		throw new NotFoundException("Customer not found with id " + id);
    	}
    }
    
    @PostMapping("/customersAdd")
    public ModelAndView createCustomer(@ModelAttribute @Valid @RequestBody @RequestParam("file") MultipartFile multipartFile, Customer customer, Model model) throws IOException {
    	ModelAndView mv = new ModelAndView("redirect:/cust/customers");
    	
    	 if (multipartFile.isEmpty()) {
          	ModelAndView mvc = new ModelAndView("redirect:/cust/customers");
          	String img = "first.jpg";
          	customer.setPhotos(img);
			/*
			 * if(customer.getSettlePaidStatus().isEmpty()) {
			 * customer.setSettlePaidStatus("Disable");
			 * 
			 * }
			 */
          	 if(customer.getAadhaarNo().isEmpty()) {
             	 customer.setAadhaarNo("None");
              }
              if( customer.getGuarantor().isEmpty()) {
             	 customer.setGuarantor("None");
              } 
              if(customer.getMobileNo().isEmpty()) {
             	 customer.setMobileNo(Integer.toString(0));
              } 
              if(customer.getNote().isEmpty()) {
             	 customer.setNote("None");
              }
              customer.setSettlePaidStatus("Disable");
              customer.setCredibilityStatus("Good"); 
          	  customerRepository.save(customer);
      	      model.addAttribute("customer", customer);
          	//model.addAttribute("customer_error", customer);
              return mvc;
              
          }
    	
		String filename=customer.getUniqueId() + multipartFile.getOriginalFilename();
		Path fileNameAndPath =Paths.get(UPLOAD_DIR,filename);
		try {
			Files.copy(multipartFile.getInputStream(),fileNameAndPath,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		customer.setPhotos(filename);
     	model.addAttribute("customer_success", customer);
        
		/*
		 * if(customer.getSettlePaidStatus().isEmpty()) {
		 * customer.setSettlePaidStatus("Disable");
		 * 
		 * }
		 */
         if(customer.getAadhaarNo().isEmpty()) {
        	 customer.setAadhaarNo("None");
        	
         }
         if( customer.getGuarantor().isEmpty()) {
        	 customer.setGuarantor("None");
         } 
         if(customer.getMobileNo().isEmpty()) {
        	 customer.setMobileNo(Integer.toString(0));
         } 
         if(customer.getNote().isEmpty()) {
        	 customer.setNote("None");
         }
        customer.setSettlePaidStatus("Disable");
        customer.setCredibilityStatus("Good"); 
     	customerRepository.save(customer);
 	    model.addAttribute("customer", customer);
        return mv;
    
	    }
    	 
    	
    @GetMapping("/customersUpdateImg/{id}")
    public ModelAndView  showCustomerUpdateImgForm(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody Customer customer,Model model) {
    	ModelAndView mv = new ModelAndView("customerUpdateImgForm.html");
        
    	Customer ct = customerRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    	System.out.println(ct.getId());
    	System.out.println(ct.getFullName());
    	//String v = StringUtils.replace(ct.getPhotos(), oldPattern, newPattern);
    	//System.out.println(v);
    	String Path = UPLOAD_DIR;
    	model.addAttribute("ct", ct);
    	model.addAttribute("Path", Path);
        return mv;
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView  showUpdateForm(@PathVariable("id") long id, @ModelAttribute @Valid @RequestBody Customer customer,Model model) {
    	ModelAndView mv = new ModelAndView("customerUpdateForm.html");
        
    	Customer ct = customerRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    	System.out.println(ct.getId());
    	System.out.println(ct.getFullName());
    	model.addAttribute("ct", ct);
        return mv;
    }
//    @PostMapping("/customersUpdateImage/{id}")
//    //@RequestMapping(value = "/studentsUpdate/{id}", method = RequestMethod.POST)
//    public ModelAndView updateCustomerImage(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id,@Valid Customer customerUpdated,Model model) {
//		
//    	 return customerRepository.findById(id)
//                 .map(customer -> {
//                	
//
//                         // validation
//                         if (multipartFile.isEmpty()) {
//                         	ModelAndView mvc = new ModelAndView("redirect:/cust/customersUpdateImg/{id}");
//                         	model.addAttribute("customer_error", customer);
//                            return mvc;
//                             
//                         }
//
//
//                         String filename=customer.getUniqueId() + multipartFile.getOriginalFilename();
//                 		Path fileNameAndPath =Paths.get(UPLOAD_DIR,filename);
//                 		try {
//                 			Files.copy(multipartFile.getInputStream(),fileNameAndPath,StandardCopyOption.REPLACE_EXISTING);
//                 		} catch (IOException e) {
//                 			e.printStackTrace();
//                 		}
//                 		customer.setPhotos(filename);
//                      	model.addAttribute("customer_success", customer);
//
//                  
//                     
//                     ModelAndView mv = new ModelAndView("redirect:/cust/customers");
//                     customerRepository.save(customer);
//                     return mv;
//                 }).orElseThrow(() -> new NotFoundException("Customer not found with id " + id));
//    	
//    }
//    @PostMapping("/customersUpdate/{id}")
//    //@RequestMapping(value = "/studentsUpdate/{id}", method = RequestMethod.POST)
//    public ModelAndView updateCustomer(@PathVariable Long id,
//                                   @Valid Customer customerUpdated) {
//        return customerRepository.findById(id)
//                .map(customer -> {
//                    customer.setFullName(customerUpdated.getFullName());
//                    customer.setAadhaarNo(customerUpdated.getAadhaarNo());
//                    customer.setGuarantor(customerUpdated.getGuarantor());
//                    customer.setMobileNo(customerUpdated.getMobileNo());
//                    customer.setAddress(customerUpdated.getAddress());
//                    customer.setNote(customerUpdated.getNote());
//                    ModelAndView mv = new ModelAndView("redirect:/cust/customers");
//                    customerRepository.save(customer);
//                    return mv;
//                }).orElseThrow(() -> new NotFoundException("Customer not found with id " + id));
//    }
    @PostMapping("/customersUpdate/{id}")
    //@RequestMapping(value = "/studentsUpdate/{id}", method = RequestMethod.POST)
    public ModelAndView updateCustomer(@ModelAttribute @Valid @RequestBody @RequestParam("file") MultipartFile multipartFile,@PathVariable Long id,@Valid Customer customerUpdated,Model model) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFullName(customerUpdated.getFullName());
                    customer.setAadhaarNo(customerUpdated.getAadhaarNo());
                    customer.setGuarantor(customerUpdated.getGuarantor());
                    customer.setMobileNo(customerUpdated.getMobileNo());
                    customer.setAddress(customerUpdated.getAddress());
                    customer.setNote(customerUpdated.getNote());
                    // validation
                    //System.out.println(customerUpdated.getPhotos());
                    //String img="fourth.jpg";
                    if (multipartFile.isEmpty()) {
                    	 customer.setFullName(customerUpdated.getFullName());
                         customer.setAadhaarNo(customerUpdated.getAadhaarNo());
                         customer.setGuarantor(customerUpdated.getGuarantor());
                         customer.setMobileNo(customerUpdated.getMobileNo());
                         customer.setAddress(customerUpdated.getAddress());
                         customer.setNote(customerUpdated.getNote());
                         customerRepository.save(customer);
                    	ModelAndView mvc = new ModelAndView("redirect:/cust/customers");
                     	model.addAttribute("customer_error", customer);
                        return mvc;
                       
                    }


                    String filename=customer.getUniqueId() + multipartFile.getOriginalFilename();
            		Path fileNameAndPath =Paths.get(UPLOAD_DIR,filename);
            		try {
            			Files.copy(multipartFile.getInputStream(),fileNameAndPath,StandardCopyOption.REPLACE_EXISTING);
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
            		customer.setPhotos(filename);
                 	model.addAttribute("customer_success", customer);
                    ModelAndView mv = new ModelAndView("redirect:/cust/customers");
                    customerRepository.save(customer);
                    return mv;
                }).orElseThrow(() -> new NotFoundException("Customer not found with id " + id));
    }
    
    @RequestMapping(value = "/customersDel/{id}", method = RequestMethod.GET)
  //  @DeleteMapping("/studentsDel/{id}")
    public ModelAndView deleteCustomer(@PathVariable Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer);
                    ModelAndView mv = new ModelAndView("redirect:/cust/customers");
                    return mv;
                }).orElseThrow(() -> new NotFoundException("Customer not found with id " + id));
    }
    

}
