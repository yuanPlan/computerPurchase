package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import db.datautils;
import javabeen.Job;

public class Spider {
	static String url="http://sou.zhaopin.com/jobs/searchresult.ashx?jl=%E9%83%91%E5%B7%9E&kw=java&sm=0&p=3";
	static int i=0;
	public static void body1() throws IOException {
		Document doc = Jsoup.connect(url).get();
		Element element = doc.select("div#newlist_list_content_table").first();
		
		//System.out.println(element);
		
		 Elements tables =element.select("table");
		 HashMap<Integer, Job>hMap=new HashMap<Integer,Job>();
		 java.util.ListIterator<Element> listIterator = tables.listIterator(1);
		 while(listIterator.hasNext()) {
			 //System.out.println(listIterator.next());
			 
		   Element table=listIterator.next();
		   Element link = table.select("tr>td.zwmc").select("a").first();
		   Element link1 = table.select("tr>td.gsmc").select("a").first();  
           Element link2 = table.select("tr>td.zwyx").first();  
           Element link3 = table.select("tr>td.gzdd").first();  
           Element link4 = table.select("tr>td.gxsj").select("span").first();  
           Element link5 = table.select("tr>td.gxsj").select("span").first();  
           Job job =new Job();
           String url1=link.attr("href").toString();
           Document doc1 = Jsoup.connect(url1).get();
          // System.out.println(doc1);
           Element element1=doc1.select("ul.terminal-ul ").first();
           
          //System.out.println(element1+"===");
           
           link.text();
           i++;
           
           job.setPosition(link.text().toString());  //职位
           job.setCompany(link1.text().toString());  //公司
           job.setCompensation(link2.text().toString());  //薪资
           job.setWorkplace(link3.text().toString());  //工作地点
           job.setDate(link4.text().toString());  //发布日期
           
           
           System.out.println("***"+element1.child(4).select("strong").text());
           
           job.setEducation(element1.child(5).select("strong").text().toString());
           System.out.println(element1.child(5).select("strong").text().toString());
           job.setExperience(element1.child(4).select("strong").text().toString());
           job.setType(element1.child(7).select("strong").text().toString());
           job.setNumber(element1.child(6).select("strong").text().toString());
           
           job.setJobdescription(doc1.select("div.tab-inner-cont").first().text().toString());
           job.setComdescription(doc1.select("div.tab-inner-cont").first().nextElementSibling().text().toString());
           hMap.put(i, job);  
		 }
		 
		 Set<Integer> keys=hMap.keySet();
		 
		 for(Integer key:keys) {
			 Job value =hMap.get(key);
			 Connection conn =null;
			 try {
				 PreparedStatement ps =null;
				 conn = datautils.getConnection();  
	                String sql = "insert into job(position,company,compensation,workplace,date,education,experience,type,number,jobdescription,comdescription)values(?,?,?,?,?,?,?,?,?,?,?)";  
	                ps = conn.prepareStatement(sql);  
	                ps.setString(1, value.getPosition());  
	                ps.setString(2, value.getCompany());  
	                ps.setString(3, value.getCompensation());  
	                ps.setString(4, value.getWorkplace());  
	                ps.setString(5, value.getDate());  
	                ps.setString(6, value.getEducation());  
	                ps.setString(7, value.getExperience());  
	                ps.setString(8, value.getType());  
	                ps.setString(9, value.getNumber());  
	                ps.setString(10, value.getJobdescription());  
	                ps.setString(11, value.getComdescription());  
	                ps.executeUpdate();  
	                conn.close();  
	            } catch (SQLException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	                System.out.println("数据库访问失败");  
	            }  
	            System.out.println(key+","+value.toString());  
	        }  
		 }
	
	 public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {  
	        body1();  
	    }  
	}  

