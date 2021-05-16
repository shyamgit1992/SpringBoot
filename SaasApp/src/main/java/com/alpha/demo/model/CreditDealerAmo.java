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
@Table(name = "creditDealerAmo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class CreditDealerAmo implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String CreditAmount;
	private String Status;
	private String Note;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Credit_date",updatable=false)
	private Date CreditDate;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dealers_id", nullable = false)
    @JsonIgnore
    private Dealers dealers;
	
	public CreditDealerAmo() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public CreditDealerAmo(Long id, String creditAmount, String status, String note, Date creditDate, Dealers dealers) {
		super();
		this.id = id;
		CreditAmount = creditAmount;
		Status = status;
		Note = note;
		CreditDate = creditDate;
		this.dealers = dealers;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreditAmount() {
		return CreditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		CreditAmount = creditAmount;
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

	public Date getCreditDate() {
		return CreditDate;
	}

	public void setCreditDate(Date creditDate) {
		CreditDate = creditDate;
	}

	
	public Dealers getDealers() {
		return dealers;
	}


	public void setDealers(Dealers dealers) {
		this.dealers = dealers;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "CreditDealerAmo [id=" + id + ", CreditAmount=" + CreditAmount + ", Status=" + Status + ", Note=" + Note
				+ ", CreditDate=" + CreditDate + ", dealers=" + dealers + "]";
	}

	
	

}
