package com.csed.csedsemester2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by amart on 10/04/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.csed.csedsemester2";

    @Override
    public void onReceive(Context context, Intent intent){
        Intent i = new Intent(context, NotificationService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
