package com.resshare.quiz.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.ResFirebaseReference;

public class AddSubjectListener extends QuizBaseListener {

//	@Override
//	public String getType() {
//		// TODO Auto-generated method stub
//		return "informatics";
//	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference("../add_subject");
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

//grade
 
//subject_code
 
//subject_name
 
//subject_screen
 
	@Override
	public void onChildAdded(DataSnapshot snapshotRoot, String previousChildName) {
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
				Boolean grade  =  snapshotRoot.child("data/grade").getValue(Boolean.class);
				String subject_code  =  snapshotRoot.child("data/subject_code").getValue(String.class);
				String subject_name  =  snapshotRoot.child("data/subject_name").getValue(String.class);
				String subject_screen  =  snapshotRoot.child("data/subject_screen").getValue(String.class);
				
				
				 
				String temple_questions =   ResFirebaseReference.getAbsolutePath("../configuration/system_setting/layout/temple_questions");
				FirebaseDatabase.getInstance().getReference(temple_questions).addListenerForSingleValueEvent(new ValueEventListener() {
					
					@Override
					public void onDataChange(DataSnapshot snapshottemple_questions) {
						if(snapshottemple_questions.exists())
						{
							String main_define =   ResFirebaseReference.getAbsolutePath("../configuration/system_setting/layout/main_define/"+subject_code);
							FirebaseDatabase.getInstance().getReference(main_define).addListenerForSingleValueEvent(new ValueEventListener() {
								
								@Override
								public void onDataChange(DataSnapshot snapshotmain_define) {
									
									// TODO Auto-generated method stub
									
									if(snapshotmain_define.exists())
									{
										String user_id_old = snapshotmain_define.child("user_id").getValue(String.class);
										if(user_id.equals(user_id_old))
										{
										Long	index_key =   snapshotmain_define.child("index_key").getValue(Long.class);
										insertMainDashboard(index_key,user_id,grade, subject_code, subject_name, subject_screen, snapshottemple_questions);
										
										String msg="Cập nhật thành công";
										//message don't permistion edit
										showMessage(snapshotRoot, msg);
										}
										else
										{
											String msg="Bạn không có quền sửa";
											//message don't permistion edit
											showMessage(snapshotRoot, msg);
										}
										
									}
									else
									{//private void insertMainDashboard(Long index_key,String user_id,Boolean grade, String subject_code, String subject_name,
										//String subject_screen, DataSnapshot snapshot) {
										insertMainDashboard(-1L,user_id,grade, subject_code, subject_name, subject_screen, snapshottemple_questions);
										String msg="Thêm mới thành công";
										//message don't permistion edit
										showMessage(snapshotRoot, msg);
									}
									
								}
								
								@Override
								public void onCancelled(DatabaseError error) {
									// TODO Auto-generated method stub
									
								}
							});
							
							 
							
							
						}
						
					}
					//insertIcon_main_dashboardString(String user_id,String icon_main_dashboard, Long index_key,String subject_code) {

					private void insertMainDashboard(Long index_key,String user_id,Boolean grade, String subject_code, String subject_name,
							String subject_screen, DataSnapshot snapshot) {
						Gson gson = new Gson();
						Object icon_main_dashboard = snapshot.child("icon_main_dashboard").getValue();
//	JSONObject jsonObjectIcon_main_dashboard = new JSONObject(icon_main_dashboard);
						//gson.toJson(icon_main_dashboard)
						
						String sJsonObjectIcon_main_dashboard = gson.toJson(icon_main_dashboard);
						String sJsonObjectIcon_main_dashboard1 = sJsonObjectIcon_main_dashboard.replace("subject_code", subject_code);
						String subject_name_code = subject_name + " (" +subject_code + ")";
						String sJsonObjectIcon_main_dashboard2 = sJsonObjectIcon_main_dashboard1.replace("subject_name", subject_name_code);
						insertIcon_main_dashboardString(user_id,sJsonObjectIcon_main_dashboard2,index_key,subject_code);
						
						
						 
						 
							String  slayout_form1 = (String) snapshot.child("form_json_layout/layout_form").getValue();
							String layout_form1 = slayout_form1.replace("subject_code", subject_code);
							Object layout_define0 = snapshot.child("form_json_layout/layout_define").getValue();
							//  JSONObject Jslayout_define = new JSONObject(layout_define);
								String sJslayout_define01 = gson.toJson(layout_define0);
								String sJslayout_define101 = sJslayout_define01.replace("subject_screen", subject_screen);
								
								if(!grade)
								{
									String sJslayout_define1201 = sJslayout_define101.replace("quiz_general", "quiz_general_topic");
									sJslayout_define101 = sJslayout_define1201;
								}
								
						
								 Map json_layoutMap01 = gson.fromJson(sJslayout_define101.toString(), Map.class);
								 String layout_formPath01 = ResFirebaseReference.getAbsolutePath((String) layout_form1);
								 FirebaseDatabase.getInstance().getReference(layout_formPath01 ).setValue(json_layoutMap01);
								 
								 
								 
								 
									String  slayout_form_test = (String) snapshot.child("form_json_layout_view_question/layout_form").getValue();
									String layout_form_test = slayout_form_test.replace("subject_code", subject_code);
									Object layout_define_test = snapshot.child("form_json_layout_view_question/layout_define").getValue();
									//  JSONObject Jslayout_define = new JSONObject(layout_define);
										String sJslayout_define_test = gson.toJson(layout_define_test);
										String sJslayout_define1_test = sJslayout_define_test.replace("subject_screen", subject_screen);
										
								 
								
										 Map json_layoutMap_test_test = gson.fromJson(sJslayout_define1_test.toString(), Map.class);
										 String layout_formPath_test = ResFirebaseReference.getAbsolutePath((String) layout_form_test);
										 FirebaseDatabase.getInstance().getReference(layout_formPath_test ).setValue(json_layoutMap_test_test);
								 
								 
								 
								 
					}
					
					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub
						
					}
				});
				
				
				
			
//			System.out.print("subject: "+subject);
//			System.out.print("grade: "+grade);
				
				 
				
				
				
	
	

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshotRoot.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
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
	private void insertIcon_main_dashboardString(String user_id,String icon_main_dashboard, Long index_key,String subject_code) {
	 
		Gson gson = new Gson();
//		JsonElement question_list = gson.toJsonTree(question_list1);
		 Map mapIcon_main_dashboard = gson.fromJson(icon_main_dashboard, Map.class);
	 
//		JsonElement question_list = gson.toJsonTree(question_list1);
	 
		 String pathquestion_icon_main_dashboard =   ResFirebaseReference.getAbsolutePath("../configuration/system_setting/layout/android/main_dashboard/grid_view_data/");//list_item");
		 FirebaseDatabase.getInstance().getReference(pathquestion_icon_main_dashboard).addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if(snapshot.exists())
				{
					Long index_key1= index_key;
					if(index_key<0)
					{
						index_key1 = snapshot.child("list_item").getChildrenCount();
						String main_define =   ResFirebaseReference.getAbsolutePath("../configuration/system_setting/layout/main_define/"+subject_code);
						Map hmap =new HashMap<>();
						hmap.put("user_id", user_id);
						hmap.put("index_key", index_key1);
						
						FirebaseDatabase.getInstance().getReference(main_define).setValue(hmap);
						
					}
					 FirebaseDatabase.getInstance().getReference(pathquestion_icon_main_dashboard+ "/list_item/"+ String.valueOf(index_key1)).setValue(mapIcon_main_dashboard);
					
				}
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
		 
			
			
			 
		
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

private void insertQuestion(JSONObject jsonObjectRoot) {
	
	
	JSONObject jsonObject=	(JSONObject) jsonObjectRoot.get("questions") 	; 
	String description = (String) jsonObject.get("description");

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
	
	
	String key = FirebaseDatabase.getInstance().getReference(pathquestion_type).push().getKey();
	HashMap hm = new HashMap();
	hm.put("count", count);
	hm.put("question_list", question_list);
	
	
	FirebaseDatabase.getInstance().getReference(pathquestion_type+"/"+key).setValue(hm);
	HashMap hmsubject = new HashMap();
	hmsubject.put("description", description);
	hmsubject.put("create_date", DataUtil.getStringDay()    );
	
 
	
	FirebaseDatabase.getInstance().getReference(pathsubject+"/"+key).setValue(hmsubject);
}

	 

	 

}
