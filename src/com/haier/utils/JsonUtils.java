package com.haier.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.haier.app.AppException;
import com.haier.bean.AccessFlag;
import com.haier.bean.Equipments;
import com.haier.bean.GameList;
import com.haier.bean.Knowledges;
import com.haier.bean.Music1;
import com.haier.bean.OptionList;
import com.haier.bean.Questions;
import com.haier.bean.RemindInfos;
import com.haier.bean.Reminds;
import com.haier.bean.Rules;
import com.haier.bean.Schedules;
import com.haier.bean.Sscion;
import com.haier.bean.User;
import com.haier.bean.UserInfo;
import com.haier.db.AccessFlagDB;
import com.haier.db.DBServer;
import com.haier.db.EquipmentDB;
import com.haier.db.GameListDB;
import com.haier.db.KnowledgeDB;
import com.haier.db.OptionListDB;
import com.haier.db.QuestionsDB;
import com.haier.db.RemindDB;
import com.haier.db.RemindfzDB;
import com.haier.db.RemindmeDB;
import com.haier.db.RemindnewsDB;
import com.haier.db.RemindzjDB;
import com.haier.db.RulesDB;
import com.haier.db.ScheduleDB;
import com.haier.db.UserDB;

/**
 * 
 * @author Administrator
 * 
 */
