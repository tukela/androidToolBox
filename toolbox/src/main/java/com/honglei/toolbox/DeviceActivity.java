package com.honglei.toolbox;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by happy_000 on 2014/12/5.
 */
public class DeviceActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();
        String tel = tm.getLine1Number();//ÊÖ»úºÅÂë
        String imei = tm.getSimSerialNumber();
        String imsi = tm.getSubscriberId();
        Cursor myCursor = getContentResolver().query(Uri.parse("content://sms"),
                new String[]{/*"msg_id", "contact_id", */
                        "(select address from addr where type = 151) as address"},
                null,
                null, "date desc");
        String myPhoneNumber = "null";
        if (myCursor != null) {
            myCursor.moveToFirst();
            Log.d("number", "number=" + myCursor.getString(myCursor.getColumnIndex("address")));
            myPhoneNumber = myCursor.getString(myCursor.getColumnIndex("address"));
        }

        StringBuffer text = new StringBuffer(deviceid).append("\n")
                .append(tel).append("\n")
                .append(imei).append("\n")
                .append(imsi).append("\n")
                .append(myPhoneNumber);
       TextView textView= new TextView(this);
        textView.setText(text.toString());
        setContentView(textView);

         }
    }

