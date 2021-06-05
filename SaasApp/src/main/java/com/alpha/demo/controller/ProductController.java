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

import com.alpha.demo.Repository.ProductRepository;
import com.alpha.demo.model.Product;



@RestController
@RequestMapping("/pro")
public class ProductController {
	@Autowired
	private ProductRepository proRepository;
	@PostMapping("/addProduct")
	public ModelAndView addProduct(@ModelAttribute @Valid @RequestBody Product product) {
		proRepository.save(product);
		ModelAndView mv = new ModelAndView("redirect:/pro/showProducts");
		return mv;
	}
	@GetMapping("/products")
	public List<Product> getProduct(@ModelAttribute @Valid @RequestBody Product product) {
		return proRepository.findAll();
		 
	}
	@GetMapping("/productsForBuy")
	public List<Product> getProductForBuy(@ModelAttribute @Valid @RequestBody Product product) {
		return proRepository.getBuy();
		 
	}
	@GetMapping("/productsForSell")
	public List<Product> getProductForSell(@ModelAttribute @Valid @RequestBody Product product) {
		return proRepository.getSell();
		 
	}
	@GetMapping("/productsForService")
	public List<Product> getProductForService(@ModelAttribute @Valid @RequestBody Product product) {
		return proRepository.getService();
		 
	}
	@GetMapping("/productsForDBuy")
	public List<Product> getProductForDBuy(@ModelAttribute @Valid @RequestBody Product product) {
		return proRepository.getDBuy();
		 
	}
	@GetMapping("/productsForDSell")
	public List<Product> getProductForDSell(@ModelAttribute @Valid @RequestBody Product product) {
		return proRepository.getDSell();
		 
	}
	@GetMapping("/deleteProduct/{id}")
	public ModelAndView DeleteProduct(@PathVariable Long id,@ModelAttribute @Valid @RequestBody Product product) {
		proRepository.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/pro/showProducts");
		return mv;
	}
	@GetMapping("/showProducts")
	public ModelAndView showProduct(@ModelAttribute @Valid @RequestBody Product product,Model model) {
		ModelAndView mv = new ModelAndView("showProducts.html");
		long count_Products = proRepository.findAll().size();
		model.addAttribute("count_Products", count_Products);
		model.addAttribute("product",product);
		return mv;
		}
	@GetMapping("/editProduct/{id}")
	public ModelAndView EditProduct(@PathVariable Long id,Model model) {
		ModelAndView mv = new ModelAndView("editProducts.html");
		Optional<Product> product = proRepository.findById(id);
		model.addAttribute("product",product);
		model.addAttribute("id",id);
		return mv;
	}
	@PostMapping("/updateProduct/{id}")
	public ModelAndView UpdateProduct(@PathVariable Long id,@ModelAttribute @Valid @RequestBody Product product,Model model) {
		ModelAndView mv = new ModelAndView("redirect:/pro/showProducts");
		Product pro = proRepository.getOne(id);
		System.out.println(pro);
		pro.setProductName(product.getProductName());
		pro.setProductBrand(product.getProductBrand());
		pro.setProductType(product.getProductType());
		pro.setProductDescription(product.getProductDescription());
		pro.setProductMeasure(product.getProductMeasure());
		pro.setProductPrice(product.getProductPrice());
		pro.setProductUnit(product.getProductUnit());
		pro.setProductNature(product.getProductNature());
		proRepository.save(pro);
		return mv;
	}
}
	
	


