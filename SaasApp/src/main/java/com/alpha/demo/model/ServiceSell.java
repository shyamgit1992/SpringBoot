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
@Table(name = "serviceSell")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class ServiceSell implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullName;
	private String address;
	private String mobileNo;
	private String itemTotalAmount;
	private String itemTotalPaidAmount;
	private String itemTotalDueAmount;
	private String transporterName;
	private String transporterNumber;
	private String transporterAddress;
	private String status;
	private String note;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "billing_date",updatable=false)
	private Date serviceCreateDate;
	@OneToMany(mappedBy = "serviceSell", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval=true) 
	private Set<ServiceBill> ServiceBill;
	public ServiceSell() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServiceSell(Long id, String fullName, String address, String mobileNo, String itemTotalAmount,
			String itemTotalPaidAmount, String itemTotalDueAmount, String transporterName, String transporterNumber,
			String transporterAddress, String status, String note, Date serviceCreateDate,
			Set<com.alpha.demo.model.ServiceBill> serviceBill) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.address = address;
		this.mobileNo = mobileNo;
		this.itemTotalAmount = itemTotalAmount;
		this.itemTotalPaidAmount = itemTotalPaidAmount;
		this.itemTotalDueAmount = itemTotalDueAmount;
		this.transporterName = transporterName;
		this.transporterNumber = transporterNumber;
		this.transporterAddress = transporterAddress;
		this.status = status;
		this.note = note;
		this.serviceCreateDate = serviceCreateDate;
		ServiceBill = serviceBill;
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
	public String getItemTotalAmount() {
		return itemTotalAmount;
	}
	public void setItemTotalAmount(String itemTotalAmount) {
		this.itemTotalAmount = itemTotalAmount;
	}
	public String getItemTotalPaidAmount() {
		return itemTotalPaidAmount;
	}
	public void setItemTotalPaidAmount(String itemTotalPaidAmount) {
		this.itemTotalPaidAmount = itemTotalPaidAmount;
	}
	public String getItemTotalDueAmount() {
		return itemTotalDueAmount;
	}
	public void setItemTotalDueAmount(String itemTotalDueAmount) {
		this.itemTotalDueAmount = itemTotalDueAmount;
	}
	public String getTransporterName() {
		return transporterName;
	}
	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}
	public String getTransporterNumber() {
		return transporterNumber;
	}
	public void setTransporterNumber(String transporterNumber) {
		this.transporterNumber = transporterNumber;
	}
	public String getTransporterAddress() {
		return transporterAddress;
	}
	public void setTransporterAddress(String transporterAddress) {
		this.transporterAddress = transporterAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getServiceCreateDate() {
		return serviceCreateDate;
	}
	public void setServiceCreateDate(Date serviceCreateDate) {
		this.serviceCreateDate = serviceCreateDate;
	}
	public Set<ServiceBill> getServiceBill() {
		return ServiceBill;
	}
	public void setServiceBill(Set<ServiceBill> serviceBill) {
		ServiceBill = serviceBill;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ServiceSell [id=" + id + ", fullName=" + fullName + ", address=" + address + ", mobileNo=" + mobileNo
				+ ", itemTotalAmount=" + itemTotalAmount + ", itemTotalPaidAmount=" + itemTotalPaidAmount
				+ ", itemTotalDueAmount=" + itemTotalDueAmount + ", transporterName=" + transporterName
				+ ", transporterNumber=" + transporterNumber + ", transporterAddress=" + transporterAddress
				+ ", status=" + status + ", note=" + note + ", serviceCreateDate=" + serviceCreateDate
				+ ", ServiceBill=" + ServiceBill + "]";
	}
	
	
	
	
}
