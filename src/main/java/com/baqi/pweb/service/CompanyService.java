package com.baqi.pweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baqi.pweb.bean.Company;
import com.baqi.pweb.common.BaqiException;
import com.baqi.pweb.dao.CompanyMapper;

@Service
public class CompanyService{
	@Autowired
	private CompanyMapper companyDao;

	public void insert(Company company) throws BaqiException{
		Company exisitCompany=companyDao.selectByName(company.getName());
		if(exisitCompany!=null){
			throw new BaqiException("company.already.exist");
		}
		companyDao.insert(company);
	}
	
	public List<Company> selectAll(){
		List<Company> list=companyDao.selectAll();
		return list;
	}
	
	public Company selectById(int id){
		Company company=companyDao.selectById(id);
		return company;
	}

	public void update(Company company) throws BaqiException {
		Company exisitCompany=companyDao.selectByName(company.getName());
		if(exisitCompany!=null && !exisitCompany.getId().equals(company.getId())){
			throw new BaqiException("company.already.exist");
		}
		companyDao.update(company);
	}
	
	public void delete(int id) {
		companyDao.delete(id);
	}
	
	
}
