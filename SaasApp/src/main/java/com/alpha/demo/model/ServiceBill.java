package com.alpha.demo.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "serviceBills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class ServiceBill implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String proId;
	private String name;
	private String description;
	private String qty;
	private String unit;
	private String price;
	private String amount;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "serviceSell_id", nullable = false)
    @JsonIgnore
    private ServiceSell serviceSell;
	public ServiceBill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServiceBill(Long id, String proId, String name, String description, String qty, String unit, String price,
			String amount, ServiceSell serviceSell) {
		super();
		this.id = id;
		this.proId = proId;
		this.name = name;
		this.description = description;
		this.qty = qty;
		this.unit = unit;
		this.price = price;
		this.amount = amount;
		this.serviceSell = serviceSell;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public ServiceSell getServiceSell() {
		return serviceSell;
	}
	public void setServiceSell(ServiceSell serviceSell) {
		this.serviceSell = serviceSell;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ServiceBill [id=" + id + ", proId=" + proId + ", name=" + name + ", description=" + description
				+ ", qty=" + qty + ", unit=" + unit + ", price=" + price + ", amount=" + amount + "]";
	}
}
