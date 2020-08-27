package com.example.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private  EditText editAlaramId;
    private Button setAlaramId,cancelAlaramId;
    private RadioGroup radioGroupalaram;
    private RadioButton radioButton;

    private Intent alarmintent;
    private PendingIntent pendingIntent;

    private static final int ALARM_REQUEST_CODE=100;

//private RadioButton secoundsId,minutesId,housrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editAlaramId=findViewById(R.id.editAlaramId);
        setAlaramId=findViewById(R.id.setAlaramId);
        cancelAlaramId=findViewById(R.id.cancelAlaramId);
        radioGroupalaram=findViewById(R.id.radioGroupalaram);
        /*secoundsId=findViewById(R.id.secoundsId);
        minutesId=findViewById(R.id.minutesId);
        housrId=findViewById(R.id.housrId);*/

        alarmintent=new Intent(MainActivity.this,AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(MainActivity.this,ALARM_REQUEST_CODE,alarmintent,0);


        setAlaramId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String interval = editAlaramId.getText().toString();
                if (!TextUtils.isEmpty(interval))
                {
                    if (radioGroupalaram.getCheckedRadioButtonId()==-1){
                        Toast.makeText(MainActivity.this,"select any one option!!",Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        int id=radioGroupalaram.getCheckedRadioButtonId();
                       radioButton=findViewById(id);
                        int Interval=Integer.parseInt(interval);
                        int finalInterval=getCorrectInterval(Interval);

                if (finalInterval!=0){
                    Calendar cal=Calendar.getInstance();
                    cal.add(Calendar.SECOND,finalInterval);
                    AlarmManager manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    manager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
                    Toast.makeText(MainActivity.this,"alarm set for:"+finalInterval+" "+radioButton.getText(),Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(MainActivity.this,"value should not be 0",Toast.LENGTH_SHORT).show();
                }
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"time interval can't be empty!!",Toast.LENGTH_SHORT ).show();
                }
            }

        });

        cancelAlaramId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.cancel(pendingIntent);
                Intent intent=new Intent(MainActivity.this,AlarmSoundServices.class);
                stopService(intent);

                Toast.makeText(MainActivity.this,"Alarm stopped",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getCorrectInterval(int interval) {
        switch (radioGroupalaram.getId()){
            case R.id.secoundsId:
            {
                return interval;
            }
            case R.id.minutesId:
            {
                return interval*60;
            }
            case R.id.housrId:
            {
                return interval*60*60;
            }
            default:{
                return interval;
            }
        }
    }

}