package com.kaadiri.examtp2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    ListView liste;
    //ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(this);
        liste = (ListView) findViewById(R.id.listeView);


        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<Integer>  Ids = new ArrayList<>();
        ArrayList<String> firstNames = new ArrayList<>();
        ArrayList<String> lastNames = new ArrayList<>();
        ArrayList<String> birthDayes = new ArrayList<>();
        ArrayList<byte[]> images = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        Cursor res = mydb.select();
        if (res.getCount()== 0){
            Toast.makeText(this, "there is no data !!!", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            //StringBuffer buffer = new StringBuffer();
            int i = 0;
            while(res.moveToNext()){
                /*
                String per = "#ID :" + res.getInt(0) + "    #NAME : " + res.getString(1) + " " + res.getString(2)
                        + "    #AGE : " + res.getInt(3);
                this.tablePersonnes.add(per);

                 */
                Ids.add(i, res.getInt(0));
                firstNames.add(i, res.getString(1));
                lastNames.add(i, res.getString(2));
                birthDayes.add(i, res.getString(3));
                images.add(i, res.getBlob(4));
                emails.add(i, res.getString(5));
                i++;
            }
            for (int j= 0; j<Ids.size(); j++){
                Person person = new Person(Ids.get(j), firstNames.get(j), lastNames.get(j), birthDayes.get(j), images.get(j), emails.get(j));
                persons.add(person);
            }
            ListAdapter listAdapter = new ListAdapter(MainActivity.this, persons);
            liste.setAdapter(listAdapter);
            liste.setClickable(true);
            /*
            personnes = (ListView) findViewById(R.id.listPersonnes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , tablePersonnes);
            personnes.setAdapter(adapter);

             */
        }
    }

    public void onClickBtnAdd(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}