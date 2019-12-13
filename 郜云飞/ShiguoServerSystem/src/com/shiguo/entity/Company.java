package com.shiguo.entity;

public class Company {
	
	private int companyid;
	private String verification;
	private String name;
	
	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
	public String getVerification() {
		return verification;
	}
	public void setVerification(String verification) {
		this.verification = verification;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Company [companyid=" + companyid + ", verification=" + verification + ", name=" + name + "]";
	}
}