public class JsonUtils {
	public final static String PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static String na;
	public final static Gson gson = new GsonBuilder().setDateFormat(PATTERN).create();
	static String json = null;
	public static DBServer db;
	public static EquipmentDB equimentDB;
	public static KnowledgeDB knowledgeDB;
	public static GameListDB  gameListDB;
	public static UserDB userDB;
	public static ScheduleDB scheduleDB;
	public static AccessFlagDB accessFlagDB;
	public static QuestionsDB questionsDB;
	public static OptionListDB optionListDB;
	public static RulesDB rulesDB;
	public static RemindDB reminddb;
	static List<RemindInfos> remindnews=new ArrayList<RemindInfos>();
	static List<RemindInfos> remindzj=new ArrayList<RemindInfos>();
	static List<RemindInfos> remindfs=new ArrayList<RemindInfos>();
	static List<RemindInfos> remindme=new ArrayList<RemindInfos>();
	public static RemindfzDB remindfzDB;
	public static RemindmeDB remindmeDB;
	public static RemindnewsDB remindnewsDB;
	public static RemindzjDB remindzjDB;
	public static String toJson(Object src) throws JsonSyntaxException {
		return gson.toJson(src);
	}
	public static <T> T fromArrayJson(String json, Class<T> classOfT)
			throws JsonSyntaxException {
		return gson.fromJson(json.substring(1, json.length() - 1), classOfT);
	}
	public static <T> T fromJson(String json, Class<T> classOfT)
			throws JsonSyntaxException {
		return gson.fromJson(json, classOfT);
	}
	public static String fromJson(String result) throws JSONException {
		// TODO Auto-generated method stub
		// json = new String(result);
		String flag = null;
		JSONArray jsonArray = new JSONArray(result);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject sc = jsonArray.getJSONObject(i);
			JSONObject accessFlagArray = sc.getJSONObject("accessFlag");
			AccessFlag accessFlag = new AccessFlag();
			accessFlag.setFlag(accessFlagArray.getString("flag"));
			accessFlag.setDescribe(accessFlagArray.getString("describe"));
			flag = accessFlag.getFlag();
			accessFlagDB.addAccessFlag(accessFlag);
			if(flag.equals("6")||flag.equals("9")){
				JSONArray schedulesArray = sc.getJSONArray("schedules");
				for (int j = 0; j < schedulesArray.length(); j++) {
					JSONObject jsonObject2 = (JSONObject) schedulesArray.opt(j);
					Schedules schedule = new Schedules();
					schedule.setStatus(jsonObject2.getString("status"));
					schedule.setKnowledgeId(jsonObject2.getString("knowledgeId"));
					scheduleDB.addSchedule(schedule);
				}
				JSONArray knowledgeArray = sc.getJSONArray("knowledges");
				for (int j = 0; j < knowledgeArray.length(); j++) {
					JSONObject jsonObject3 = (JSONObject) knowledgeArray.opt(j);
					Knowledges knowledge = new Knowledges();
					knowledge.setId(jsonObject3.getString("id"));
					knowledge.setName(jsonObject3.getString("name"));
					knowledge.setParentId(jsonObject3.getString("parentId"));
					knowledgeDB.addKnowledge(knowledge);
				}
				JSONArray equipmentArray = sc.getJSONArray("equipments");
				for (int j = 0; j < equipmentArray.length(); j++) {
					JSONObject jsonObject4 = (JSONObject) equipmentArray.opt(j);
					Equipments equipment = new Equipments();
					equipment.setId(jsonObject4.getString("id"));
					equipment.setName(jsonObject4.getString("name"));
					equipment.setPath(jsonObject4.getString("path"));
					equimentDB.addEquipment(equipment);
				}
				JSONArray remindInfos = sc.getJSONArray("remindInfos");
				if (!remindInfos.toString().equals("[]")) {
					reminddb.addRemind(remindInfos.toString());
					Reminds reminds = null;
					try {
						reminds = Reminds.parse(reminddb.select());
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (int l = 0; l < reminds.getFileList().size(); l++) {
						if (reminds.getFileList().get(l).getType().equals("1")) {
							remindnews.add(reminds.getFileList().get(l));
						}
						if (reminds.getFileList().get(l).getType().equals("2")) {
							remindzj.add(reminds.getFileList().get(l));
						}
						if (reminds.getFileList().get(l).getType().equals("3")) {
							remindfs.add(reminds.getFileList().get(l));
						}
						if (reminds.getFileList().get(l).getType().equals("4")) {
							remindme.add(reminds.getFileList().get(l));
						}
					}
					if (!JsonUtils.toJson(remindnews).toString().equals("[]")) {
						remindfzDB.addRemind(JsonUtils.toJson(remindnews));
					}
					if (!JsonUtils.toJson(remindme).toString().equals("[]")) {
						remindmeDB.addRemind(JsonUtils.toJson(remindme));
					}
					if (!JsonUtils.toJson(remindfs).toString().equals("[]")) {
						remindnewsDB.addRemind(JsonUtils.toJson(remindfs));
					}
					if (!JsonUtils.toJson(remindzj).toString().equals("[]")) {
						remindzjDB.addRemind(JsonUtils.toJson(remindzj));
					}
				}
				JSONObject userInfoArray = sc.getJSONObject("userInfo");
				UserInfo user = new UserInfo();
				user.setId(userInfoArray.getInt("id"));
				user.setSessionId(sc.getString("sessionId"));
				user.setExpertFlag(userInfoArray.getString("expertFlag"));
				user.setUserName(userInfoArray.getString("userName"));
				Sscion.setSsid(sc.getString("sessionId"));
				String flog=sc.getString("roleFlag");
				Music1.setRoleflag(flog);
				User.setName(userInfoArray.getString("name"));
				user.setName(userInfoArray.getString("name"));
				user.setTotalScore(userInfoArray.getString("totalScore"));
				userDB.addUser(user);
			}else{
			}
		}
		
		return flag;
	}
	public static String nameString;
	public static String describee;
	public static String allString;
	public static String titleString=null;
	public static String rrString=null;
	public static String fjson2(String string) throws JSONException{
		// TODO Auto-generated method stub
		String flag=null;
		JSONArray jsonArray = new JSONArray(string);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject sc = jsonArray.getJSONObject(i);
			JSONArray users = sc.getJSONArray("askAllInfos");
			for (int j = 0; j < users.length(); j++) {
				 JSONObject jsonObject3 = (JSONObject) users.opt(j);
				JSONObject users1 = jsonObject3.getJSONObject("userInfo");
				nameString=users1.getString("userName");
				titleString = jsonObject3.getString("title");
				rrString="@"+nameString+":"+titleString+".";
				allString=allString+rrString;
				}
	      }
		return allString;
}
public static String describe;
	public static String fjson(String string) throws JSONException{
		// TODO Auto-generated method stub
		String flag=null;
		JSONArray jsonArray = new JSONArray(string);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject sc = jsonArray.getJSONObject(i);
			JSONArray gameListArray = sc.getJSONArray("gameList");
			for (int j = 0; j < gameListArray.length(); j++) {
				JSONObject jsonObject3 = (JSONObject) gameListArray.opt(j);
				GameList gameList = new GameList();
				gameList.setId(jsonObject3.getString("id"));
				gameList.setPath(jsonObject3.getString("path"));
				gameList.setSessionId(sc.getString("sessionId"));
				gameListDB.addGameList(gameList);
				}
			Sscion.setSsid(sc.getString("sessionId"));
			JSONObject accessFlagArray = sc.getJSONObject("accessFlag");
			AccessFlag accessFlag = new AccessFlag();
			accessFlag.setFlag(accessFlagArray.getString("flag"));
			accessFlag.setDescribe(accessFlagArray.getString("describe"));
			describe=accessFlag.getDescribe();
			flag = accessFlag.getFlag();
			accessFlagDB.addAccessFlag(accessFlag);
	}
		return flag;
}
	public static String Fjson(String strResult) throws JSONException{
		// TODO Auto-generated method stub
		String flag=null;
		JSONArray jsonArray = new JSONArray(strResult);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject sc = jsonArray.getJSONObject(i);
			JSONArray questionArray = sc.getJSONArray("questions");
			for (int j = 0; j < questionArray.length(); j++) {
				JSONObject jsonObject3 = (JSONObject) questionArray.opt(j);
				Questions question = new Questions();
				question.setId(jsonObject3.getInt("id"));
				question.setAnswer(jsonObject3.getInt("answer"));
				question.setTitle(jsonObject3.getString("title"));
				question.setOptionList(jsonObject3.getString("optionList"));
				questionsDB.addQuestions(question);
				JSONArray optionArray=jsonObject3.getJSONArray("optionList");
				for(int k = 0; k < optionArray.length(); k++){
					JSONObject jsonObject4 = (JSONObject) optionArray.opt(k);
					OptionList optionList = new OptionList();
					optionList.setId(jsonObject4.getInt("id"));
					optionList.setOrderIndex(jsonObject4.getString("orderIndex"));
					optionList.setQcontext(jsonObject4.getString("qcontext"));
					optionListDB.addOptionList(optionList);
				  }
				}
			JSONArray rulesArray = sc.getJSONArray("rules");
			for (int j = 0; j < questionArray.length(); j++) {
				JSONObject jsonObject5 = (JSONObject) rulesArray.opt(j);
				Rules relus = new Rules();
				relus.setSeconds(jsonObject5.getString("seconds"));
				relus.setScore(jsonObject5.getString("score"));
				rulesDB.addRules(relus);
				}
			JSONObject accessFlagArray = sc.getJSONObject("accessFlag");
			AccessFlag accessFlag = new AccessFlag();
			accessFlag.setFlag(accessFlagArray.getString("flag"));
			accessFlag.setDescribe(accessFlagArray.getString("describe"));
			describe=accessFlag.getDescribe();
			flag = accessFlag.getFlag();
			accessFlagDB.addAccessFlag(accessFlag);
		}
		return flag;
	}
	public static String flagJson(String string) throws JSONException{
		String flag=null;
		JSONArray jsonArray = new JSONArray(string);
		JSONObject accessFlagArray = jsonArray.getJSONObject(0).getJSONObject("accessFlag");
		flag=accessFlagArray.getString("flag");
		describe=accessFlagArray.getString("describe");
		return flag;
	}
	/**
	 * 总页数
	 */
	public static int totalpage;
	public static int jifen;
	public static String loadTime=null;
	
	public static String newsJson(String string) throws JSONException{
		String json=null;
		JSONArray jsonArray = new JSONArray(string);
		json=jsonArray.getJSONObject(0).getString("newInfos");
		totalpage=jsonArray.getJSONObject(0).getInt("totalpage");
		return json;
	}
	public static String rankJson(String string) throws JSONException{
		String json=null;
		JSONArray jsonArray = new JSONArray(string);
		json=jsonArray.getJSONObject(0).getString("userRankInfos");
		return json;
	}
	public static String gloryJson(String string ) throws JSONException{
		String json=null;
		JSONArray jsonArray = new JSONArray(string);
		json=jsonArray.getJSONObject(0).getString("userRankInfos");
		return json;
	}
	public static String intreactJson(String string ) throws JSONException{
		String json=null;
		JSONArray jsonArray = new JSONArray(string);
		json=jsonArray.getJSONObject(0).getString("askAllInfos");
		return json;
	}
	public static String registerJson(String string) throws JSONException{
		String jsonString=null;
		JSONArray jsonArray=new JSONArray(string);
		jsonString=jsonArray.getJSONObject(0).getString("registerQuestions");
		return jsonString;
	}
