package com.arvanitis.stockstalker; 
  
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.net.URL; 
import java.util.ArrayList; 
import java.util.List; 
  




import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
  




import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document; 
import org.w3c.dom.Element; 
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 
import org.xml.sax.InputSource; 

import com.arvanitis.stockstalker.JSONfuntions;
  
public class NamesParser { 
  
    Item objItem, objItem2; 
    List<Item> listArray; 
    JSONArray json_result = null;
  
    public List<Item> getData(String url,String id) { 
  
        try { 
  
            listArray = new ArrayList<Item>(); 
            //objItem = new Item();
            
            JSONObject json_data = JSONfuntions.getJSONfromURL(url);
            
            try
			{
				JSONObject json_query = json_data.getJSONObject("query");
				JSONObject json_results = json_query.getJSONObject("results");
				json_result = json_results.getJSONArray("quote");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
            
            if (json_result != null)
				for (int i = 0; i < json_result.length(); i++) 
				{
					//HashMap<String, String> map = new HashMap<String, String>();
					JSONObject vo = json_result.getJSONObject(i);
					objItem = new Item();
					//JSONObject vo = c.getJSONObject("volumeInfo");
					//map.put("title", vo.optString("title"));
					//map.put("description", vo.optString("description"));
					//JSONObject il = vo.getJSONObject("imageLinks");
					//map.put("thumbnail", il.optString("thumbnail"));
					//arraylist.add(map);
					//if (vo.optString("symbol").equals("^GDAXI") || vo.optString("symbol").equals("CAC") || vo.optString("symbol").equals("DIA") || vo.optString("symbol").equals("^IXIC") || vo.optString("symbol").equals("EURUSD=X") || vo.optString("symbol").equals("^FTSE"))
					//{
						System.out.print("\rSymbol: " + vo.optString("Name") + ", Price: " + vo.optString("LastTradePriceOnly") + ", Ask Price: " + vo.optString("AskRealtime") + ", Bid Price: " + vo.optString("BidRealtime") + ", Change: " + vo.optString("ChangeinPercent") + ", Last Trade: " + vo.optString("LastTradeDate") + " " + vo.optString("LastTradeTime"));
						//System.out.println("\r");
						//System.out.flush();
					//}
						
						objItem.setId(""+i+"");
						objItem.setTitle(vo.optString("Name"));
						objItem.setAddress(vo.optString("LastTradePriceOnly"));
						
						listArray.add(objItem);

				}
			System.out.println("\r");
			try 
			{
				Thread.sleep(100);
			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              
         //   objItem = new Item(); 
         //   objItem.setId("123"); 
         //   objItem.setTitle("perigrafi"); 
         //   objItem.setDesc("typos"); 
         //   objItem.setAddress("dieuthinsi"); 
         //   objItem.setPubdate("id_user"); 
         //   objItem.setLink("link"); 
              
         //   listArray.add(objItem); 
              
        //    objItem2 = new Item(); 
        //    objItem2.setId("1234"); 
        //    objItem2.setTitle("perigrafi2"); 
        //    objItem2.setDesc("typos2"); 
        //    objItem2.setAddress("dieuthinsi2"); 
        //    objItem2.setPubdate("id_user2"); 
        //    objItem2.setLink("link2"); 
              
        //    listArray.add(objItem2); 
              
/*          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
            DocumentBuilder db = dbf.newDocumentBuilder(); 
            URL xml = new URL(url); 
            InputStream inputStream = xml.openStream(); 
            InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8"); 
  
            InputSource is = new InputSource(reader); 
            //is.setEncoding("UTF-8"); 
            Document doc = db.parse(is); 
  
            doc.getDocumentElement().normalize(); 
  
            NodeList nList = doc.getElementsByTagName("data"); 
  
            for (int temp = 0; temp < nList.getLength(); temp++) { 
  
                Node nNode = nList.item(temp); 
                if (nNode.getNodeType() == Node.ELEMENT_NODE) { 
  
                    Element eElement = (Element) nNode; 
  
                    //if(getTagValue("id_user",eElement).equals(id)){ 
                    objItem = new Item(); 
  
                    objItem.setId(getTagValue("id", eElement)); 
                    objItem.setTitle(getTagValue("perigrafi", eElement)); 
                    objItem.setDesc(getTagValue("typos", eElement)); 
                    objItem.setAddress(getTagValue("dieuthinsi",eElement)); 
                    objItem.setPubdate(getTagValue("id_user", eElement)); 
                    objItem.setLink(getTagValue("link", eElement)); 
  
                    if(objItem.getPubdate().equals(id)) 
                    listArray.add(objItem);//} 
                } 
            } 
*/
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
  
        return listArray; 
    } 
  
    private static String getTagValue(String sTag, Element eElement) { 
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0) 
                .getChildNodes(); 
  
        Node nValue = (Node) nlList.item(0); 
  
        return nValue.getNodeValue(); 
  
    } 
      
} 