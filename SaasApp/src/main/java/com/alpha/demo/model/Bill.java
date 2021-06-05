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
@Table(name = "bills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Bill implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String guarantorName;
	private String TotalGSTAmount;
	private String TotalDiscountAmount;
	private String SubtotalAmount;
	private String TotalAmount;
	private String PaidAmount;
	private String DueAmount;
	private String InterestRate;
	private String TotalInterestAmount;
	private String Status;
	private String SettleStatus;
	private String SettleDays;
	private String Note;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "billing_date",updatable=false)
	private Date BillingDate;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;
	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval=true) 
	private Set<BuyBill> BuyBill;
  
	

	
	public Bill() {
		
	}
	
	public String getSettleStatus() {
		return SettleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		SettleStatus = settleStatus;
	}

	public String getSettleDays() {
		return SettleDays;
	}

	public void setSettleDays(String settleDays) {
		SettleDays = settleDays;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGuarantorName() {
		return guarantorName;
	}
	public void setGuarantorName(String guarantorName) {
		this.guarantorName = guarantorName;
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
	public String getInterestRate() {
		return InterestRate;
	}
	public void setInterestRate(String interestRate) {
		InterestRate = interestRate;
	}
	public String getTotalInterestAmount() {
		return TotalInterestAmount;
	}
	public void setTotalInterestAmount(String totalInterestAmount) {
		TotalInterestAmount = totalInterestAmount;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
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
	public Set<BuyBill> getBuyBill() {
		return BuyBill;
	}
	public void setBuyBill(Set<BuyBill> buyBill) {
		BuyBill = buyBill;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	
	public Bill(Long id, String guarantorName, String totalGSTAmount, String totalDiscountAmount, String subtotalAmount,
			String totalAmount, String paidAmount, String dueAmount, String interestRate, String totalInterestAmount,
			String status, String settleStatus, String settleDays, String note, Date billingDate, Customer customer,
			Set<com.alpha.demo.model.BuyBill> buyBill) {
		super();
		this.id = id;
		this.guarantorName = guarantorName;
		TotalGSTAmount = totalGSTAmount;
		TotalDiscountAmount = totalDiscountAmount;
		SubtotalAmount = subtotalAmount;
		TotalAmount = totalAmount;
		PaidAmount = paidAmount;
		DueAmount = dueAmount;
		InterestRate = interestRate;
		TotalInterestAmount = totalInterestAmount;
		Status = status;
		SettleStatus = settleStatus;
		SettleDays = settleDays;
		Note = note;
		BillingDate = billingDate;
		this.customer = customer;
		BuyBill = buyBill;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", guarantorName=" + guarantorName + ", TotalGSTAmount=" + TotalGSTAmount
				+ ", TotalDiscountAmount=" + TotalDiscountAmount + ", SubtotalAmount=" + SubtotalAmount
				+ ", TotalAmount=" + TotalAmount + ", PaidAmount=" + PaidAmount + ", DueAmount=" + DueAmount
				+ ", InterestRate=" + InterestRate + ", TotalInterestAmount=" + TotalInterestAmount + ", Status="
				+ Status + ", SettleStatus=" + SettleStatus + ", SettleDays=" + SettleDays + ", Note=" + Note
				+ ", BillingDate=" + BillingDate + ", customer=" + customer + ", BuyBill=" + BuyBill + "]";
	}

	
	
	

	 
}
