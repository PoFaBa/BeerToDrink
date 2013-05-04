package com.example.beertodrink;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BeerListActivity extends Activity {
	String result1[];
	String result = "";
	InputStream is = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);
        
      
        ListView listview = (ListView) findViewById(R.id.listview);
        
        // Envoyer la requête au script PHP.
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
 		// Envoie de la commande http
 		try{
 			HttpClient httpclient = new DefaultHttpClient();
 			HttpPost httppost = new HttpPost("http://192.168.0.11/Biere.php");
 			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 			HttpResponse response = httpclient.execute(httppost);
 			HttpEntity entity = response.getEntity();
 			is = entity.getContent();

 		}catch(Exception e){
 			Log.e("log_tag", "Error in http connection " + e.toString());
 		}
 		
 		//convert response to string
 		 try{
 		         BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
 		         StringBuilder sb = new StringBuilder();
 		         String line = null;
 		         while ((line = reader.readLine()) != null) {
 		                 sb.append(line + "\n");
 		         }
 		         is.close();
 		         result=sb.toString();

 		         JSONArray jArray = new JSONArray(result);

 		             for(int i=0;i<jArray.length();i++){
 		                     JSONObject json_data = jArray.getJSONObject(i);

 		                     result1[i] = json_data.getString("nome");

 		                  } 

 		             listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, result1));  
 		       }
 		 catch(Exception e){
 		         Log.e("log_tag", "Error converting result "+e.toString());
 		         }
}
}


