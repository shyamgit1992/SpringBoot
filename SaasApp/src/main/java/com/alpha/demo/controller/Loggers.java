package com.alpha.demo.controller;

import com.alpha.demo.model.Bill;
import com.alpha.demo.model.BuyBill;
import com.alpha.demo.model.Check;

public class Loggers{
	
	/*
	 * Check check; Bill bill;
	 */
	BuyBill buyBill;
	Bill bill;
	
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public BuyBill getBuyBill() {
		return buyBill;
	}

	public void setBuyBill(BuyBill buyBill) {
		this.buyBill = buyBill;
	}

	Check check;

	public Check getCheck() {
		return check;
	}

	public void setCheck(Check check) {
		this.check = check;
	}
	
	/*
	 * public String getFullname() { return fullname; } public void
	 * setFullname(String fullname) { this.fullname = fullname; } public Check
	 * getCheck() { return check; } public void setCheck(Check check) { this.check =
	 * check; } public Loggers() { super(); // TODO Auto-generated constructor stub
	 * } } public Loggers(Check check) { super(); this.check = check; }
	 */
	}
