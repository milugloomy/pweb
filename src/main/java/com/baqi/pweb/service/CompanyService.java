package com.baqi.pweb.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baqi.pweb.bean.Company;
import com.baqi.pweb.common.BaqiException;
import com.baqi.pweb.dao.CompanyMapper;

@Service
public class CompanyService{
	@Value("${doc.path}")
    private String docPath;
	@Autowired
	private CompanyMapper companyDao;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");

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
	
	public String downDoc() throws BaqiException{
		List<Company> list=selectAll();
		
		XWPFDocument document = null;
		FileOutputStream os = null;
		
		String fileName=sdf.format(new Date())+".docx";
		try{
			document = new XWPFDocument();
			os = new FileOutputStream(new File(docPath+fileName));
			//标题
			XWPFParagraph titleParagraph = document.createParagraph();  
	        titleParagraph.setAlignment(ParagraphAlignment.CENTER);  
	        XWPFRun titleParagraphRun = titleParagraph.createRun();  
	        titleParagraphRun.setText("公司详情");  
	        titleParagraphRun.setColor("000000");
	        titleParagraphRun.setBold(true);
	        titleParagraphRun.setFontSize(20);  
	  
	        for(int i=0;i<list.size();i++){
	        	XWPFParagraph para0 = document.createParagraph();  
	    		XWPFRun run0 = para0.createRun();
	    		run0.setColor("FF0000");
	    		run0.setText(i+"、");
	    		
	    		XWPFParagraph para1 = document.createParagraph();  
	    		XWPFRun run1 = para1.createRun();
	    		run1.setBold(true); //加粗 
	    		run1.setFontSize(14);
	    		run1.setText("公司名：");
	    		run1 = para1.createRun();  
	    		run1.setFontSize(14);
	    		run1.setText("我的公司");
	    		XWPFParagraph para2 = document.createParagraph();  
	    		XWPFRun run2 = para2.createRun();
	    		run2.setBold(true); //加粗  
	    		run2.setFontSize(14);
	    		run2.setText("电    话：");
	    		run2 = para2.createRun();  
	    		run2.setFontSize(14);
	    		run2.setText("13807191145");
	    		
	    		//换行  
	            XWPFRun runBlank = document.createParagraph().createRun();  
	            runBlank.setText("\r");
	        }
	        document.write(os);
		}catch(IOException e){
			throw new BaqiException(e.getMessage());
		}finally{
	        try {
				os.close();
		        document.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
	
}
