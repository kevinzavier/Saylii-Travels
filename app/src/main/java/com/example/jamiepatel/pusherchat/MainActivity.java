package com.example.jamiepatel.pusherchat;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends ActionBarActivity implements View.OnKeyListener, View.OnClickListener {
    public static String SMSRECEVID="custom.action.SMSRECEVEDINFO";
    final String MESSAGES_ENDPOINT = "http://pusher-chat-demo.herokuapp.com";

    private ArrayList<String> phonenumbers = new ArrayList<String>();
    private ArrayList<String> names = new ArrayList<String>();
    MessageAdapter messageAdapter;
    EditText messageInput;
    Button sendButton;

    SmsReceiver myReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //the arraylist of String
        phonenumbers = this.getIntent().getExtras().getStringArrayList("phonenumbers");
        //the arraylist of names
        names = this.getIntent().getExtras().getStringArrayList("names");

        //to print out the phone numbers and names
        for(String x: phonenumbers){
            Log.i("phonenumbers", x);
        }
        for(String x: names){
            Log.i("names", x);
        }



        Toast.makeText(this, "Welcome, " + Verification.myName + "!", Toast.LENGTH_LONG).show();

        messageInput = (EditText) findViewById(R.id.message_input);
        messageInput.setOnKeyListener(this);

        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);

        messageAdapter = new MessageAdapter(this, new ArrayList<Message>());
        final ListView messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        Pusher pusher = new Pusher("faa685e4bb3003eb825c");

        pusher.connect();

        Channel channel = pusher.subscribe("messages");

        channel.bind("new_message", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Message message = gson.fromJson(data, Message.class);
                        messageAdapter.add(message);
                        messagesView.setSelection(messageAdapter.getCount() - 1);
                    }

                });
            }

        });

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.addAction("android.provider.Telephony.NEW_OUTGOING_SMS");
        filter.addCategory("com.example.jamiepatel.pusherchat");
        this.registerReceiver(new SmsReceiver(), filter, "android.permission.BROADCAST_SMS", null);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.add_user) {
            return true;
        }
        if (id == R.id.to_map) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
            postMessage();
        }

        return true;

    }


    public void postMessage()  {
        String text = messageInput.getText().toString();

        if (text.equals("")) {
            return;
        }

        RequestParams params = new RequestParams();

        params.put("text", text);
        //so the login activity name is useless
        params.put("name", Verification.myName);
        //TODO made a change here
        //params.put("thumbnail", "http://i.imgur.com/Tny2C2o.png");
        params.put("time", new Date().getTime());

        AsyncHttpClient client = new AsyncHttpClient();

        SmsManager smsManager = SmsManager.getDefault();
        for(int i = 0; i < phonenumbers.size(); i++) {
            smsManager.sendTextMessage(phonenumbers.get(i), null, "saylii: " + text, null, null);
            Log.i("PHONE NUMBER",phonenumbers.get(i));
        }

        //PendingIntent sendPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SEND), 0);




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

    }

    //this one is for receiving messages
    public void postMessage(String name, String body)  {

        RequestParams params = new RequestParams();

        params.put("text", body);
        params.put("name", name);
        params.put("time", new Date().getTime());

        AsyncHttpClient client = new AsyncHttpClient();
        //Log.i("USERNAME", name);
        //Log.i("BODY", body);


        /*
        Log.i("README", phonenumber);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phonenumber, null,"FROM TRAV CHAT: " + text, null, null);
        */

        //PendingIntent sendPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SEND), 0);




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

    }

    @Override
    public void onClick(View v) {
        postMessage();
    }
/*
    SmsReceiver mySmsReceiver = new SmsReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("WE ARE GOOD", "yay");
        }
    };
    */


      public class SmsReceiver extends BroadcastReceiver {

        public SmsReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.i("LISTEING", "ITS GOOD");

            //postMessage();
            //---get the SMS message passed in---
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String messageReceived = "";
            if (bundle != null) {
                //---retrieve the SMS message received---
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                String received = "";
                for (int i = 0; i < msgs.length; i++)

                {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    received = msgs[i].getOriginatingAddress();
                    received = received.substring(received.length() - 4);
                    Log.i("received", received);

                    //Check if the message received is from our conversation
                    //THIS IS THE VERIFICATION
                    String name = "";
                    boolean post = false;
                    for(int j = 0; j < phonenumbers.size();j++){
                        if(phonenumbers.get(j).contains(received)){
                            post = true;
                            name = names.get(j);
                            break;
                        }
                    }


                    if(post) {
                        postMessage(name, msgs[i].getMessageBody().toString());
                    }
                }
            }

            //Toast.makeText(context, messageReceived, Toast.LENGTH_SHORT).show();
        }


    }
};

