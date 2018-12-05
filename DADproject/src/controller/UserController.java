package controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DatabaseConnection;
import model.User;

public class UserController {
	private User user = new User();
	
	public static User searchUser(String username, char[] passwordChar) throws JSONException {
		String password = "";
		for(char a: passwordChar)
			password+=a;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "searchUser"));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		JSONArray jArr = DatabaseConnection.makeRequest(params);
		if(jArr.length()!=0) {
			JSONObject user = jArr.getJSONObject(0);
			return new User(user.getString("userID"),user.getString("username"),user.getString("password"), user.getString("position"), user.getString("phone"));
		}
		else 
			return null;
	}
	
	public void updateUser(User user) throws JSONException {	
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "updateUser"));
			
		params.add(new BasicNameValuePair("username", user.getUserName()));
		params.add(new BasicNameValuePair("password", user.getPassword()));
		params.add(new BasicNameValuePair("position", user.getPosition()));
		params.add(new BasicNameValuePair("phone", user.getPhone()));
		DatabaseConnection.makeRequest(params);
	}
	public void deleteUser(String username) throws JSONException {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "deleteUser"));
		
		params.add(new BasicNameValuePair("username", username));
		DatabaseConnection.makeRequest(params);
	}
	public static void addUser(String username, String password, String position, String phone) throws Exception {
	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "addUser"));
		
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("position", position));
		params.add(new BasicNameValuePair("phone", phone));
	}
	public static User searchByUser(String username) throws JSONException {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "searchByUser"));
		
		params.add(new BasicNameValuePair("username", username));
		JSONArray jArr = DatabaseConnection.makeRequest(params);
		if(jArr.length()!=0){
			return new User(jArr.getJSONObject(0).getString("userID"), 
							jArr.getJSONObject(0).getString("username"), 
							jArr.getJSONObject(0).getString("password"), 
							jArr.getJSONObject(0).getString("position"), 
							jArr.getJSONObject(0).getString("phone"));
		}
		else
			return null;
	}
	
	public static ArrayList<User> searchByPosition(String position) throws JSONException {
		ArrayList<User> users = new ArrayList<User>();
		 
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("selectFn", "searchByPosition"));
		params.add(new BasicNameValuePair("position", position));
		
		JSONArray jArr = DatabaseConnection.makeRequest(params);
		for(int i = 0; i<jArr.length();i++)
			try {
				users.add(new User(	jArr.getJSONObject(i).getString("userID"), 
									jArr.getJSONObject(i).getString("username"), 
									jArr.getJSONObject(i).getString("password"), 
									jArr.getJSONObject(i).getString("position"), 
									jArr.getJSONObject(i).getString("phone")));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return users;
	}
	
	public static ArrayList<String> listAllUsername(ArrayList<User> users){
		ArrayList<String> usernames = new ArrayList<String>();
		for (int i = 0; i<users.size();i++)
			usernames.add(users.get(i).getUserName());
		return usernames;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setUser(User user)
	{
		this.user=user;
	}
}