package com.alpha.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "credit")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Credit implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String Credit;
	private String Note;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Credit_date",updatable=false)
	private Date CreditDate;
	public Credit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Credit(Long id, String credit, String note, Date creditDate) {
		super();
		this.id = id;
		Credit = credit;
		Note = note;
		CreditDate = creditDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCredit() {
		return Credit;
	}
	public void setCredit(String credit) {
		Credit = credit;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Credit [id=" + id + ", Credit=" + Credit + ", Note=" + Note + ", CreditDate=" + CreditDate + "]";
	}
	
	
	
}
