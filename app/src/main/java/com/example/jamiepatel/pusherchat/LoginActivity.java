package com.example.jamiepatel.pusherchat;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {

    EditText phonenumberInput;
    EditText usernameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = (EditText) findViewById(R.id.username_input);
        //usernameInput.setOnKeyListener(this);
        phonenumberInput = (EditText) findViewById(R.id.phonenumber_input);
        //phonenumberInput.setOnKeyListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        return super.onOptionsItemSelected(item);
    }


    public boolean onBottonClick(View v) {


        String username = usernameInput.getText().toString();
        String phonenumber = phonenumberInput.getText().toString();
        if(username.length() == 0 && phonenumber.length() < 10){
            Toast myToast = Toast.makeText(getApplicationContext(),
                    "Please enter a name and phone number", Toast.LENGTH_SHORT);
            myToast.show();
            return false;
        }
        if(username.length() == 0){
            Toast myToast = Toast.makeText(getApplicationContext(),
                    "Please enter a name", Toast.LENGTH_SHORT);
            myToast.show();
            return false;
        }
        if(phonenumber.length() < 10){
            Toast myToast = Toast.makeText(getApplicationContext(),
                    "Please enter a valid phone number", Toast.LENGTH_SHORT);
            myToast.show();
            return false;
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("phonenumber", phonenumber);
        startActivity(intent);
        return true;
    }
}
