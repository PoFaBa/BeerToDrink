package com.example.beertodrink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BeerListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);
        
        //Récupération des données dans la bdd
        // Information d'accès à la base de données
        String url = "jdbc:mysql://localhost/BeerToDrink";
        String login = "root";
        String passwd = "";
        Connection cn =null;
        Statement st =null;
        ResultSet rs =null;
        try {
        // Etape 1 : Chargement du driver
        Class.forName("com.mysql.jdbc.Driver");
        // Etape 2 : récupération de la connexion
        cn = DriverManager.getConnection(url, login, passwd);
       
       
        // Etape 3 : Création d'un statement
        st = cn.createStatement();
        String sql = "SELECT NomBiere     FROM  Biere ORDER BY NomBiere ASC";
       
        // Etape 4 : exécution requête
        rs = (ResultSet) st.executeQuery(sql);
       
        // Etape 5: parcours resultatSet
        
        rs.last();
        String[] out = new String[rs.getRow()];

        rs.beforeFirst();
        int n=0;
        while (rs.next()) {
            out[n]=rs.getString(1);
            n++;
          }
       // Etape 6 ajout des valeurs dans la listView
        
        final ListView listview = (ListView) findViewById(R.id.listview);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < out.length; ++i) {
          list.add(out[i]);
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        		this,
        		R.layout.activity_beer_list,
        		out);
        listview.setAdapter(adapter);
        
       
        rs = (ResultSet) st.executeQuery(sql);
        
        }
        catch (SQLException e) {
        e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
        e.printStackTrace();
        }
        finally {
        try { // Etape 6 libérer ressources de la mémoire.
        cn.close();
        st.close();
        }
        catch (SQLException e) {
        e.printStackTrace();
        }
        }
        
        
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
}
