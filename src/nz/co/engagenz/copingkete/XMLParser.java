package nz.co.engagenz.copingkete;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.Message;
import android.util.Log;
  
public class XMLParser { 
  
	
	
    // 构�?方法
    public XMLParser() { 
  
    }
  
    /** 
     * 从URL获取XML使HTTP请求
     * @param url string 
     * */
    public String getXmlFromUrl(String url) {
        String xml = null; 
  
        try { 
            // defaultHttpClient 
            DefaultHttpClient httpClient = new DefaultHttpClient(); 
            HttpPost httpPost = new HttpPost(url); 
  
            HttpResponse httpResponse = httpClient.execute(httpPost); 
            HttpEntity httpEntity = httpResponse.getEntity(); 
            xml = EntityUtils.toString(httpEntity); 
            
         // 生成�?��请求对象，参数就是地�?
			HttpGet httpGet = new HttpGet(url);
			// 生成Http客户�?
			HttpClient httpClientGet = new DefaultHttpClient();  
			// 发�?请求的响�?
			httpResponse = httpClientGet.execute(httpGet);
			// 代表接收的http消息，服务器返回的消息都在httpEntity
			httpEntity = httpResponse.getEntity();
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				 xml = EntityUtils.toString(httpEntity); 
			}
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace(); 
        } catch (ClientProtocolException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return xml; 
    } 
  
    /** 
     * 获取XML DOM元素
     * @param XML string 
     * */
    public Document getDomElement(String xml){ 
        Document doc = null; 
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
        try { 
  
            DocumentBuilder db = dbf.newDocumentBuilder(); 
  
            InputSource is = new InputSource(); 
                is.setCharacterStream(new StringReader(xml)); 
                doc = db.parse(is);  
  
            } catch (ParserConfigurationException e) { 
                Log.e("Error: ", e.getMessage()); 
                return null; 
            } catch (SAXException e) { 
                Log.e("Error: ", e.getMessage()); 
                return null; 
            } catch (IOException e) { 
                Log.e("Error: ", e.getMessage()); 
                return null; 
            } 
  
            return doc; 
    } 
  
    /** 获取节点�?
      * @param elem element 
      */
     public final String getElementValue( Node elem ) { 
         Node child; 
         if( elem != null){ 
             if (elem.hasChildNodes()){ 
                 for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){ 
                     if( child.getNodeType() == Node.TEXT_NODE  ){ 
                         return child.getNodeValue(); 
                     } 
                 } 
             } 
         } 
         return ""; 
     } 
  
     /** 
      * 获取节点�?
      * @param Element node 
      * @param key string 
      * */
     public String getValue(Element item, String str) { 
            NodeList n = item.getElementsByTagName(str); 
            return this.getElementValue(n.item(0)); 
        }
}