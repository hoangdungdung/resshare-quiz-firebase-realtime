package com.resshare.quiz.services;

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
import com.resshare.framework.core.reflectproxy.ProxyDemo;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.FireBaseReference;
import com.resshare.framework.core.service.IRequest;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.model.Input;

public class AnswerListener extends QuizBaseListener {

//	@Override
//	public String getType() {
//		// TODO Auto-generated method stub
//		return "informatics";
//	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference("../answer");
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
	public String getScriptName() {
		return null;
		// TODO Auto-generated method stub
	//	return LoadFormSearchingUI.class.getName();
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {
				// user_id
				// { key = -N7sIwzPfAcW2YjyIpA6, value = {application=quiz,
				// data={name_control=@+id/btnD, examination_key=-N7sISt0FBonyRwEm7nN,
				// questions_name_master=question_1, questions_name_detail=question_1b},
				// user_id=-N5ncBaINx6cuuzF1VAy,
				// event_dashboard_sesion=event_dashboard1658801944782,
				// event_dashboard_current_day=event_dashboard_current_day_2022_07_26,
				// event=get_data_layout1658802003066, message_key=1658802004252} }
				String user_id = snapshot1.child("user_id").getValue(String.class);
				String message_key = snapshot1.child("message_key").getValue(String.class);
				String questions_name_master = snapshot1.child("data/questions_name_master").getValue(String.class);
				String questions_name_detail = snapshot1.child("data/questions_name_detail").getValue(String.class);
				String examination_key = snapshot1.child("data/examination_key").getValue(String.class);
				String subject = snapshot1.child("data/subject").getValue(String.class);
			 
				String questions_type = (String)snapshot1.child("data/questions_type").getValue(String.class);
				String questions_key = (String)snapshot1.child("data/questions_key").getValue(String.class);
				String name_control = snapshot1.child("data/name_control").getValue(String.class);
				String answer = "";
				switch (name_control) {
				case "@+id/btnD":
					answer = "D";
					break;
				case "@+id/btnC":
					answer = "C";
					break;
				case "@+id/btnB":
					answer = "B";
					break;
				case "@+id/btnA":
					answer = "A";
					break;
				}

				String examination_key_questions = examination_key + "/" + questions_name_master + "/"
						+ questions_name_detail;
				HashMap hm = new HashMap<>();
				hm.put("answer", answer);
				hm.put("message_key", message_key);
				FirebaseDatabase.getInstance().getReference(ResFirebaseReference.getAbsolutePath(
						"../data/test/examination/" + subject+"/" + user_id + "/" + examination_key_questions + "/customer_answer"))
						.setValue(hm);

				// examination_step
				FirebaseDatabase.getInstance()
						.getReference(ResFirebaseReference
								.getAbsolutePath("../data/test/examination_step/"+ subject+"/" + user_id + "/" + examination_key))
						.addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshotexamination_step) {
								// TODO Auto-generated method stub
								// array_questions_name
								if (snapshotexamination_step.child("array_questions_name") != null) {
									String array_questions_name = snapshotexamination_step.child("array_questions_name")
											.getValue(String.class);
									if (!"".equals(array_questions_name)) {
										Integer step = snapshotexamination_step.child("step")
												.getValue(Integer.class);
										String questions_name_master = getQuestionsName(array_questions_name);
										String arrayQuestionsNameNext = arrayQuestionsNameNext(questions_name_master,
												array_questions_name);
										FirebaseDatabase.getInstance()
										.getReference(ResFirebaseReference
												.getAbsolutePath("../data/"+questions_type+"/" + subject + "/" + questions_key + "/question_list/" + questions_name_master))
										.addListenerForSingleValueEvent(new ValueEventListener() {
											
											@Override
											public void onDataChange(DataSnapshot snapshotquestions_name_master) {
												// TODO Auto-generated method stub
												DataSnapshot objSnapshotquestions_name_detail = getQuestionDetailtByMaster(snapshotquestions_name_master);
												 	
													sendMessageQuestionView( snapshot1,  user_id, questions_name_master,
															arrayQuestionsNameNext, objSnapshotquestions_name_detail,examination_key,step,subject,questions_type,questions_key);
											}
											
											@Override
											public void onCancelled(DatabaseError error) {
												// TODO Auto-generated method stub
												
											}
										});
									}
									else {
										//thông báo nộp bài hay xem lại
										// showAlertDialog ();.
										HashMap hashMapCommand = new HashMap<>();
										HashMap hashMapCommand1 = new HashMap<>();
										hashMapCommand1.put(CommandConstant.show_alert_dialog, "Bạn xem lại bài, chọn lại đáp án nếu cần, ấn Nộp Bài để xem kết quả");
										hashMapCommand.put("screen",hashMapCommand1 );
										
										sendCommand(snapshot1,hashMapCommand);
										
									}
								}

							}

							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub

							}
						});

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

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 

	 

}
