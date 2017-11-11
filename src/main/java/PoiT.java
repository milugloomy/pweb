import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class PoiT {

	public static void main(String[] args) throws IOException {
		XWPFDocument document = new XWPFDocument();
		
		//标题
		XWPFParagraph titleParagraph = document.createParagraph();  
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);  
        XWPFRun titleParagraphRun = titleParagraph.createRun();  
        titleParagraphRun.setText("公司详情");  
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setBold(true);
        titleParagraphRun.setFontSize(20);  
  
        
        writeline(document,1);
        writeline(document,2);
        writeline(document,3);
        writeline(document,4);
        writeline(document,5);
        
        FileOutputStream os = new FileOutputStream(new File("D://create_table.docx"));
        document.write(os);
        os.close();
        document.close();
	}
	
	public static void writeline(XWPFDocument document,int i){
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

}
