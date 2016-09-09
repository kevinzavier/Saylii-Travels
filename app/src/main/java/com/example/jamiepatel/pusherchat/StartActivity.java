package com.example.jamiepatel.pusherchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

/**
 * Created by kevin on 8/10/16.
 */
public class StartActivity extends Activity {
    Button start;
    public static boolean verified = false;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        start = (Button) findViewById(R.id.button2);
        start.setBackgroundResource(R.drawable.ic_button_getgoing);

        SharedPreferences sharedPref = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        verified = sharedPref.getBoolean("verification", verified);
        verified = true;


        //sharedPref = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putBoolean("verification", verified);
        //editor.apply();
    }

    public void onBottonTap(View v){

        SmsManager smsManager = SmsManager.getDefault();
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        int randomNum = (int)(100000 * Math.random());
        String random = String.valueOf(randomNum);

        smsManager.sendTextMessage(number, null, random, null, null);
        Intent intent = new Intent(getApplicationContext(), Verification.class);

        intent.putExtra("random", random);
        startActivity(intent);
        finish();
    }
}
