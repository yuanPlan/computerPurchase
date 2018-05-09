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
import javabeen.ComputersInfo;
import javabeen.Job;

public class Spider2 {
	
	static HashMap<Integer, ComputersInfo>hMap=new HashMap<Integer,ComputersInfo>();
	static int page=1;
	static int i=0;
	
	public static void tmall(String url) throws IOException {
		//int i=0;
		Document doc = Jsoup.connect(url).timeout(30000).get();
		Element element = doc.select("div#J_ItemList").first();
				
		 Elements products =element.select("div.product");
		
		 java.util.ListIterator<Element> listIterator = products.listIterator(0);
		 while(listIterator.hasNext()) {
			 //System.out.println(listIterator.next());
			 System.out.println("i="+i);
		   Element product=listIterator.next();
		   Element link = product.select("div.productImg-wrap").select("a").first();
		   Element link1 = product.select("p.productPrice").first();  
		   Element link2 = product.select("div.productTitle").first();  
		   Element link3 = product.select("div.productShop").select("a").first();  
		   Element link4 = product.select("p.productStatus").select("span").first();  
		  // System.out.println("img="+link2);
		  //System.out.println("title="+link2);
		   if((Element)link2==null||(Element)link2.getElementsByClass("prop").first()==null)
        	   continue;
           ComputersInfo computer=new ComputersInfo();
           computer.setDntp("https:"+product.children().select("img").attr("src"));  //图片
           computer.setDndz("https:"+link.attr("href"));  //地址
           computer.setDnjg(link1.select("em").attr("title"));//价格
           computer.setDnmz(link2.select("a").first().text());//名字
           
          
           String dnjj=link2.select("a.prop").first().attr("title").toString()+","+link2.select("a.prop").first().nextElementSibling().attr("title").toString()+","+link2.select("a.prop").first().lastElementSibling().attr("title").toString();
          
          
           computer.setDnjj(dnjj);
           computer.setDnly(link3.text());//来源
           computer.setDnxl(link4.text());//销量
           String[] sp=computer.getDnmz().split(" ");
           computer.setDnpp(sp[0]);//品牌
           i++;
           hMap.put(i, computer);  
           
		 }
		 page++;
		 //爬取多页数据
		 String url2="https://list.tmall.com/search_product.htm"+doc.select("p.ui-page-s").select("a").attr("href");
		 System.out.println(url2);
		 System.out.println(page);
		 if(page<20) {
			 tmall(url2);
		 }
		 else {
			 db() ;
		 }
	}
	
	public static void jd(String url) throws IOException {
		Document doc = Jsoup.connect(url).timeout(30000).get();
		Elements items = doc.select("li.gl-item");
		//System.out.println(items);
		java.util.ListIterator<Element> listIterator = items.listIterator(0);
		 while(listIterator.hasNext()) {
			// System.out.println(listIterator.next());
			 System.out.println("i="+i);
			
		   Element product=listIterator.next();
		   String img ="https:"+product.select("div.p-img").select("a").select("img").attr("src").toString();
		   String proUrl=product.select("div.p-img").select("a").attr("href").toString();
		   if(!proUrl.contains("https:"))
			   proUrl="https:"+proUrl;
		   String price=product.select("div.p-price").select("i").text().toString();
		   String name=product.select("div.p-name").select("em").text().toString();
		   System.out.println("name:"+name);
		   
		   String[] sp=name.split("[\\( \\（    \\s]");
		   String brand=sp[0];
		   //System.out.println(product.select("div.p-shop").select("a.curr-shop").attr("title"));
		   String shop=product.select("div.p-shop").select("a.curr-shop").attr("title").toString();
		   System.out.println("shop:"+shop);
		   if(shop==null)
			   continue;
		  // System.out.println(proUrl);
		   String commment=product.select("div.p-commit").select("strong").text();
		   Document doc1 = Jsoup.connect(proUrl).timeout(30000).get();
		   String parameter=doc1.select("div.Ptable").text();
		   ComputersInfo computer=new ComputersInfo();
		   computer.setDndz(proUrl);
		   computer.setDnjg(price);
		   computer.setDnjj(parameter);
		   computer.setDnly(shop);
		   computer.setDnmz(name);
		   computer.setDnpp(brand);
		   computer.setDntp(img);
		   computer.setDnxl(commment);
		   
		   i++;
		  // System.out.println(computer.toString());
		   hMap.put(i, computer);  
		 }
		page+=2;
		 //爬取多页数据
		 
		 //System.out.println(page+1);
		 String url2="https://search.jd.com/Search?keyword=%E7%94%B5%E8%84%91&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E7%94%B5%E8%84%91&page="+page;
		 System.out.println(url2);
		 System.out.println(page-1);
		 if(page<3) {
			 jd(url2);
		 }
		 else {
			 db() ;
		 }
	}
	public static void db() throws IOException {
		 Set<Integer> keys=hMap.keySet();
		 for(Integer key:keys) {
			 ComputersInfo value =hMap.get(key);
			 Connection conn =null;
			 try {
				 PreparedStatement ps =null;
				 conn = datautils.getConnection();  
	                String sql = "insert into computerinfo(DNPP,DNJJ,DNTP,DNJG,DNLY,DNXL,DNDZ,DNMC)values(?,?,?,?,?,?,?,?)";  
	                ps = conn.prepareStatement(sql);  
	                ps.setString(1, value.getDnpp());  
	                ps.setString(2, value.getDnjj());  
	                ps.setString(3, value.getDntp());  
	                ps.setString(4, value.getDnjg());  
	                ps.setString(5, value.getDnly());  
	                ps.setString(6, value.getDnxl());  
	                ps.setString(7, value.getDndz());  
	                ps.setString(8, value.getDnmz());  
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
		 //String url1="https://list.tmall.com/search_product.htm?q=%B5%E7%C4%D4&type=p&vmarket=&spm=875.7931836%2FB.a2227oh.d100&from=mallfp..pc_1_searchbutton";  
		 //tmall(url1);  
		 String url2="https://search.jd.com/Search?keyword=%E7%94%B5%E8%84%91&enc=utf-8&wq=%E7%94%B5%E8%84%91&pvid=57428c3cb4ec4d50b216cff7e740086b";
		 jd(url2);
	    }  
	}  