public static String userJson(String string) throws JSONException{
		String jsonString=null;
		JSONArray jsonArray=new JSONArray(string);
		jsonString=jsonArray.getJSONObject(0).getString("userInfos");
		return jsonString;
}
	public static String eqJson(String string) throws JSONException{
		String jsonString=null;
		JSONArray jsonArray=new JSONArray(string);
		jsonString=jsonArray.getJSONObject(0).getString("equipments");
		return jsonString;
	}	public static void signJson(String string) throws JSONException {
		String json=null;
		JSONArray jsonArray = new JSONArray(string);
		loadTime=jsonArray.getJSONObject(0).getString("loadTimes");
		jifen=jsonArray.getJSONObject(0).getInt("jifen");
	}
	public static String ruleJson(String strResult) throws JSONException {
		// TODO Auto-generated method stub
		String json=null;
		JSONArray jsonArray = new JSONArray(strResult);
		json=jsonArray.getJSONObject(0).getString("rule");
		return json;
	}

	public static String userinfoJson(String strResult) throws JSONException {
		// TODO Auto-generated method stub
		String json=null;
		JSONArray jsonArray = new JSONArray(strResult);
		json=jsonArray.getJSONObject(0).getString("userInfos");
		return json;
	}

	public static String pwJson(String strResult) throws JSONException {
		// TODO Auto-generated method stub
		String json=null;
		JSONArray jsonArray = new JSONArray(strResult);
		json=jsonArray.getJSONObject(0).getString("registerQuestions");
		return json;
	}
	}
