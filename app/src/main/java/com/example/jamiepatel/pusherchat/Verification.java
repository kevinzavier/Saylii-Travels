package com.example.jamiepatel.pusherchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.loopj.android.http.RequestParams;

/**
 * Created by kevin on 8/11/16.
 */
public class Verification extends Activity{
    EditText messageInput;
    private String myRandom;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        messageInput = (EditText) findViewById(R.id.message_input);
        //RequestParams params = new RequestParams();
        //params.put("random", myRandom);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            myRandom = extras.getString("random");
        }
        //Bundle b = iin.getExtras;

        messageInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if(myRandom.equals(messageInput.getText().toString())){
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Verification.this, "Wrong verification number please try again!", Toast.LENGTH_LONG).show();
                        messageInput.setText("");
                        startActivity(intent);
                    }
                    // Perform action on key press

                    return true;
                }
                return false;
            }
        });


    }
/*
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.i("", "WE MADE IT");

        if (keyCode == KeyEvent.KEYCODE_ENTER){
            if(myRandom.equals(messageInput.getText().toString())){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Wrong verification number please try again!", Toast.LENGTH_LONG).show();
                messageInput.setText("");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        }

        return true;

    }
    */
}
