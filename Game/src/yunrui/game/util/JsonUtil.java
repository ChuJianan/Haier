package yunrui.game.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yunrui.game.Test;
import yunrui.game.been.Oplist;
import yunrui.game.been.Question;

import com.haier.bean.Equipments;
import com.haier.bean.OptionList;
import com.haier.bean.Questions;
import com.haier.bean.Schedules;
import com.haier.bean.UserInfo;

public class JsonUtil {

	public static List<Question> qList=new ArrayList<Question>();
//	public static List<Oplist>  oplists=new ArrayList<Oplist>();
	public static List<Oplist> fromjson(String string) throws JSONException{
			List<Oplist>  oplists=new ArrayList<Oplist>();
			JSONArray jsonArray2=new JSONArray(string);
			for (int j = 0; j < jsonArray2.length(); j++) {
				JSONObject jsonObject2=(JSONObject) jsonArray2.opt(j);
				Oplist oplist=new Oplist();
				oplist.setId(jsonObject2.getInt("id"));
				oplist.setOrderIndex(jsonObject2.getInt("orderIndex"));
				oplist.setQcontext(jsonObject2.getString("qcontext"));
				oplists.add(oplist);
			}			
		return oplists;
	}
	public static List<Schedules> scheduleslList=new ArrayList<Schedules>();//学习进度
	public static Equipments equipments=new Equipments();//装备
	public static UserInfo userInfo=new UserInfo();//用户
	public static int pass;//过关提示
	public static int updateFlag;//装备更新标示
	public static String describe;
	public static String fromJson(String string) throws JSONException{
		String flag=null;
		JSONArray jsonArray=new JSONArray(string);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			JSONObject accessFlag =jsonObject.getJSONObject ("accessFlag");
				flag=accessFlag.getString("flag");
				describe=accessFlag.getString("describe");
		}
		return flag;
	}
	
	public static String fJson(String string) throws JSONException{
		JSONArray jsonArray=new JSONArray(string);
		for (int i = 0; i < jsonArray.length(); i++) {
		JSONObject jsonObject=jsonArray.getJSONObject(i);
		JSONObject nextjsObject=jsonObject.getJSONObject("next");
		Test.kId=nextjsObject.getString("id");
		JSONArray scheduleInfos=jsonObject.getJSONArray("scheduleInfos");
		for (int j = 0; j < scheduleInfos.length(); j++) {
			JSONObject schedule=scheduleInfos.getJSONObject(j);
			Schedules schedules=new Schedules();
			schedules.setKnowledgeId(schedule.getString("knowledgeId"));
			schedules.setStatus(schedule.getString("status"));
			scheduleslList.add(schedules);
		}
		JSONObject equipment = jsonObject.getJSONObject("equipment");
		Equipments equipments= new Equipments();
		equipments.setId(equipment.getString("id"));
		equipments.setName(equipment.getString("name"));
		equipments.setPath(equipment.getString("path"));
		JsonUtil.equipments=equipments;
		userInfo.setTotalScore(jsonObject.getString("totalScore"));
		pass=jsonObject.getInt("pass");
		updateFlag=jsonObject.getInt("updateFlag");
		}
		
		return string;
	}
	public static String Fjson(String strResult) throws JSONException{
		// TODO Auto-generated method stub
		/*qList.clear();
		JSONArray jsonArray = new JSONArray(strResult);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject sc = jsonArray.getJSONObject(i);
			JSONArray questionArray = sc.getJSONArray("questions");
			for (int j = 0; j < questionArray.length(); j++) {
				JSONObject jsonObject3 = (JSONObject) questionArray.opt(j);
				Question question = new Question();
				question.setId(jsonObject3.getInt("id"));
				question.setAnswer(jsonObject3.getInt("answer"));
				question.setTitle(jsonObject3.getString("title"));
				JSONArray optionArray=jsonObject3.getJSONArray("optionList");
				for(int k = 0; k < optionArray.length(); k++){
					JSONObject jsonObject4 = (JSONObject) optionArray.opt(k);
					fromjson(jsonObject4.toString());
				  }
				qList.add(question);
				}
			}*/
		
		return String.valueOf(qList.size());
		}
}
