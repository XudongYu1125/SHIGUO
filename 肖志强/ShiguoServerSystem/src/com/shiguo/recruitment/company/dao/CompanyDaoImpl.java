package com.shiguo.recruitment.company.dao;

import java.util.List;

import com.shiguo.entity.Company;
import com.shiguo.util.DBUtil;

public class CompanyDaoImpl {
	
	public int insertCompany(Company company) {
		return DBUtil.executeUpdate("insert into company(name,verification) values (?,?)", 
									new Object[] {company.getName(),company.getVerification()});
	}
	
	public int deleteCompany(int id) {
		return DBUtil.executeUpdate("delete from company where companyid=?", new Object[] {id});
	}
	
	public int updateCompany(Company company) {	
		return DBUtil.executeUpdate("update company set name=?,verification=? where companyid=?", 
									new Object[] {company.getName(),company.getVerification(),company.getCompanyid()});
	}
	
	public List<Company> findAllCompany() {
		return DBUtil.findAll(Company.class,"select * from company");
	}
	
	public Company findCompanyById(int id) {
		Object obj = DBUtil.findById(Company.class,"select * from company where companyid=?",id);
		return obj != null ? (Company)obj : null;
	}
	public Company findCompanyByName(String name) {
		List<Company> companyList = DBUtil.find(Company.class, "select * from company where name=?", new Object[] {name});
		if(companyList == null) {
			return null;
		}
		if(companyList.size()==0) {
			return null;
		}
		Object obj = DBUtil.find(Company.class, "select * from company where name=?", new Object[] {name}).get(0);
		return obj != null ? (Company)obj : null;
	}
	
}
