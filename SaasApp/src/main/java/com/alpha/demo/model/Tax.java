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
@Table(name = "tax")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Tax implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long gst;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date",updatable=false)
	private Date createDate;
	public Tax() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Tax(Long id, Long gst) {
		super();
		this.id = id;
		this.gst = gst;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGst() {
		return gst;
	}
	public void setGst(Long gst) {
		this.gst = gst;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Tax(Long id, Long gst, Date createDate) {
		super();
		this.id = id;
		this.gst = gst;
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "Tax [id=" + id + ", gst=" + gst + ", createDate=" + createDate + "]";
	}

}
