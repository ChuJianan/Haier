package com.haier.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Intent;
import android.sax.StartElementListener;
import android.text.LoginFilter.UsernameFilterGeneric;

import com.haier.activity.LoginActivity;
import com.haier.activity.MainActivity;
import com.haier.activity.WeiFeiends;
import com.haier.activity.WeiGameActivity;
import com.haier.activity.fragment.EquipmentFragment;
import com.haier.activity.fragment.RankingFragment;
import com.haier.bean.URLs;
import com.haier.utils.JsonUtils;
import com.haier.utils.UIHelper;

/**
 * httpget
 * 
 * @author Administrator
 * 
 */
public class AppClient {
	static String usernameString = null;
	static String answerString = null;

	public static String httpget(String sessionId, int pagNo, int type) {
		String flag = null;
		String query = "sessionId=" + sessionId + "&" + "pageno=" + pagNo + "&"
				+ "type=" + type;
		HttpGet httpRequest = new HttpGet(URLs.NEWS_URL + query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag != null && flag.equals("6")) {
				flag = JsonUtils.newsJson(strResult);
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
		return flag;
	}

	public static String sign(String sscion) {
		String flag = null;
		String query = "sessionId=" + sscion;
		HttpGet httpRequest = new HttpGet(URLs.SIGN_URL + query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			
			flag = JsonUtils.flagJson(strResult);
			if (flag != null && flag.equals("6")) {
				JsonUtils.signJson(strResult);
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
		return flag;
	}
	public static String sign2(String sscion) {
		String flag = null;
		String query = "sessionId=" + sscion;
		HttpGet httpRequest = new HttpGet(URLs.SIGN_URL + query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			
			flag = JsonUtils.flagJson(strResult);
			if (flag != null) {
				JsonUtils.signJson(strResult);
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
		return flag;
	}
	
	public static String httpget(String sessionId, int pagNo) {
		String flag = null;
		String query = "sessionId=" + sessionId + "&" + "areaFlag=" + pagNo;
		HttpGet httpRequest = new HttpGet(URLs.GLORY_URL + query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag != null && flag.equals("6")) {
				flag = JsonUtils.gloryJson(strResult);
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
		return flag;
	}
	public static String httpgetpw() {
		String flag = null;
		HttpGet httpRequest = new HttpGet(URLs.REGISTER_URL);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag != null && flag.equals("6")) {
				flag = JsonUtils.pwJson(strResult);
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
		return flag;
	}

	public static String httpget(String sessionId) {
		String flag = null;
		String query = URLs.REGISTER_URL + "sessionId=" + sessionId;
		HttpGet httpRequest = new HttpGet(query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag != null && flag.equals("6")) {
				flag = JsonUtils.registerJson(strResult);
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
		return flag;
	}

	public static String httpget(String sessionId, String newpwd,
			String newpwdagn, long rgsqId, String anwser) {
		String flag = null;
		String query = URLs.GETQS_URL + "sessionId=" + sessionId
				+ "&newPassword=" + newpwd + "&newPasswordAgain=" + newpwdagn
				+ "&registerQuestionId=" + rgsqId + "&answer=" + anwser;
		HttpGet httpRequest = new HttpGet(query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
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
		return flag;
	}

	public static String httpgetAsk(String url) {
		String flag = null;
		HttpGet httpRequest = new HttpGet(url);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag != null && flag.equals("6")) {
				flag = JsonUtils.intreactJson(strResult);
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
		return flag;
	}

	public static String httpgetNews(String sessionId, String pagNo) {
		String flag = null;
		String query = "sessionId=" + sessionId + "&" + "newsId=" + pagNo;
		HttpGet httpRequest = new HttpGet(URLs.NEWSCONTENT_URL + query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag != null && flag.equals("6")) {
				flag = JsonUtils.newsJson(strResult);
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
		return flag;
	}

	public static String httpgetFS(String url) {
		String flag = null;
		HttpGet httpRequest = new HttpGet(url);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
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
		return flag;
	}

	public static String httpgetFSList(String url) {
		String flag = null;
		HttpGet httpRequest = new HttpGet(url);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag.equals("6")) {
				flag = JsonUtils.userJson(strResult);
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
		return flag;
	}

	public static String httpgetRank(String sessionId, int pageNo, int type) {
		// TODO Auto-generated method stub
		String flag = null;
		String query = "sessionId=" + sessionId;
		HttpGet httpRequest = new HttpGet(URLs.RANK_URL + query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag!=null) {
				if ( flag.equals("6")) {
					flag = JsonUtils.rankJson(strResult);
				}else if (flag.equals("1")) {
					WeiFeiends.friendsActivity.finish();
					UIHelper.ToastMessage(MainActivity.mainActivity, "用户已在其他客户端登陆！");
					MainActivity.mainActivity.finish();
				}
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
		return flag;
	}
	public static String httpgetFriend(String sessionId, String searchStr) {
		// TODO Auto-generated method stub
		String flag = null;
		String query = "sessionId=" + sessionId + "&searchStr=" + searchStr;
		HttpGet httpRequest = new HttpGet(URLs.FRIEND_URL + query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag!=null) {
				if ( flag.equals("6")) {
					flag = JsonUtils.userinfoJson(strResult);
				}
				if (flag.equals(1)) {
					WeiFeiends.friendsActivity.finish();
					UIHelper.ToastMessage(MainActivity.mainActivity, "用户已在其他客户端登陆！");
					MainActivity.mainActivity.finish();
				}
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
		return flag;
	}

	public static String httpgetaddFriend(String sessionId, String searchStr) {
		// TODO Auto-generated method stub
		String flag = null;
		String query = "sessionId=" + sessionId + "&firendId=" + searchStr;
		HttpGet httpRequest = new HttpGet(URLs.ADDF_URL + query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
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
		return flag;
	}

	public static String httpgeteq(String sessionId) {
		String flag = null;
		String query = URLs.EQUIP_URL + "sessionId=" + sessionId;
		HttpGet httpRequest = new HttpGet(query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
			if (flag != null) {
				if (flag.equals("6")) {
					flag = JsonUtils.eqJson(strResult);
				}else if (flag.equals("1")) {
					WeiGameActivity.weiGameActivity.finish();
					UIHelper.ToastMessage(MainActivity.mainActivity, "用户已在其他客户端登陆！");
					MainActivity.mainActivity.finish();
				}
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
		return flag;
	}

	public static String httptest(String string, long knowledgeId,
			String string2) {
		// TODO Auto-generated method stub
		String flag = null;
		try {
			usernameString = URLEncoder.encode(string, "utf-8");
			answerString = URLEncoder.encode(string2, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String query = URLs.PWSS_TEST + "&userName=" + usernameString
				+ "&registerQuestionId=" + knowledgeId + "&answer="
				+ answerString;
		HttpGet httpRequest = new HttpGet(query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
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
		return flag;
	}

	public static String httptopw(String userName, String string, String string2) {
		// TODO Auto-generated method stub
		String flag = null;
		String query = URLs.GET_STRING + "&userName=" + userName
				+ "&newPassword=" + string + "&newPasswordAgain=" + string2;
		HttpGet httpRequest = new HttpGet(query);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			flag = JsonUtils.flagJson(strResult);
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
		return flag;
	}
}
