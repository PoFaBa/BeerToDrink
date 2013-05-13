package com.example.beertodrink;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BarListActivity extends Activity{

	/** Called when the activity is first created. */
	public static final String strURL = "http://10.0.2.2:8080/mesRequestes/Alarm.php";    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_list);
        new AsyncTask<Void,Void,List<Map<String,Object>>>()
        {
			@Override
			protected List<Map<String, Object>> doInBackground(Void... voids) 
			{
          	   List<Map<String, Object>>  res = new ArrayList<Map<String, Object>>();
               InputStream is = null;  
               String result = "";  
 
               try{  
                   HttpClient httpclient = new DefaultHttpClient();
                   HttpPost httppost = new HttpPost(strURL);  
                   HttpResponse response = httpclient.execute(httppost);    
                   is = response.getEntity().getContent(); 
 
               }catch(Exception e)
               {  
                   Log.e("log_tag", "Error in http connection " + e.toString());  
               }  
 
               try{  
               	BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8); 
               	StringBuilder sb = new StringBuilder();  
                   String line = null;  
                   while ((line = reader.readLine()) != null) 
                   {  
                       sb.append(line + "\n");  
                   }  
                   is.close();  
                   result=sb.toString();  
                   Log.i("result", result);
               }catch(Exception e)
               {  
                   Log.e("log_tag", "Error converting result " + e.toString());  
               }   
               try{  
                  JSONArray jArray = new JSONArray(result);  
                   for(int i=0;i<jArray.length();i++)
                   {  
                      JSONObject json_data = jArray.getJSONObject(i);  
 
                       Map<String, Object> map = new HashMap<String, Object>();
                       map.put("NomBar", json_data.getString("NomBar"));
                       res.add(map); 
                       Log.i("log_tag","NomBar: "+json_data.getString("NomBar"));  
                   }  
               }catch(JSONException e)
               {  
                   Log.e("log_tag", "Error parsing data " + e.toString());  
               }  
              return res;
			}
			protected void onPostExecute(List<Map<String, Object>> res) {
				ListView list = (ListView) findViewById(R.id.list);
				list.setAdapter(new SimpleAdapter(BarListActivity.this, res,
						R.layout.list_item, new String[] {"NomBar"}, new int[] {R.id.text1 }));
				
				list.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,int position, long arg3) {
						// getting values from selected ListItem
		                String name = ((TextView) view.findViewById(R.id.text1)).getText().toString();		               
		                if (name=="Bar a thym"){		                 
		                // Starting new intent
		                Intent intent = new Intent(getApplicationContext(), SingleBarActivity.class);
		                startActivity(intent);
		                };
					}
	 
	            });
	 
	           }   
	        }.execute(); 
	    }}
    
 
	 
 
      