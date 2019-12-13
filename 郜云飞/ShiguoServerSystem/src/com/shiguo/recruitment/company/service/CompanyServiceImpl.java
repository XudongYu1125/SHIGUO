package com.shiguo.recruitment.company.service;

import java.util.List;

import com.shiguo.entity.Company;
import com.shiguo.entity.User;
import com.shiguo.personal.user.service.UserServiceImpl;
import com.shiguo.recruitment.company.dao.CompanyDaoImpl;

public class CompanyServiceImpl {
	
	public boolean addCompany(Company company) {
		
		return new CompanyDaoImpl().insertCompany(company) > 0;
		
	}
	
	public boolean dropCompany(int id) {
		
		return new CompanyDaoImpl().deleteCompany(id) > 0;
		
	}
	
	public boolean editCompany(Company company) {

		return new CompanyDaoImpl().updateCompany(company) > 0;
		
	}
	
	public List<Company> companyList() {
		
		return new CompanyDaoImpl().findAllCompany();
		
	}
	
	public Company searchCompanyById(int id) {
		
		return new CompanyDaoImpl().findCompanyById(id);
		
	}
	
	public int certificateCompany(int userid, String name, String verification) {
		
		Object obj = new CompanyDaoImpl().findCompanyByName(name);
		
		if(obj != null) {
			Company result = (Company)obj;
			if(result.getVerification().equals(verification)) {
				System.out.println("user");
				User user = new UserServiceImpl().searchUserById(userid);
				user.setCompanyid(result.getCompanyid());
				new UserServiceImpl().editUser(user);
				return result.getCompanyid();
				
			}else {
				return 0;
			}
		}else {
			return 0;
		}
		
	}
	
}
