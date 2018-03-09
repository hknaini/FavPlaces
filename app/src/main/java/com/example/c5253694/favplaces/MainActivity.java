package com.example.c5253694.favplaces;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap hashMap = new HashMap();
    ArrayList<String> mainArrayList = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("In on creatre-- ", "true");

        ListView listView = (ListView) findViewById(R.id.listView);
      //  hashMap.put(0, new String[]{"Add", "Add", "Add"});
        ArrayList al = new ArrayList();
        al.add("Add");
        al.add("Add");
        al.add("Add");
        hashMap.put(0 , al);



        mainArrayList.add("Add New Place...");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mainArrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                if (i == 0) {
                    Log.i("Add-- ", "true");
                    intent.putExtra("Add", "Add");
                } else {
                    Log.i("Add-- ", "false");
                     ArrayList output = (ArrayList) hashMap.get(i);
                            //{hashMap.get(i).toString(), hashMap.get(i).toString(), hashMap.get(i).toString()};
                    intent.putExtra("Add", "No");
                    String[] myStringArray = {output.get(0).toString(),output.get(1).toString(),output.get(2).toString()};
                    intent.putExtra("favData", myStringArray);

                }

                startActivityForResult(intent, 0);

            }


        });


    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {
        Log.i("rescode", Integer.toString(resultCode));

       // if ( resultCode == Activity.RESULT_OK) {

            String[] favData = intent.getStringArrayExtra("favData");
            Log.i("length", Integer.toString(favData.length));
            if (resultCode == 2) {

                ArrayList al = new ArrayList();
                // if (null != favData[0])
                //{
                    al.add(favData[0].toString());
                    al.add(favData[1].toString());
                    al.add(favData[2].toString());
                    hashMap.put(hashMap.size(), al);

                    // hashMap.put(hashMap.size(), new String[]{favData[0].toString(), favData[1].toString(), favData[2].toString()});
                    mainArrayList.add(favData[0].toString());
                    ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mainArrayList);
                    Log.i("MALSIZE", Integer.toString(mainArrayList.size()));
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(arrayAdapter);
                }


           // }
        /*}
        else
        {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mainArrayList);
            Log.i("MALS", Integer.toString(mainArrayList.size()));
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(arrayAdapter);
        }*/
    }
}



