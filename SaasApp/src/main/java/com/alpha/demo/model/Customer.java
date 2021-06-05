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
public class Customer implements Serializable {
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
	private String CredibilityStatus;
	private String SettlePaidStatus;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date",updatable=false)
	private Date createDate;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private Set<Bill> Bill;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private Set<SBill> SBill;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private Set<SerBill> SerBill;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private Set<CreditAmo> CreditAmo;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	private Set<DebitAmo> DebitAmo;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCredibilityStatus() {
		return CredibilityStatus;
	}
	public void setCredibilityStatus(String credibilityStatus) {
		CredibilityStatus = credibilityStatus;
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
	
	public Set<CreditAmo> getCreditAmo() {
		return CreditAmo;
	}
	public void setCreditAmo(Set<CreditAmo> creditAmo) {
		CreditAmo = creditAmo;
	}
	public Set<DebitAmo> getDebitAmo() {
		return DebitAmo;
	}
	public void setDebitAmo(Set<DebitAmo> debitAmo) {
		DebitAmo = debitAmo;
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

	public Set<SerBill> getSerBill() {
		return SerBill;
	}
	public void setSerBill(Set<SerBill> serBill) {
		SerBill = serBill;
	}
	
	
	
	public String getSettlePaidStatus() {
		return SettlePaidStatus;
	}
	public void setSettlePaidStatus(String settlePaidStatus) {
		SettlePaidStatus = settlePaidStatus;
	}
	
	public Customer(Long id, UUID uniqueId, String photos, String fullName, String aadhaarNo, String guarantor,
			String address, String mobileNo, String note, String credibilityStatus, String settlePaidStatus,
			Date createDate, Set<com.alpha.demo.model.Bill> bill, Set<com.alpha.demo.model.SBill> sBill,
			Set<com.alpha.demo.model.SerBill> serBill, Set<com.alpha.demo.model.CreditAmo> creditAmo,
			Set<com.alpha.demo.model.DebitAmo> debitAmo) {
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
		CredibilityStatus = credibilityStatus;
		SettlePaidStatus = settlePaidStatus;
		this.createDate = createDate;
		Bill = bill;
		SBill = sBill;
		SerBill = serBill;
		CreditAmo = creditAmo;
		DebitAmo = debitAmo;
	}
	public Customer() {
	}

}
