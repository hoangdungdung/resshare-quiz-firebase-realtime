package com.resshare.quiz.services.grade;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resshare.book.RefFireBaseBook;
import com.resshare.common.CommandConstant;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.reflectproxy.ProxyDemo;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.FireBaseReference;
import com.resshare.framework.core.service.IRequest;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.framework.model.Input;
import com.resshare.goibinhoxy.service.listener.LoadFormOxyBaseListener;
import com.resshare.quiz.services.QuizBaseListener;

public class FormOptionCfgGradeListener  extends QuizBaseListener {

//	@Override
//	public String getType() {
//		// TODO Auto-generated method stub
//		return "informatics";
//	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference("../option_cfg_grade");
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
//		// TODO Auto-generated method stub
//		return LoadFormSearchingUI.class.getName();
//	}

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);

				 
				String user_id = (String) objJs.get("user_id");
				String gradeValue = snapshot1.child("data/grade").getValue(String.class);
						
				FirebaseDatabase.getInstance()
				.getReference(ResFirebaseReference.getAbsolutePath("../data/profile/" + user_id+"/grade")).setValue(gradeValue);
				
				
				
				HashMap hashMapCommand = new HashMap<>();
				HashMap hashMapCommand1 = new HashMap<>();
				hashMapCommand1.put(CommandConstant.show_alert_dialog, "Tùy chọn của bạn đã được lưu");
				hashMapCommand.put("screen",hashMapCommand1 );
				
				sendCommand(snapshot1,hashMapCommand);
				

			

		 
				
				
				
				
			 

			
				// ResponseClient.sendResponse(objJs);

				// ResponseClient.sendResponse(objJs);

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void sendMessageQuestionView(  DataSnapshot snapshot11,  String user_id,
			String questions_name_master, String arrayQuestionsNameNext,
			DataSnapshot objSnapshotquestions_name_detail, String examination_key,  int order_number , String subject              ) {
		String questions_name_detail = objSnapshotquestions_name_detail.getKey();
		
		
		

		String question = objSnapshotquestions_name_detail.child("question").getValue(String.class);
		question = "Câu "+ String.valueOf(order_number)  + ": " + question;
		String answer = objSnapshotquestions_name_detail.child("answer").getValue(String.class);
		
		String A = objSnapshotquestions_name_detail.child("answer_choose/A").getValue(String.class);
		String B = objSnapshotquestions_name_detail.child("answer_choose/B").getValue(String.class);
		String C = objSnapshotquestions_name_detail.child("answer_choose/C").getValue(String.class);
		String D = objSnapshotquestions_name_detail.child("answer_choose/D").getValue(String.class);
	

		String examination_key_questions = examination_key + "/" + questions_name_master
				+ "/" + questions_name_detail;
		FirebaseDatabase.getInstance()
				.getReference(
						ResFirebaseReference.getAbsolutePath("../data/test/examination/"
								+ subject+"/"+user_id + "/" + examination_key_questions + "/answer" ))
				.setValue(answer);
		Map stepMap = new HashMap<>();
		order_number =order_number+1;
		stepMap.put("step", order_number);
		stepMap.put("array_questions_name", arrayQuestionsNameNext);

		FirebaseDatabase.getInstance()
				.getReference(ResFirebaseReference
						.getAbsolutePath("../data/test/examination_step/" +subject + "/"+ user_id + "/"
								+ examination_key ))
				.setValue(stepMap);

		Map fieldMap = new HashMap<>();
		fieldMap.put("A","A: "+ A);
		fieldMap.put("B","B: "+ B);
		fieldMap.put("C","C: "+ C);
		fieldMap.put("D","D: "+ D);
//fieldMap.put("answer", answer);
		fieldMap.put("question", question);
		fieldMap.put("questions_name_master", questions_name_master);
		fieldMap.put("questions_name_detail", questions_name_detail);
		fieldMap.put("examination_key", examination_key);
		fieldMap.put("subject", subject);
		
		viewGridUI(snapshot11, fieldMap);
	}

	


	public DataSnapshot getQuestionsDetailt(String questions_name_master, DataSnapshot snapshot_question_list) {
		DataSnapshot snapshot_questions_name_master = snapshot_question_list.child(questions_name_master);
		
		return	getQuestionDetailtByMaster(snapshot_questions_name_master);

		// TODO Auto-generated method stub
		
	}

	public DataSnapshot getQuestionDetailtByMaster(DataSnapshot snapshot_questions_name_master) {
		if (snapshot_questions_name_master != null) {
			 long count = snapshot_questions_name_master.getChildrenCount();
			double x = Math.random() *   (count);
			  long i = Math.round(x);
			  int j=1;
			  for (DataSnapshot snapshotChild : snapshot_questions_name_master.getChildren()) {
					 if(j>=i)
						 return snapshotChild;
					 j++;

				}
			 

		}
		return null;
	}
	
	public String arrayQuestionsNameNext(String questionsName, String arrayQuestionsName) {
		String questionsNameP =  questionsName  + ",";
		if (arrayQuestionsName.contains(questionsNameP)) {
			return arrayQuestionsName.replace(questionsNameP, "");
		}
		// TODO Auto-generated method stub
		return arrayQuestionsName.replace(questionsName, "");
	}

	public String getQuestionsName(String listQuestionsName) {
		// TODO Auto-generated method stub
		if (listQuestionsName.contains(",")) {
			String[] arrayQuestionsName = listQuestionsName.split(",");
			return arrayQuestionsName[0];
		}
		return listQuestionsName;
	}

	public String getArray(DataSnapshot snapshot) {

		String sName = "";
		String arrayName = "";
		for (DataSnapshot snapshotChild : snapshot.getChildren()) {
			sName = snapshotChild.getKey();
			arrayName = arrayName + sName + ",";

		}
		return arrayName.substring(0, arrayName.length() - 1);
	}
	public static void sendCommand(DataSnapshot snapshot, Map commandMap) {

		try {

			Map msgDashboardMessage = getHashMapResponse(snapshot);

  
			Date dt = new Date();
			String id = String.valueOf(dt.getTime());
 

			 
	 

	 
 

			msgDashboardMessage.put("data", commandMap);

			msgDashboardMessage.put("id", id);
			 
			msgDashboardMessage.put("msg_type", "command");

	 

			sendResponse(msgDashboardMessage);

			// FirebaseDatabase.getInstance().getReference(
			// ResFirebaseReference.getAbsolutePath(RefFireBaseBook.BOOK_DATA_BOOKS_NEW+"/"+book_id))

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void viewGridUI(DataSnapshot snapshot, Map fieldMap) {

		try {

			Map msgDashboardMessage = getHashMapResponse(snapshot);

 
			String path = "system_setting/layout/android/quiz_text_layout/layout";
 

			Object cfgLayout = com.resshare.framework.core.service.Cache.configuration.child(path).getValue();
 
			Date dt = new Date();
			String id = String.valueOf(dt.getTime());
 

			Map mapGridItem = new HashMap<>();


			mapGridItem.put("layout", cfgLayout);
			mapGridItem.put("layout_data_field", fieldMap);
			Map mapControlRecycler_viewCommand = new HashMap<>();
			mapControlRecycler_viewCommand.put(   "recycler_binding_data", mapGridItem);
			//"examination_key", examination_key
			Object examination_key = fieldMap.get("examination_key");
			Map mapExamination_key = new HashMap<>();
			mapExamination_key.put("total_examination_key", examination_key);
			mapExamination_key.put("subject", fieldMap.get("subject"));
			Map mapscreenCommand = new HashMap<>();
			mapscreenCommand.put("set_data_field", mapExamination_key);
			
			Map mapCommand = new HashMap<>();
			mapCommand.put("recycler_view", mapControlRecycler_viewCommand);
			
			
			
			
			
			mapCommand.put("screen", mapscreenCommand);
 

			msgDashboardMessage.put("data", mapCommand);

			msgDashboardMessage.put("id", id);
			msgDashboardMessage.put("on_top", 1);
			msgDashboardMessage.put("msg_type", "command");

	 

			sendResponse(msgDashboardMessage);
			
			
			
			


			// FirebaseDatabase.getInstance().getReference(
			// ResFirebaseReference.getAbsolutePath(RefFireBaseBook.BOOK_DATA_BOOKS_NEW+"/"+book_id))

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	static Map getHashMapResponse(DataSnapshot snapshot) {
		DashboardMessage dashboardMessage = new DashboardMessage();
		dashboardMessage.setApplication(snapshot.child("application").getValue(String.class));
		dashboardMessage.setDelete(0);
		dashboardMessage.setEvent(snapshot.child("event").getValue(String.class));
		
			
		String user_id = snapshot.child("user_id_destination").getValue(String.class);
		if( snapshot.hasChild("user_id") )
			user_id = snapshot.child("user_id").getValue(String.class);
		dashboardMessage.setUser_id_destination(user_id);

		Map msgDashboardMessage = dashboardMessage.totHashMap();
		return msgDashboardMessage;
	}

	static void sendResponse(Map msgDashboardMessage) {
		ProxyDemo<IRequest> prIRequest = new ProxyDemo<IRequest>();
		IRequest iRequest = prIRequest.create(IRequest.class);
		Gson gson = new GsonBuilder().create();

		String jsonString = gson.toJson(msgDashboardMessage);
		Input input = new Input("resshare", "key1", jsonString);

		input.setDataCollection(FireBaseReference.draft_get_resshare_user_id);
		input.setModule("core");
		iRequest.input(input);
	}

	@Override
	public String getScriptName() {
		// TODO Auto-generated method stub
		return null;
	}

}
