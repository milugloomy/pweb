package com.baqi.pweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.baqi.pweb.bean.Announce;
import com.baqi.pweb.common.BaqiException;

@Service
public class AnnounceService{
	
	public List<Announce> announceList(String url,Method method) throws BaqiException{
		return announceList(url, method,new HashMap<String,String>());
	}
	public List<Announce> announceList(String url,Method method,Map<String,String> map) throws BaqiException{
		List<Announce> announceList=new ArrayList<Announce>();
		try {
			Response res = Jsoup.connect(url)
					.timeout(30*1000)//30秒
					.method(Method.POST)
					.data(map)
					.execute();
			Document doc=Jsoup.parse(res.body());
			Element newsContent=doc.getElementsByClass("news_content").get(0);
			Elements lis=newsContent.getElementsByTag("li");
			for(Element li:lis){
				Announce announce=new Announce();
				Element a=li.getElementsByTag("a").get(0);

				String href=a.attr("href").replace("../", "");
				href="http://www.ccgp-hubei.gov.cn/"+href;
				announce.setHref(href);
				announce.setName(a.text());

				String time=li.select("font[color=\"#000000\"]").get(0).text();
				time=time.replace("[发布时间 ", "").replace(" ]", "");
				announce.setTime(time);
				
				String type=li.select("font[color=\"blue\"]").get(0).text();
				announce.setType(type);
				
				announceList.add(announce);
			}
		} catch (IOException e) {
			throw new BaqiException(e.getMessage());
		}
		
		return announceList;
	}

}
