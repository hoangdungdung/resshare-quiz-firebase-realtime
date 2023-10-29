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

public class CommitTotalAnswerListener extends QuizBaseListener {

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "informatics";
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference("../commit_total_answer");
		// FirebaseRefCovid19.draft_covid19 + "/load_form_script/" + getType();
	}

	@Override
	public String getReferenceNamePostData() {
		// "../draft/covid19/create_volunteers_group/post_data";
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.requets_informatics);
		// "../requets/searching");
	}

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
			
				 
				String examination_key = snapshot1.child("data/total_examination_key").getValue(String.class);
				String subject = snapshot1.child("data/subject").getValue(String.class);
				FirebaseDatabase.getInstance().getReference(ResFirebaseReference.getAbsolutePath(
						"../data/test/examination/"+ subject +"/"+ user_id + "/" + examination_key)).addListenerForSingleValueEvent(new ValueEventListener() {
							
							@Override
							public void onDataChange(DataSnapshot snapshotexamination) {
								int dung=0;
								int sai =0;
								
								  for (DataSnapshot snapshotMaster : snapshotexamination.getChildren()) {
										 
									  for (DataSnapshot snapshotDetailt : snapshotMaster.getChildren()) {
										  String answer = snapshotDetailt.child("answer").getValue(String.class);
										  String customer_answer = snapshotDetailt.child("customer_answer/answer").getValue(String.class);
										  if(answer.equals(customer_answer) )
										  {
											  dung=dung+1;
											  
										  }
										  else
										  {
											  sai = sai+1;
												String message_key = snapshotDetailt.child("customer_answer/message_key").getValue(String.class);
												Map msgDashboardMessage = getHashMapResponse(snapshot1);
												msgDashboardMessage.put(CommandConstant.message_key, message_key);
												Map commandPropetybackground = new HashMap<>();
												commandPropetybackground.put(CommandConstant.background ,"@color/red" );
												
												Map commandgriditem = new HashMap<>();
												commandgriditem.put(CommandConstant.apply_propety,commandPropetybackground);
												Map mapGridItemControl = new HashMap<>();
												String controlName = "btn"+answer;
												mapGridItemControl.put(controlName, commandgriditem);
												Map mapGridItemCommand = new HashMap<>();
												
												mapGridItemCommand.put(CommandConstant.command, mapGridItemControl);
												mapGridItemCommand.put(CommandConstant.message_key, message_key);
												mapGridItemCommand.put(CommandConstant.msg_type, CommandConstant.command);
												
												
																					 
												Map binding_data_control = new HashMap<>();
												binding_data_control.put(CommandConstant.recycler_binding_data, mapGridItemCommand);
											 
											 
												
												Map mapCommand = new HashMap<>();
												mapCommand.put("recycler_view", binding_data_control);
												
												 
									 

												msgDashboardMessage.put("data", mapCommand);
												Date dt = new Date();
												String id = String.valueOf(dt.getTime());

												msgDashboardMessage.put("id", id);
											//	msgDashboardMessage.put("on_top", 1);
												msgDashboardMessage.put(CommandConstant.msg_type, CommandConstant.command);

										 

												sendResponse(msgDashboardMessage);
												 
												
											  
										  }

										}
									}
								  String viewResunt= "Đúng: "+  String.valueOf(dung) + " Sai: "+ String.valueOf(sai);
									Map mapExamination_key = new HashMap<>();
									mapExamination_key.put("text_total", viewResunt);
									mapExamination_key.put("text_nopbai", "Đã Nộp Bài");
									Map mapscreenCommand = new HashMap<>();
									mapscreenCommand.put(CommandConstant.set_data_field, mapExamination_key);
									//Map mapCommandLockForm = new HashMap<>();
									mapscreenCommand.put(CommandConstant.lock_form, true);
								//	mapCommand.put("screen", mapCommandLockForm);
									
									Map mapCommand = new HashMap<>();
									
									mapCommand.put(CommandConstant.screen, mapscreenCommand);
						 
							//	  HashMap hashMapCommand = new HashMap<>();
								//	msgDashboardMessage.put("data", mapCommand);
									
									sendCommand(snapshot1,mapCommand);
								  
								
							}
							
							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub
								
							}
						});
				
				
			

			


				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 

	 

}
