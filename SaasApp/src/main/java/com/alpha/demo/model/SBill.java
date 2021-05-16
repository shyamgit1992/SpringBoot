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
@Table(name = "sbills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class SBill implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String TotalGSTAmount;
	private String TotalDiscountAmount;
	private String SubtotalAmount;
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
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;
	@OneToMany(mappedBy = "sbill", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval=true) 
	private Set<SellBill> SellBill;
	public SBill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SBill(Long id, String totalGSTAmount, String totalDiscountAmount, String subtotalAmount, String totalAmount,
			String paidAmount, String dueAmount, String status, String note, Date billingDate, Customer customer,
			Set<com.alpha.demo.model.SellBill> sellBill) {
		super();
		this.id = id;
		TotalGSTAmount = totalGSTAmount;
		TotalDiscountAmount = totalDiscountAmount;
		SubtotalAmount = subtotalAmount;
		TotalAmount = totalAmount;
		PaidAmount = paidAmount;
		DueAmount = dueAmount;
		Status = status;
		Note = note;
		BillingDate = billingDate;
		this.customer = customer;
		SellBill = sellBill;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTotalGSTAmount() {
		return TotalGSTAmount;
	}
	public void setTotalGSTAmount(String totalGSTAmount) {
		TotalGSTAmount = totalGSTAmount;
	}
	public String getTotalDiscountAmount() {
		return TotalDiscountAmount;
	}
	public void setTotalDiscountAmount(String totalDiscountAmount) {
		TotalDiscountAmount = totalDiscountAmount;
	}
	public String getSubtotalAmount() {
		return SubtotalAmount;
	}
	public void setSubtotalAmount(String subtotalAmount) {
		SubtotalAmount = subtotalAmount;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Set<SellBill> getSellBill() {
		return SellBill;
	}
	public void setSellBill(Set<SellBill> sellBill) {
		SellBill = sellBill;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "SBill [id=" + id + ", TotalGSTAmount=" + TotalGSTAmount + ", TotalDiscountAmount=" + TotalDiscountAmount
				+ ", SubtotalAmount=" + SubtotalAmount + ", TotalAmount=" + TotalAmount + ", PaidAmount=" + PaidAmount
				+ ", DueAmount=" + DueAmount + ", Status=" + Status + ", Note=" + Note + ", BillingDate=" + BillingDate
				+ ", customer=" + customer + ", SellBill=" + SellBill + "]";
	}
	
	}

