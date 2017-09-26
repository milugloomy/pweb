package com.baqi.pweb.dao;

import java.util.List;

import com.baqi.pweb.bean.Company;

public interface CompanyMapper {
    int insert(Company company);
    
    Company selectByName(String name);

	Company selectById(int id);
	
	List<Company> selectAll();

	void update(Company company);

	void delete(int id);

}