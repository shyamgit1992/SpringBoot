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
@Table(name = "sellBills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class SellBill implements Serializable {
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
	private String dis;
	private String gst;
	private String amount;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sbill_id", nullable = false)
    @JsonIgnore
    private SBill sbill;

	

	

	public SellBill(Long id, String proId, String name, String description, String qty, String unit, String price,
			String dis, String gst, String amount, SBill sbill) {
		super();
		this.id = id;
		this.proId = proId;
		this.name = name;
		this.description = description;
		this.qty = qty;
		this.unit = unit;
		this.price = price;
		this.dis = dis;
		this.gst = gst;
		this.amount = amount;
		this.sbill = sbill;
	}

	public SellBill() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDis() {
		return dis;
	}

	public void setDis(String dis) {
		this.dis = dis;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public SBill getSbill() {
		return sbill;
	}

	public void setSbill(SBill sbill) {
		this.sbill = sbill;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	@Override
	public String toString() {
		return "SellBill [id=" + id + ", proId=" + proId + ", name=" + name + ", description=" + description + ", qty="
				+ qty + ", unit=" + unit + ", price=" + price + ", dis=" + dis + ", gst=" + gst + ", amount=" + amount
				+ "]";
	}

	
	
	
	

	

	
	

}
