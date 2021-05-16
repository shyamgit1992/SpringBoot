package com.alpha.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

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
@Table(name = "customers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class CustomerService implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "uniqueId", updatable = false, nullable = false)
    private UUID uniqueId = UUID.randomUUID();
	@Column(columnDefinition = "TEXT")
    private String photos;
	private String fullName;
	private String aadhaarNo;
	private String guarantor;
	private String address;
	private String mobileNo;
	private String note;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date",updatable=false)
	private Date createDate;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private Set<Bill> Bill;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private Set<SBill> SBill;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UUID getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(UUID uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public String getAadhaarNo() {
		return aadhaarNo;
	}
	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}
	public String getGuarantor() {
		return guarantor;
	}
	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Set<Bill> getBill() {
		return Bill;
	}
	public void setBill(Set<Bill> bill) {
		Bill = bill;
	}
	
	public Set<SBill> getSBill() {
		return SBill;
	}
	public void setSBill(Set<SBill> sBill) {
		SBill = sBill;
	}

	public CustomerService(Long id, UUID uniqueId, String photos, String fullName, String aadhaarNo, String guarantor,
			String address, String mobileNo, String note, Date createDate, Set<com.alpha.demo.model.Bill> bill,
			Set<com.alpha.demo.model.SBill> sBill) {
		super();
		this.id = id;
		this.uniqueId = uniqueId;
		this.photos = photos;
		this.fullName = fullName;
		this.aadhaarNo = aadhaarNo;
		this.guarantor = guarantor;
		this.address = address;
		this.mobileNo = mobileNo;
		this.note = note;
		this.createDate = createDate;
		Bill = bill;
		SBill = sBill;
	}
	public CustomerService() {
	}

}
