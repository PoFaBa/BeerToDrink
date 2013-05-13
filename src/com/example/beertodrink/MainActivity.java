package com.example.beertodrink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        Button listeBiereButton = (Button) findViewById(R.id.BeerList);
        listeBiereButton.setOnClickListener(new OnClickListener() {
      			
        @Override
        public void onClick(View v) {
      	Intent intent = new Intent(MainActivity.this, BeerListActivity.class);
      	startActivity(intent);
      	}
      });
        
        
        Button listeBarButton = (Button) findViewById(R.id.BarList);
        listeBarButton.setOnClickListener(new OnClickListener() {
      			
        @Override
        public void onClick(View v) {
      	Intent intent2 = new Intent(MainActivity.this, BarListActivity.class);
      	startActivity(intent2);
      	}
      });
        
    }
    
}
