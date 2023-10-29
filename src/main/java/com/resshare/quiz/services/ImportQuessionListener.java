package com.resshare.quiz.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.api.client.json.Json;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.resshare.book.RefFireBaseBook;
import com.resshare.common.CommandConstant;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.reflectproxy.ProxyDemo;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.FireBaseReference;
import com.resshare.framework.core.service.IRequest;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.model.Input;

import model.UtilBook;

public class ImportQuessionListener extends QuizBaseListener {

//	@Override
//	public String getType() {
//		// TODO Auto-generated method stub
//		return "informatics";
//	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference("../pick_file_cfg_content_file");
		// FirebaseRefCovid19.draft_covid19 + "/load_form_script/" + getType();
	}

//	@Override
//	public String getReferenceNamePostData() {
//		// "../draft/covid19/create_volunteers_group/post_data";
//		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.requets_informatics);
//		// "../requets/searching");
//	}

	// @Override
	// public Script getScript() {
	// LoadFormSupplierOxyUI loadFormSupplierOxyUI = new LoadFormSupplierOxyUI();
	// return loadFormSupplierOxyUI.getUIBuilder().getScript();
	//
	// }
//	public String getScriptName() {
//		return null;
//		// TODO Auto-generated method stub
//	//	return LoadFormSearchingUI.class.getName();
//	}

	@Override
	public void onChildAdded(DataSnapshot   snapshotRoot, String previousChildName) {
		try {
			if (snapshotRoot.child("processing").getValue() == null) {
				// user_id
				// { key = -N7sIwzPfAcW2YjyIpA6, value = {application=quiz,
				// data={name_control=@+id/btnD, examination_key=-N7sISt0FBonyRwEm7nN,
				// questions_name_master=question_1, questions_name_detail=question_1b},
				// user_id=-N5ncBaINx6cuuzF1VAy,
				// event_dashboard_sesion=event_dashboard1658801944782,
				// event_dashboard_current_day=event_dashboard_current_day_2022_07_26,
				// event=get_data_layout1658802003066, message_key=1658802004252} }
				String user_id = snapshotRoot.child("user_id").getValue(String.class);
				String content_file  =  snapshotRoot.child("data/content_file").getValue(String.class);
				JSONObject jsonObject = new JSONObject(content_file);
				
				if(jsonObject.has("questions"))
				{
					insertQuestion(snapshotRoot,user_id,jsonObject);
				}
				if(jsonObject.has("icon_main_dashboard"))
				{
					insertIcon_main_dashboard(jsonObject);
				} 
				if(jsonObject.has("form_json_layout"))
				{
					insertForm_json_layout(jsonObject);
				} 
//				if(jsonObject.has("form_json_layout_quiz_general_topic"))
//				{
//					insertForm_json_layout(jsonObject);
//				} 
				 
//				
//			System.out.print("subject: "+subject);
//			System.out.print("grade: "+grade);
				
				 
				
				
				
	
				// Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);
				//
				// HashMap script_param = new HashMap<>();
				// Object collection = getReferenceNamePostData();
				// System.out.println("collection:" + String.valueOf(collection));
				// // "../draft/covid19/create_volunteers_group/post_data";
				// script_param.put("post_collection", collection);
				// // objJs.put("user_id_destination", user_id);
				//
				// Map mapReturnData = new HashMap<>();
				//
				// mapReturnData.put("script", getScriptName());
				// mapReturnData.put("script_param", script_param);
				//
				// objJs.put("data", mapReturnData);
				//
				// System.out.println("event:" + objJs.get("event"));
				// ResponseClient.sendResponse(objJs);
				// ResponseClient.sendResponse(objJs);
			 
				// ResponseClient.sendResponse(objJs);

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshotRoot.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshotRoot.getKey())
			.child("processing").setValue("error");
			e.printStackTrace();
		}
	}

 
	private void insertForm_json_layout(JSONObject jsonObjectRoot) {
		JSONObject jsonObject=	(JSONObject) jsonObjectRoot.get("form_json_layout") ;
	 
		String layout_form = (String) jsonObject.get("layout_form") ;
		
		String layout_formPath = ResFirebaseReference.getAbsolutePath((String) layout_form);
		Gson gson = new Gson();
		JSONObject json_layout = (JSONObject) jsonObject.get("layout_define") ;
//		JsonElement question_list = gson.toJsonTree(question_list1);
		 Map json_layoutMap = gson.fromJson(json_layout.toString(), Map.class);
		 FirebaseDatabase.getInstance().getReference(layout_formPath ).setValue(json_layoutMap);
	 
		
	}

