package com.resshare.framework.core.service;

import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetLayoutFormDataListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				try {

					String user_id = snapshot1.child("user_id").getValue(String.class);
					 
					String screen_name = snapshot1.child("screen_name").getValue(String.class);
					String event = snapshot1.child("event").getValue(String.class);
					String referenceShort = snapshot1.child("reference").getValue(String.class);
					Object parameter = snapshot1.child("parameter").getValue();
					String application = snapshot1.child("application").getValue(String.class);
					if (referenceShort != null) {
						String reference = ResFirebaseReference.getAbsolutePath(referenceShort);
						FirebaseDatabase.getInstance().getReference(reference)
								.addListenerForSingleValueEvent(new ValueEventListener() {

									@Override
									public void onDataChange(DataSnapshot snapshot) {
										if (snapshot.exists())
											try {

												// size = 6

												HashMap<String, Object> mapReturn = new HashMap<>();
												mapReturn.put("user_id_destination", user_id);
												mapReturn.put("screen_name", screen_name);
												
												mapReturn.put("event", event);
												mapReturn.put("parameter", parameter);
												// mapReturn.put("data", snapshot.getValue());
												mapReturn.put("application", application);
												mapReturn.put("session", snapshot1.getKey());
												// ProxyDemo<IResponse> pr = new ProxyDemo<IResponse>();
												// IResponse iResponse = pr.create(IResponse.class);

												long sendMessage = 0;
												if (snapshot.child("json_layout").exists()) {

													sendMessage++;

												}

												if (snapshot.child("method_definition").exists()) {

													sendMessage++;
												}
												if (snapshot.child("values_map").exists()) {

													sendMessage++;
												}

												if (snapshot.child("json_layout_include").exists()) {
													if (snapshot.child("json_layout_include").hasChildren()) {
														long childrenCount = snapshot.child("json_layout_include")
																.getChildrenCount();
														sendMessage = sendMessage + childrenCount;
													}
												}

												if (snapshot.child("method_script").exists()) {
													if (snapshot.child("method_script").hasChildren()) {
														long childrenCount = snapshot.child("method_script")
																.getChildrenCount();
														sendMessage = sendMessage + childrenCount;
													}
												}
												if (snapshot.child("method_script").exists()) {
													if (snapshot.child("method_script").hasChildren()) {

														Iterable<DataSnapshot> chil = snapshot.child("method_script")
																.getChildren();
														for (DataSnapshot dataSnapshot : chil) {
															String property = dataSnapshot.getValue(String.class);
															mapReturn.put("type", "method_script");
															mapReturn.put("key_data", property);
															mapReturn.put("data", dataSnapshot.getKey());
															mapReturn.put("count_message", sendMessage);

															FirebaseDatabase.getInstance()
																	.getReference(
																			ResFirebaseReference.getInputPathReference(
																					FireBaseReference.get_layout_script
																							+ "/" + property))
																	.push().setValue(mapReturn);
														}
													}

												}

												if (snapshot.child("json_layout_include").exists()) {
													if (snapshot.child("json_layout_include").hasChildren()) {

														Iterable<DataSnapshot> chil = snapshot
																.child("json_layout_include").getChildren();
														for (DataSnapshot dataSnapshot : chil) {
															mapReturn.put("type", "include_json");
															mapReturn.put("key_data", dataSnapshot.getKey());
															mapReturn.put("data", dataSnapshot.getValue());
															mapReturn.put("count_message", sendMessage);

															// iResponse.viewOject(mapReturn);
															RequestClient.sendRequest("resshare", "key1",
																	FireBaseReference.draft_get_resshare_user_id,
																	mapReturn);
														}
													}

												}

												if (snapshot.child("method_definition").exists()) {
													Object dataMethod_definition = snapshot.child("method_definition")
															.getValue();
													mapReturn.put("type", "method_definition");
													mapReturn.put("key_data", "method_definition");
													mapReturn.put("data", dataMethod_definition);
													mapReturn.put("count_message", sendMessage);

													// iResponse.viewOject(mapReturn);
													RequestClient.sendRequest("resshare", "key1",
															FireBaseReference.draft_get_resshare_user_id, mapReturn);

												}
												if (snapshot.child("values_map").exists()) {
													Object values_map = snapshot.child("values_map").getValue();
													mapReturn.put("type", "values_map");
													mapReturn.put("key_data", "values_map");
													mapReturn.put("data", values_map);
													mapReturn.put("count_message", sendMessage);

													// iResponse.viewOject(mapReturn);
													RequestClient.sendRequest("resshare", "key1",
															FireBaseReference.draft_get_resshare_user_id, mapReturn);

												}

												if (snapshot.child("json_layout").exists()) {

													Object dataLayout = snapshot.child("json_layout").getValue();
													mapReturn.put("type", "json_layout");
													mapReturn.put("key_data", "json_layout");
													mapReturn.put("data", dataLayout);
													mapReturn.put("count_message", sendMessage);

													// iResponse.viewOject(mapReturn);
													RequestClient.sendRequest("resshare", "key1",
															FireBaseReference.draft_get_resshare_user_id, mapReturn);

												}

												if (snapshot.child("load_form_script").exists()) {

													String path_load_form_script = snapshot.child("load_form_script")
															.getValue(String.class);
													mapReturn.put("type", "load_form_script");
													mapReturn.put("key_data", "load_form_script");
													mapReturn.put("data",  snapshot1.child("data").getValue());
													mapReturn.put("count_message", sendMessage);
													mapReturn.put("user_id", user_id);
													DatabaseReference rf = ResFirebaseReference
															.getInputReference(path_load_form_script);
													// DatabaseReference rf =
													// FirebaseDatabase.getInstance().getReference(path_load_form_script);
													rf.push().setValue(mapReturn);

												}

												// hm.put("vehicle",vehicle_drive);
												// if (map.containsKey("user_id_destination") &&
												// map.containsKey("event")) {

											} catch (Exception e) {
												e.printStackTrace();
											}
									}

									@Override
									public void onCancelled(DatabaseError error) {
										// TODO Auto-generated method stub

									}
								});
					}

				} catch (Exception e) {
					e.printStackTrace();
					FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
							.child("processing").setValue("error");
				}

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
					.child("processing").setValue("error");
		}
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/core/get_layout_data
		return ResFirebaseReference.getInputPathReference(FireBaseReference.get_layout_data);
	}
}
