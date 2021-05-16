package com.alpha.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "firmSellBills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class FirmSellBill implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String Brokerage;
	private String Fare;
	private String SubTotalAmount;
	private String TotalItem;
	private String TotalWeight;
	private String TotalNetWeight;
	private String TotalCut;
	private String TotalCutAmo;
	private String TotalOtherCut;
	private String TotalAmount;
	private String PaidAmount;
	private String DueAmount;
	private String Status;
	private String Note;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "billing_date",updatable=false)
	private Date BillingDate;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dealers_id", nullable = false)
    @JsonIgnore
    private Dealers dealers;
	@OneToMany(mappedBy = "firmSellBill", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval=true) 
	private Set<FirmSellBillItem> FirmSellBillItem;
	public FirmSellBill() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public FirmSellBill(Long id, String brokerage, String fare, String subTotalAmount, String totalItem,
			String totalWeight, String totalNetWeight, String totalCut, String totalCutAmo, String totalOtherCut,
			String totalAmount, String paidAmount, String dueAmount, String status, String note, Date billingDate,
			Dealers dealers, Set<com.alpha.demo.model.FirmSellBillItem> firmSellBillItem) {
		super();
		this.id = id;
		Brokerage = brokerage;
		Fare = fare;
		SubTotalAmount = subTotalAmount;
		TotalItem = totalItem;
		TotalWeight = totalWeight;
		TotalNetWeight = totalNetWeight;
		TotalCut = totalCut;
		TotalCutAmo = totalCutAmo;
		TotalOtherCut = totalOtherCut;
		TotalAmount = totalAmount;
		PaidAmount = paidAmount;
		DueAmount = dueAmount;
		Status = status;
		Note = note;
		BillingDate = billingDate;
		this.dealers = dealers;
		FirmSellBillItem = firmSellBillItem;
	}



	public String getTotalWeight() {
		return TotalWeight;
	}



	public void setTotalWeight(String totalWeight) {
		TotalWeight = totalWeight;
	}



	public String getTotalNetWeight() {
		return TotalNetWeight;
	}



	public void setTotalNetWeight(String totalNetWeight) {
		TotalNetWeight = totalNetWeight;
	}



	public String getTotalItem() {
		return TotalItem;
	}
	public void setTotalItem(String totalItem) {
		TotalItem = totalItem;
	}
	public String getTotalCut() {
		return TotalCut;
	}
	public void setTotalCut(String totalCut) {
		TotalCut = totalCut;
	}
	
	public String getTotalCutAmo() {
		return TotalCutAmo;
	}

	public void setTotalCutAmo(String totalCutAmo) {
		TotalCutAmo = totalCutAmo;
	}

	public String getTotalOtherCut() {
		return TotalOtherCut;
	}
	public void setTotalOtherCut(String totalOtherCut) {
		TotalOtherCut = totalOtherCut;
	}
	public String getSubTotalAmount() {
		return SubTotalAmount;
	}



	public void setSubTotalAmount(String subTotalAmount) {
		SubTotalAmount = subTotalAmount;
	}



	public String getBrokerage() {
		return Brokerage;
	}


	public void setBrokerage(String brokerage) {
		Brokerage = brokerage;
	}


	public String getFare() {
		return Fare;
	}


	public void setFare(String fare) {
		Fare = fare;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		TotalAmount = totalAmount;
	}
	public String getPaidAmount() {
		return PaidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		PaidAmount = paidAmount;
	}
	public String getDueAmount() {
		return DueAmount;
	}
	public void setDueAmount(String dueAmount) {
		DueAmount = dueAmount;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	public Date getBillingDate() {
		return BillingDate;
	}
	public void setBillingDate(Date billingDate) {
		BillingDate = billingDate;
	}
	public Dealers getDealers() {
		return dealers;
	}
	public void setDealers(Dealers dealers) {
		this.dealers = dealers;
	}
	public Set<FirmSellBillItem> getFirmSellBillItem() {
		return FirmSellBillItem;
	}
	public void setFirmSellBillItem(Set<FirmSellBillItem> firmSellBillItem) {
		FirmSellBillItem = firmSellBillItem;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "FirmSellBill [id=" + id + ", Brokerage=" + Brokerage + ", Fare=" + Fare + ", SubTotalAmount="
				+ SubTotalAmount + ", TotalItem=" + TotalItem + ", TotalWeight=" + TotalWeight + ", TotalNetWeight="
				+ TotalNetWeight + ", TotalCut=" + TotalCut + ", TotalCutAmo=" + TotalCutAmo + ", TotalOtherCut="
				+ TotalOtherCut + ", TotalAmount=" + TotalAmount + ", PaidAmount=" + PaidAmount + ", DueAmount="
				+ DueAmount + ", Status=" + Status + ", Note=" + Note + ", BillingDate=" + BillingDate + ", dealers="
				+ dealers + ", FirmSellBillItem=" + FirmSellBillItem + "]";
	}



}
