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
@Table(name = "debit")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Debit implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String Debit;
	private String Note;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Debit_date",updatable=false)
	private Date DebitDate;
	public Debit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Debit(Long id, String debit, String note, Date debitDate) {
		super();
		this.id = id;
		Debit = debit;
		Note = note;
		DebitDate = debitDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDebit() {
		return Debit;
	}
	public void setDebit(String debit) {
		Debit = debit;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Debit [id=" + id + ", Debit=" + Debit + ", Note=" + Note + ", DebitDate=" + DebitDate + "]";
	}

	
}
