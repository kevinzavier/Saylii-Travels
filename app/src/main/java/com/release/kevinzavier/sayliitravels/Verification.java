package com.release.kevinzavier.sayliitravels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by kevin on 8/11/16.
 */
public class Verification extends Activity{
    EditText messageInput;
    EditText nameInput;
    private String myRandom;
    public static String myName = "";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        messageInput = (EditText) findViewById(R.id.message_input);
        nameInput = (EditText) findViewById(R.id.name_input);
        //RequestParams params = new RequestParams();
        //params.put("random", myRandom);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            myRandom = extras.getString("random");
        }
        //Bundle b = iin.getExtras;

        //verification so we can set onKeyListener for the name_input and the
        //verification_input EditTexts
        View.OnKeyListener verification = new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                //the next activity
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    //to see if the name edittext is filled in
                    boolean nameEmpty = nameInput.getText().toString().trim().isEmpty();
                    boolean verifyEmpty = messageInput.getText().toString().trim().isEmpty();
                    String name = nameInput.getText().toString();

                    if(nameEmpty){
                        Toast.makeText(Verification.this, "Please enter your name!", Toast.LENGTH_LONG).show();
                    }
                    else if(verifyEmpty){
                        Toast.makeText(Verification.this, "Please enter your verification number!", Toast.LENGTH_LONG).show();
                    }
                    //if the verification number is right
                    else if(myRandom.equals(messageInput.getText().toString())){
                        myName = name;
                        StartActivity.verified = true;
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                    //if verification number is not right
                    else{
                        Toast.makeText(Verification.this, "Wrong verification number please try again!", Toast.LENGTH_LONG).show();
                        myName = name;
                        messageInput.setText("");
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                    // Perform action on key press

                    return true;
                }
                return false;
            }
        };

        messageInput.setOnKeyListener(verification);
        nameInput.setOnKeyListener(verification);






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