private void insertIcon_main_dashboard(JSONObject jsonObjectRoot) {
	JSONObject jsonObject=	(JSONObject) jsonObjectRoot.get("icon_main_dashboard") 	; 
	
	Gson gson = new Gson();
//	JsonElement question_list = gson.toJsonTree(question_list1);
	 Map icon_main_dashboard = gson.fromJson(jsonObject.toString(), Map.class);
	 String pathquestion_icon_main_dashboard =   ResFirebaseReference.getAbsolutePath("../configuration/system_setting/layout/android/main_dashboard/grid_view_data/");//list_item");
	 FirebaseDatabase.getInstance().getReference(pathquestion_icon_main_dashboard).addListenerForSingleValueEvent(new ValueEventListener() {
		
		@Override
		public void onDataChange(DataSnapshot snapshot) {
			if(snapshot.exists())
			{
				long l = snapshot.child("list_item").getChildrenCount();
				 FirebaseDatabase.getInstance().getReference(pathquestion_icon_main_dashboard+ "/list_item/"+ String.valueOf(l)).setValue(icon_main_dashboard);
				
			}
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onCancelled(DatabaseError error) {
			// TODO Auto-generated method stub
			
		}
	});
	 
		
		
		 
	
}

private void insertQuestion(DataSnapshot snapshotRoot,String user_id, JSONObject jsonObjectRoot) {
	
	
	JSONObject jsonObject=	(JSONObject) jsonObjectRoot.get("questions") 	; 
	String description = (String) jsonObject.get("description");
	String questions_id = (String) jsonObject.get("questions_id");
	

//				"question_type": "question_bank",
//				  "subject": "informatics"
	String question_type = (String) jsonObject.get("question_type");
	String subject = (String) jsonObject.get("subject");
	Integer count = (Integer) jsonObject.get("count");
	
	JSONObject  question_list1 = (JSONObject) jsonObject.get("question_list");
	
	String question_list2 = question_list1.toString();
	
	
	Gson gson = new Gson();
//	JsonElement question_list = gson.toJsonTree(question_list1);
	 Map question_list = gson.fromJson(question_list2, Map.class);
	 
	 
	String pathsubject =   ResFirebaseReference.getAbsolutePath("../data/subject/"+subject);
	if(jsonObject.has("grade"))
	{
		String grade =  String.valueOf(jsonObject.get("grade"));
		pathsubject =pathsubject+ "/grade/" + grade;
		
	}
	else
	{
		 
	}
	
	pathsubject = pathsubject +"/" + question_type;
	String pathquestion_type =   ResFirebaseReference.getAbsolutePath("../data/"+question_type+ "/" +subject);
	
	if("".equals( questions_id))
	{
		String key = insertQuestionList(user_id, description, count, question_list, pathsubject, pathquestion_type);
		String msg = "Thêm câu hỏi thanh công, mã là: "+key;
		 
		showMessage(snapshotRoot, msg);
	}
	else
	{
		FirebaseDatabase.getInstance().getReference(pathsubject+"/"+questions_id).addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if(snapshot.exists())
				{
					String user_id_owner =  snapshot.child("user_id").getValue(String.class);
					if( user_id.equals(user_id_owner))
					{
						// update
						HashMap hm = new HashMap();
						hm.put("count", count);
						hm.put("question_list", question_list);
						
						
						FirebaseDatabase.getInstance().getReference(pathquestion_type+"/"+questions_id).setValue(hm);
						String msg = "Cập nhật ngân hàng câu hỏi thành công";
						 
						showMessage(snapshotRoot, msg);
					}
					else
					{
						//khong co quyen
						String msg = "Bạn không có quyền sửa ngân hàng câu hỏi này";
						 
						showMessage(snapshotRoot, msg);
					}
				}
				else {
				
				// id sai
					String msg = "Không thấy questions_id: "+questions_id;
					 
					showMessage(snapshotRoot, msg);
				}
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		} );
		
	}
	
	
	
}

private String  insertQuestionList(String user_id, String description, Integer count, Map question_list,
		String pathsubject, String pathquestion_type) {
	String key = FirebaseDatabase.getInstance().getReference(pathquestion_type).push().getKey();
	HashMap hm = new HashMap();
	hm.put("count", count);
	hm.put("question_list", question_list);
	
	
	FirebaseDatabase.getInstance().getReference(pathquestion_type+"/"+key).setValue(hm);
	
	HashMap hmsubject = new HashMap();
	hmsubject.put("description", description);
	hmsubject.put("create_date", DataUtil.getStringDay()    );
	hmsubject.put("user_id", user_id);
	
 
	
	FirebaseDatabase.getInstance().getReference(pathsubject+"/"+key).setValue(hmsubject);
	return key;
}

	 

	 

}
