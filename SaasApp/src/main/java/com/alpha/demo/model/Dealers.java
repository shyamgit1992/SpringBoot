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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "dealers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Dealers implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullName;
	private String aadhaarNo;
	private String dealerShipDetails;
	private String gstnNo;
	private String bankName;
	private String bankAccNo;
	private String bankAccOnName;
	private String bankAccType;
	private String bankIfscCode;
	private String address;
	private String mobileNo;
	private String note;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date",updatable=false)
	private Date createDate;
	@OneToMany(mappedBy = "dealers", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private Set<FirmPurchaseBill> FirmPurchaseBill;
	@OneToMany(mappedBy = "dealers", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private Set<FirmSellBill> FirmSellBill;
	public Dealers() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Dealers(Long id, String fullName, String aadhaarNo, String dealerShipDetails, String gstnNo, String bankName,
			String bankAccNo, String bankAccOnName, String bankAccType, String bankIfscCode, String address,
			String mobileNo, String note, Date createDate, Set<com.alpha.demo.model.FirmPurchaseBill> firmPurchaseBill,
			Set<com.alpha.demo.model.FirmSellBill> firmSellBill) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.aadhaarNo = aadhaarNo;
		this.dealerShipDetails = dealerShipDetails;
		this.gstnNo = gstnNo;
		this.bankName = bankName;
		this.bankAccNo = bankAccNo;
		this.bankAccOnName = bankAccOnName;
		this.bankAccType = bankAccType;
		this.bankIfscCode = bankIfscCode;
		this.address = address;
		this.mobileNo = mobileNo;
		this.note = note;
		this.createDate = createDate;
		FirmPurchaseBill = firmPurchaseBill;
		FirmSellBill = firmSellBill;
	}

	public Set<FirmSellBill> getFirmSellBill() {
		return FirmSellBill;
	}

	public void setFirmSellBill(Set<FirmSellBill> firmSellBill) {
		FirmSellBill = firmSellBill;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAadhaarNo() {
		return aadhaarNo;
	}
	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}
	public String getDealerShipDetails() {
		return dealerShipDetails;
	}
	public void setDealerShipDetails(String dealerShipDetails) {
		this.dealerShipDetails = dealerShipDetails;
	}
	public String getGstnNo() {
		return gstnNo;
	}
	public void setGstnNo(String gstnNo) {
		this.gstnNo = gstnNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getBankAccOnName() {
		return bankAccOnName;
	}
	public void setBankAccOnName(String bankAccOnName) {
		this.bankAccOnName = bankAccOnName;
	}
	public String getBankAccType() {
		return bankAccType;
	}
	public void setBankAccType(String bankAccType) {
		this.bankAccType = bankAccType;
	}
	public String getBankIfscCode() {
		return bankIfscCode;
	}
	public void setBankIfscCode(String bankIfscCode) {
		this.bankIfscCode = bankIfscCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Set<FirmPurchaseBill> getFirmPurchaseBill() {
		return FirmPurchaseBill;
	}
	public void setFirmPurchaseBill(Set<FirmPurchaseBill> firmPurchaseBill) {
		FirmPurchaseBill = firmPurchaseBill;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Dealers [id=" + id + ", fullName=" + fullName + ", aadhaarNo=" + aadhaarNo + ", dealerShipDetails="
				+ dealerShipDetails + ", gstnNo=" + gstnNo + ", bankName=" + bankName + ", bankAccNo=" + bankAccNo
				+ ", bankAccOnName=" + bankAccOnName + ", bankAccType=" + bankAccType + ", bankIfscCode=" + bankIfscCode
				+ ", address=" + address + ", mobileNo=" + mobileNo + ", note=" + note + ", createDate=" + createDate
				+ ", FirmPurchaseBill=" + FirmPurchaseBill + ", FirmSellBill=" + FirmSellBill + "]";
	}
	
	
	


}
