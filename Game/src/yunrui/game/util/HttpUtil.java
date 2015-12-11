package yunrui.game.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import yunrui.game.Test;
import yunrui.game.been.Getquestion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.haier.bean.Sscion;
import com.haier.bean.URLs;


public class HttpUtil {

	public static String httpget(){
		String result=null;
		   String query="&questions=";
		   for (int i = 0; i < JsonUtil.qList.size(); i++) {
			query+=Getquestion.questidList.get(i)+","+
					Getquestion.questtimeList.get(i)+","+
					Getquestion.questTFList.get(i)+","+
					Getquestion.mfList.get(i)+","+
					Getquestion.questuserList.get(i)+";";
		}
//		   Test.kId  Test.ssesionid
	   		String url=Test.url+"knowledgeId="+Test.kId+"&sessionId="+Test.ssesionid+query;
	       	HttpGet httpRequest = new HttpGet(url); 
	        String strResult = ""; 
	        try { 
	            // HttpClient对象 
	            HttpClient httpClient = new DefaultHttpClient(); 
	            // 获得HttpResponse对象 
	            HttpResponse httpResponse = httpClient.execute(httpRequest); 
	            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { 
	                // 取得返回的数据 
	                strResult = EntityUtils.toString(httpResponse.getEntity()); 
	            }  
	       result=JsonUtil.fromJson(strResult);
	       if (result.equals("6")) {
				JsonUtil.fJson(strResult);
			}
	        } catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	       
	   		return result;
	}
	public static String getQuset() {
		// TODO Auto-generated method stub
		HttpGet httpRequest = new HttpGet("http://58.56.163.30:8080/gamelearning/clientGame/getQuestions.action?"+"knowledgeId="+Test.kId+"&sessionId="+Test.ssesionid); 

        String result = "6"; 
//
//        try { 
//
//            // HttpClient���� 
//
//            HttpClient httpClient = new DefaultHttpClient(); 
//
//            // ���HttpResponse���� 
//
//            HttpResponse httpResponse = httpClient.execute(httpRequest); 
//
//            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { 
//
//
//                strResult = EntityUtils.toString(httpResponse.getEntity()); 
//
//            }  
//            result=JsonUtil.fromJson(strResult);
//            if (result.equals("6")) {
//            	result=JsonUtil.Fjson(strResult);
//			}
//        } catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		return result;
		}
	public static Bitmap returnBitMap(String url) {
		// TODO Auto-generated method stub
		 URL myFileUrl = null;  
			Bitmap bitmap = null;  
			try {  
			myFileUrl = new URL(url);  
			} catch (MalformedURLException e) {  
			e.printStackTrace();  
			}  
			try {  
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();  
			conn.setDoInput(true);  
			conn.connect();  
			InputStream is = conn.getInputStream();  
			bitmap = BitmapFactory.decodeStream(is);  
			is.close();  
			} catch (IOException e) {  
			e.printStackTrace();  
			}  
			return bitmap;
		}		
}
