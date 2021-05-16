package com.alpha.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "DebitAmo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class DebitAmo implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String DebitAmount;
	private String DebitInt;
	private String Status;
	private String Note;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Debit_date",updatable=false)
	private Date DebitDate;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;
	public DebitAmo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public DebitAmo(Long id, String debitAmount, String debitInt, String status, String note, Date debitDate,
			Customer customer) {
		super();
		this.id = id;
		DebitAmount = debitAmount;
		DebitInt = debitInt;
		Status = status;
		Note = note;
		DebitDate = debitDate;
		this.customer = customer;
	}



	public String getDebitInt() {
		return DebitInt;
	}



	public void setDebitInt(String debitInt) {
		DebitInt = debitInt;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDebitAmount() {
		return DebitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		DebitAmount = debitAmount;
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

	public Date getDebitDate() {
		return DebitDate;
	}

	public void setDebitDate(Date debitDate) {
		DebitDate = debitDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "DebitAmo [id=" + id + ", DebitAmount=" + DebitAmount + ", DebitInt=" + DebitInt + ", Status=" + Status
				+ ", Note=" + Note + ", DebitDate=" + DebitDate + ", customer=" + customer + "]";
	}



	
 
}
