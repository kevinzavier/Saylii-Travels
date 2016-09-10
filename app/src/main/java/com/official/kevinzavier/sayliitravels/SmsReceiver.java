/*package com.example.jamiepatel.pusherchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kevin on 7/11/16.
 */
/*
public class SmsReceiver extends BroadcastReceiver {
    private String TAG = SmsReceiver.class.getSimpleName();
    EditText messageInput;



    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("ok", "cmon");
        intent = new Intent();
        intent.setAction(MainActivity.SMSRECEVID);
        context.sendBroadcast(intent);

        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String messageReceived = "";
        if (bundle != null) {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++)

            {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                messageReceived += msgs[i].getOriginatingAddress() + " : ";
                messageReceived += msgs[i].getMessageBody().toString();
                messageReceived += "\n";

                //start(intent);
            }
        }
/*
        RequestParams params = new RequestParams();

        params.put("text", text);
        params.put("name", username);
        params.put("time", new Date().getTime());

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(MESSAGES_ENDPOINT + "/messages", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageInput.setText("");
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
            }
        });

        Toast.makeText(context, messageReceived, Toast.LENGTH_SHORT).show();
        /*
        // Get the data (SMS data) bound to intent
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;

        String str = "";

        if (bundle != null) {
            // Retrieve the SMS Messages received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            // For every SMS message received
            for (int i=0; i < msgs.length; i++) {

                // Convert Object array
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                // Sender's phone number
                str += "SMS from " + msgs[i].getOriginatingAddress() + " : ";
                // Fetch the text message
                str += msgs[i].getMessageBody().toString();
                // Newline <img draggable="false" class="emoji" alt="🙂" src="https://s.w.org/images/core/emoji/72x72/1f642.png">
                str += "\n";
            }

            // Display the entire SMS Message
            Log.d("HERE!!!!!!!!!!!!!!!!!!!", str);

        }

    }
}
*/