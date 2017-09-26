package com.baqi.pweb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baqi.pweb.bean.Company;
import com.baqi.pweb.common.BaqiException;
import com.baqi.pweb.service.CompanyService;

@RestController
public class CompanyController{
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping("/companyList")
	public List<Company> companyList() throws BaqiException{
		List<Company> list=companyService.selectAll();
		return list;
	}
	
	@RequestMapping("/qryCompany")
	public Company qryCompany(int id) throws BaqiException{
		Company company=companyService.selectById(id);
		return company;
	}
	
	@RequestMapping("/addCompany")
	public void addCompany(String name,String taxno,String address,String tel,
			String bank,String bankno,String cardno,String owner) throws BaqiException{
		Company company=new Company();
		company.setName(name);
		company.setTaxno(taxno);
		company.setAddress(address);
		company.setTel(tel);
		company.setBank(bank);
		company.setBankno(bankno);
		company.setCardno(cardno);
		company.setOwner(owner);
		companyService.insert(company);
	}
	
	@RequestMapping("/updateCompany")
	public void updateCompany(int id,String name,String taxno,String address,String tel,
			String bank,String bankno,String cardno,String owner) throws BaqiException{
		Company company=new Company();
		company.setId(id);
		company.setName(name);
		company.setTaxno(taxno);
		company.setAddress(address);
		company.setTel(tel);
		company.setBank(bank);
		company.setBankno(bankno);
		company.setCardno(cardno);
		company.setOwner(owner);
		companyService.update(company);
	}
	
	@RequestMapping("/deleteCompany")
	public void deleteCompany(int id) throws BaqiException{
		companyService.delete(id);
	}
}
