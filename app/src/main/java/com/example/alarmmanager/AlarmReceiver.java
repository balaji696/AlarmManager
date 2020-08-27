package com.example.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"alaram fired in Broadcast receiver",Toast.LENGTH_SHORT).show();

        Intent intent1=new Intent(context,AlarmSoundServices.class);
        context.startService(intent1);
    }
}
