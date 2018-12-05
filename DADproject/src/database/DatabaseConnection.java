package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

public class DatabaseConnection {
	public static JSONArray makeRequest(List<NameValuePair> params) throws JSONException{
		
		InputStream is = null;
		String json = "";
		JSONArray jArr = null;
		StringBuilder sb = new StringBuilder();
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String strUrl = ("http://localhost/labreservation/webservice.php");
		HttpPost httpPost = new HttpPost(strUrl);
		try {
		if(params!=null) {
			params.add(new BasicNameValuePair("encode","url3986"));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		}
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		is = httpEntity.getContent();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		
		String line = null;
		while((line = reader.readLine())!=null) 
			sb.append(line+"\n");
		is.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		json = sb.toString();
		System.out.println(json);
		jArr = new JSONArray(json);
		
		
		return jArr;
	}
}