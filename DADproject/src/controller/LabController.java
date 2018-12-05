package controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import database.DatabaseConnection;
import model.Lab;

public class LabController {
	public static ArrayList<Lab> allLab() throws JSONException {
		ArrayList<Lab> labs = new ArrayList<Lab>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "getAllLab"));
		
		JSONArray jArr = DatabaseConnection.makeRequest(params);
		for(int i = 0; i<jArr.length();i++)
			try {
				labs.add(new Lab (jArr.getJSONObject(i).getString("labID"),
						jArr.getJSONObject(i).getString("labName"),
						jArr.getJSONObject(i).getString("labAbbrev"),
						jArr.getJSONObject(i).getString("labLocation"),
						jArr.getJSONObject(i).getString("staffID"),
						jArr.getJSONObject(i).getString("labType")));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		return labs;
	}
	
	public static ArrayList<String> allLabName() throws JSONException {
		ArrayList<String> labs = new ArrayList<String>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "getAllLabName"));
		
		JSONArray jArr = DatabaseConnection.makeRequest(params);
		for(int i = 0; i<jArr.length();i++)
			try {
				labs.add(jArr.getJSONObject(i).getString("labName"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return labs;
	}
	
		public static void deleteLab(Lab lab) throws JSONException {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("selectFn", "deleteLab"));
			
			params.add(new BasicNameValuePair("labID", lab.getId()));
			DatabaseConnection.makeRequest(params);
		}
		
		public static Lab searchLabByAbbrev(String labAbbrev) throws JSONException {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("selectFn", "getAllLabAbbrev"));
			
			params.add(new BasicNameValuePair("labAbbrev", labAbbrev));
			JSONArray jArr = DatabaseConnection.makeRequest(params);
			if(jArr.length()!=0){
				return new Lab(	jArr.getJSONObject(0).getString("labID"), 
								jArr.getJSONObject(0).getString("labName"), 
								jArr.getJSONObject(0).getString("labAbbrev"), 
								jArr.getJSONObject(0).getString("labLocation"), 
								jArr.getJSONObject(0).getString("staffID"));
			}
			else
				return null;
		}
		
		public static Lab searchLabByName(String labName) throws Exception {
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("selectFn", "searchLabByName"));
			
			params.add(new BasicNameValuePair("labName", labName));
			JSONArray jArr = DatabaseConnection.makeRequest(params);
			if(jArr.length()!=0){
				return new Lab(	jArr.getJSONObject(0).getString("labID"), 
								jArr.getJSONObject(0).getString("labName"), 
								jArr.getJSONObject(0).getString("labAbbrev"), 
								jArr.getJSONObject(0).getString("labLocation"), 
								jArr.getJSONObject(0).getString("staffID"));
			}
			else
				return null;
		}
		
		
		public static ArrayList<Lab> searchLabByStaffID (String staffID) throws Exception {
			ArrayList<Lab> labs = new ArrayList<Lab>();
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("selectFn", "searchLabByStaffID"));
			
			JSONArray jArr = DatabaseConnection.makeRequest(params);
			for(int i = 0; i<jArr.length();i++)
				try {
					labs.add(new Lab(	jArr.getJSONObject(i).getString("labID"), 
										jArr.getJSONObject(i).getString("labName"), 
										jArr.getJSONObject(i).getString("labAbbrev"), 
										jArr.getJSONObject(i).getString("labLocation"), 
										jArr.getJSONObject(i).getString("staffID")));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return labs;
		}
		
		public static ArrayList<Lab> searchLabByType (String labType) throws Exception {
			ArrayList<Lab> labs = new ArrayList<Lab>();
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("selectFn", "searchLabByType"));
			params.add(new BasicNameValuePair("labType", labType));
			
			JSONArray jArr = DatabaseConnection.makeRequest(params);
			for(int i = 0; i<jArr.length();i++)
				try {
					labs.add(new Lab(	jArr.getJSONObject(i).getString("labID"), 
										jArr.getJSONObject(i).getString("labName"), 
										jArr.getJSONObject(i).getString("labAbbrev"), 
										jArr.getJSONObject(i).getString("labLocation"), 
										jArr.getJSONObject(i).getString("staffID"),
										jArr.getJSONObject(i).getString("labType")));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return labs;
		}
	 
		public static void addLab(String name, String abbrev, String location, String staffID, String labType) throws Exception {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("selectFn", "addLab"));
			
			params.add(new BasicNameValuePair("labName", name));
			params.add(new BasicNameValuePair("labAbbrev", abbrev));
			params.add(new BasicNameValuePair("labLocation", location));
			params.add(new BasicNameValuePair("staffID", staffID));
			params.add(new BasicNameValuePair("labType", labType));
			DatabaseConnection.makeRequest(params);
		}
		
		public static ArrayList<Object[]> getAllLab () throws Exception {
			ArrayList<Object[]> objects = new ArrayList<Object[]>(); 
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("selectFn", "getAllLab"));
			
			JSONArray jArr = DatabaseConnection.makeRequest(params);
			for(int i = 0; i<jArr.length();i++)
				try {
					objects.add(new Object[] {jArr.getJSONObject(i).getString("labID"), 
										jArr.getJSONObject(i).getString("labName"), 
										jArr.getJSONObject(i).getString("labAbbrev"), 
										jArr.getJSONObject(i).getString("labLocation"), 
										jArr.getJSONObject(i).getString("staffID")});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return objects;
		}
		
		public static void updateLab(String labID, String name, String abbrev, String location,String staffID, String labType) throws Exception {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("selectFn", "updateLab"));
			
			params.add(new BasicNameValuePair("labName", name));
			params.add(new BasicNameValuePair("labAbbrev", abbrev));
			params.add(new BasicNameValuePair("labLocation", location));
			params.add(new BasicNameValuePair("staffID", staffID));
			params.add(new BasicNameValuePair("labType", labType));
			params.add(new BasicNameValuePair("labID", labID));
			DatabaseConnection.makeRequest(params);
		}
}