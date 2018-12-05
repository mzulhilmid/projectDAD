package controller;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DatabaseConnection;

import model.Lab;
import model.Reservation;

public class ReservationController {
	private Lab lab;
	public ReservationController(Lab lab) {
		this.lab = lab;
	}

	public boolean checkIfAvailable(Time start, Time end, Date date) throws Exception {
		System.out.println(start.toString()+" "+end.toString()+" "+date.toString());
		if(start.after(end)) {
			System.out.println("start is after");
			return false;
		}

		if(start.equals(end)) {
			System.out.println("start is equal to end");
			return false;
		}
		System.out.println("Loading reservation");
		loadReservation();
		System.out.println("back in check");
		for(Reservation a: lab.getReservations()) {
			System.out.println("start : " +start+ "time reserved start : "+a.getTimeReservedStart()+"time reserved end : "+a.getTimeReservedEnd()+" "+a.getDateReserved());
			if(a.getDateReserved().toString().equals(date.toString())) {
				if(start.after(a.getTimeReservedStart()) && start.before(a.getTimeReservedEnd()) && a.getApprovalStatus().matches("APPROVED")){
					System.out.println("start : " +start+ "time reserved start : "+a.getTimeReservedStart()+"time reserved end : "+a.getTimeReservedEnd()+" "+a.getDateReserved());	
				}
				if (start.before(a.getTimeReservedStart()) && end.after(a.getTimeReservedStart()) && a.getApprovalStatus().matches("APPROVED"))
				{
					System.out.println("start : " +start+ "time reserved start : "+a.getTimeReservedStart()+"time reserved end : "+a.getTimeReservedEnd()+" "+a.getDateReserved());
				}
				if (start.equals(a.getTimeReservedStart()) && a.getApprovalStatus().matches("APPROVED"))
				{
					System.out.println("start : " +start+ "time reserved start : "+a.getTimeReservedStart()+"time reserved end : "+a.getTimeReservedEnd()+" "+a.getDateReserved());
				}
			}
		}
		System.out.println("true");
		return true;
	}

	public void loadReservation() throws JSONException {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add( new BasicNameValuePair("labID",Lab.getId()));
		params.add( new BasicNameValuePair("selectFn","loadReservation"));
		JSONArray jArr = DatabaseConnection.makeRequest(params);
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		for(int i =0;i<jArr.length();i++) {
			JSONObject jObj = jArr.getJSONObject(i);
			try {
				lab.loadReservation(new Reservation(Time.valueOf(jObj.getString("timeReservedStart")), 
						Time.valueOf(jObj.getString("timeReservedEnd")), 
						Date.valueOf(jObj.getString("dateReserved")), 
						jObj.getString("reservedBy"), 
						jObj.getString("reason"), 
						jObj.getString("approvalStatus")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("fininshed loading");
	}

	public static void deleteReservation(String reservationID) throws Exception {
	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add( new BasicNameValuePair("reservationID",reservationID));
		params.add( new BasicNameValuePair("selectFn","deleteReservation"));
		DatabaseConnection.makeRequest(params);
	}

	public void addNewReservation(Time start, Time end, Date date, String user, String reason, String position) throws Exception {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		System.out.println(user);
		params.add( new BasicNameValuePair("selectFn","addNewReservation"));
		params.add( new BasicNameValuePair("timeReservedStart",start.toString()));
		params.add( new BasicNameValuePair("timeReservedEnd",end.toString()));
		params.add( new BasicNameValuePair("dateReserved",date.toString()));
		params.add( new BasicNameValuePair("reservedBy",user));
		params.add( new BasicNameValuePair("labID",Lab.getId()));
		params.add( new BasicNameValuePair("reason",reason));
		params.add( new BasicNameValuePair("position",position));
		DatabaseConnection.makeRequest(params);

		lab.addReservation(new Reservation(start, end, date, user, reason));

	}

	public static ArrayList<Object[]> getReservedByUser(String userID, String labID) throws Exception {
		ArrayList<Object[]> objects = new ArrayList<Object[]>(); 
labID = Lab.getId();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add( new BasicNameValuePair("selectFn","getReservedByUser"));
		params.add( new BasicNameValuePair("reservedBy",userID));
		JSONArray jArr = DatabaseConnection.makeRequest(params);
		for(int i =0;i<jArr.length();i++) {
			JSONObject jObj = jArr.getJSONObject(i);
			objects.add(new Object[] {jObj.getString("Name"),
					jObj.getString("Start"),
					jObj.getString("End"),
					jObj.getString("Date"),
					jObj.getString("username"),
					jObj.getString("Reason"),
					jObj.getString("Status"),
					jObj.getString("rID")});
		}
		return objects;
	}

	public static ArrayList<Object[]> getReservedAll(String approvalStatus) throws Exception {
		ArrayList<Object[]> objects = new ArrayList<Object[]>(); 
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add( new BasicNameValuePair("selectFn","getReservedAll"));
		params.add( new BasicNameValuePair("approvalStatus",approvalStatus));
		JSONArray jArr = DatabaseConnection.makeRequest(params);
		for(int i =0;i<jArr.length();i++) {
			JSONObject jObj = jArr.getJSONObject(i);
			objects.add(new Object[] {jObj.getString("labName"),jObj.getString("timeReservedStart"),jObj.getString("timeReservedEnd"),jObj.getString("dateReserved"),jObj.getString("username"),jObj.getString("reason"),jObj.getString("approvalStatus"), jObj.getString("reservationID")});
		}

		return objects;
	}

	public static ArrayList<Object[]> getReservedByStaff(String userID, String approvalStatus) throws Exception {
		ArrayList<Object[]> objects = new ArrayList<Object[]>(); 

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add( new BasicNameValuePair("selectFn","getReservedByStaff"));
		params.add( new BasicNameValuePair("staffID",userID));
		params.add( new BasicNameValuePair("approvalStatus",approvalStatus));
		JSONArray jArr = DatabaseConnection.makeRequest(params);
		for(int i =0;i<jArr.length();i++) {
			JSONObject jObj = jArr.getJSONObject(i);
			objects.add(new Object[] {jObj.getString("Name"),
					jObj.getString("Start"),
					jObj.getString("End"),
					jObj.getString("Date"),
					jObj.getString("username"),
					jObj.getString("Reason"),
					jObj.getString("Status"),
					jObj.getString("rID")});
		}

		return objects;
	}

	public static void changeStatus(String reservationID, String approvalStatus) throws Exception {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add( new BasicNameValuePair("reservationID",reservationID));
		params.add( new BasicNameValuePair("selectFn","changeStatus"));
		params.add( new BasicNameValuePair("approvalStatus",approvalStatus));
		DatabaseConnection.makeRequest(params);
		
	}

	public String getLabID() {
		return Lab.getId();
	}


}