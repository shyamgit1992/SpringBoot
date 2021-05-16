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
@Table(name = "firmSellBillItems")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class FirmSellBillItem implements Serializable{
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
	private String itemno;
	private String cut;
	private String otherCut;
	private String qtyCut;
	private String amount;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "firm_sell_bill_id", nullable = false)
    @JsonIgnore
    private FirmSellBill firmSellBill;
	public FirmSellBillItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public FirmSellBillItem(Long id, String proId, String name, String description, String qty, String unit,
			String price, String itemno, String cut, String otherCut, String qtyCut, String amount,
			FirmSellBill firmSellBill) {
		super();
		this.id = id;
		this.proId = proId;
		this.name = name;
		this.description = description;
		this.qty = qty;
		this.unit = unit;
		this.price = price;
		this.itemno = itemno;
		this.cut = cut;
		this.otherCut = otherCut;
		this.qtyCut = qtyCut;
		this.amount = amount;
		this.firmSellBill = firmSellBill;
	}



	public String getQtyCut() {
		return qtyCut;
	}



	public void setQtyCut(String qtyCut) {
		this.qtyCut = qtyCut;
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
	
	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public String getCut() {
		return cut;
	}

	public void setCut(String cut) {
		this.cut = cut;
	}

	public String getOtherCut() {
		return otherCut;
	}

	public void setOtherCut(String otherCut) {
		this.otherCut = otherCut;
	}

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public FirmSellBill getFirmSellBill() {
		return firmSellBill;
	}
	public void setFirmSellBill(FirmSellBill firmSellBill) {
		this.firmSellBill = firmSellBill;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "FirmSellBillItem [id=" + id + ", proId=" + proId + ", name=" + name + ", description=" + description
				+ ", qty=" + qty + ", unit=" + unit + ", price=" + price + ", itemno=" + itemno + ", cut=" + cut
				+ ", otherCut=" + otherCut + ", qtyCut=" + qtyCut + ", amount=" + amount + "]";
	}

	
	
	

}
