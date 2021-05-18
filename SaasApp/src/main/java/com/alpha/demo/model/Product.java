package com.alpha.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productName;
	private String productBrand;
	private String productType;
	private String productDescription;
	private String productMeasure;
	private String productPrice;
	private String productUnit;
	private String productNature;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date",updatable=false)
	private Date createDate;
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(Long id, String productName, String productBrand, String productType, String productDescription,
			String productMeasure, String productPrice, String productUnit, String productNature, Date createDate) {
		super();
		this.id = id;
		this.productName = productName;
		this.productBrand = productBrand;
		this.productType = productType;
		this.productDescription = productDescription;
		this.productMeasure = productMeasure;
		this.productPrice = productPrice;
		this.productUnit = productUnit;
		this.productNature = productNature;
		this.createDate = createDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductMeasure() {
		return productMeasure;
	}
	public void setProductMeasure(String productMeasure) {
		this.productMeasure = productMeasure;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public String getProductNature() {
		return productNature;
	}
	public void setProductNature(String productNature) {
		this.productNature = productNature;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productBrand=" + productBrand
				+ ", productType=" + productType + ", productDescription=" + productDescription + ", productMeasure="
				+ productMeasure + ", productPrice=" + productPrice + ", productUnit=" + productUnit
				+ ", productNature=" + productNature + ", createDate=" + createDate + "]";
	}
	
}
