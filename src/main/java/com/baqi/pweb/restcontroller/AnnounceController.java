package com.baqi.pweb.restcontroller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Connection.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baqi.pweb.bean.Announce;
import com.baqi.pweb.common.BaqiException;
import com.baqi.pweb.service.AnnounceService;

@RestController
public class AnnounceController{
	
	@Autowired
	private AnnounceService announceService;
	
	@RequestMapping("/lastWeek")
	public List<Announce> lastWeek() throws IOException, BaqiException{
		String url="http://www.ccgp-hubei.gov.cn/fnoticeAction!listFNoticeInfos_n.action";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance();
		String endTime=sdf.format(c.getTimeInMillis());
		String beginTime=sdf.format(c.getTimeInMillis()-1000*3600*24*7);//7天前
		Map<String,String> map=new HashMap<String,String>();
		map.put("rank","");
		map.put("queryInfo.curPage","1");
		map.put("queryInfo.pageSize","1000");//查全部
		map.put("queryInfo.TITLE","");
		map.put("queryInfo.FBRMC","");
		map.put("queryInfo.GGLX","招标公告");
		map.put("queryInfo.CGLX","");
		map.put("queryInfo.CGFS","");
		map.put("queryInfo.BEGINTIME1",beginTime);
		map.put("queryInfo.ENDTIME1",endTime);
		map.put("queryInfo.QYBM","420101");
		map.put("queryInfo.JHHH","");
		List<Announce> list=announceService.announceList(url, Method.POST, map);
		return list;
	}
	
	@RequestMapping("/showAll")
	public List<Announce> showAll(HttpServletRequest request) throws IOException, BaqiException{
		String url="http://www.ccgp-hubei.gov.cn/pages/html/xzbnotice.html";
		List<Announce> list=announceService.announceList(url, Method.GET);
		return list;
	}
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request) throws IOException{
		return "test";
	}

}
