package com.myapp.dataanalysis.call_log;

import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.myapp.dataanalysis.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class uniqueCallLog extends AppCompatActivity {
    ListView listView;
    ArrayList<String> finalString = new ArrayList<String>();
    ArrayList<String> numberList = new ArrayList<String>();
    ArrayList<String> durationList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_call_log);


        listView=findViewById(R.id.callLogListViewID);

        getData();

        getFinalString();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,finalString);

        listView.setAdapter(adapter);


    }

    void getData(){


        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);


        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callDuration = managedCursor.getString(duration);
            numberList.add(phNumber);
            durationList.add(callDuration);
        }
    }

    void getFinalString(){

        int duration=0;
        Set<String> uniqueNumber = new HashSet<String>(numberList);
        for (String uniqueAddress : uniqueNumber) {

            for (int i=0;i<numberList.size();i++){
                if(uniqueAddress.equals(numberList.get(i))){
                    duration=duration+Integer.valueOf(durationList.get(i));
                }
            }

            finalString.add("Number: "+uniqueAddress + "\nTotal Duration: (in sec) " + duration);
        }

    }
}
