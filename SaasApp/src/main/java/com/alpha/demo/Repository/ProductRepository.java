package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.demo.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query(value = "SELECT * FROM product where product_nature=\"ForBuying\";", nativeQuery = true) 
	List<Product> getBuy();
	@Query(value = "SELECT * FROM product where product_nature=\"ForSelling\";", nativeQuery = true) 
	List<Product> getSell();
	@Query(value = "SELECT * FROM product where product_nature=\"ForService\";", nativeQuery = true) 
	List<Product> getService();
	@Query(value = "SELECT * FROM product where product_nature=\"ForDBuying\";", nativeQuery = true) 
	List<Product> getDBuy();
	@Query(value = "SELECT * FROM product where product_nature=\"ForDSelling\";", nativeQuery = true) 
	List<Product> getDSell();
}
