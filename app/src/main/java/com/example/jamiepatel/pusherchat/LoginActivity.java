package com.example.jamiepatel.pusherchat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//



public class LoginActivity extends ActionBarActivity {

    EditText phonenumberInput;
    EditText usernameInput;
    public static String username;

    //Below is for the multiautocomplete view
    private ArrayList<Map<String, String>> mPeopleList;
    private SimpleAdapter mAdapter;
    private MultiAutoCompleteTextView mTxtPhoneNo;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private Cursor people;
    private Cursor phones;
    String original = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = (EditText) findViewById(R.id.username_input);
        //usernameInput.setOnKeyListener(this);
        phonenumberInput = (EditText) findViewById(R.id.phonenumber_input);
        //phonenumberInput.setOnKeyListener(this);

        mPeopleList = new ArrayList<Map<String, String>>();
        PopulatePeopleList();
        mTxtPhoneNo = (MultiAutoCompleteTextView) findViewById(R.id.mmWhoNo);

        mAdapter = new SimpleAdapter(this, mPeopleList, R.layout.custcontview ,new String[] { "Name", "Phone" , "Type" }, new int[] { R.id.ccontName, R.id.ccontNo, R.id.ccontType });

        mTxtPhoneNo.setAdapter(mAdapter);


        mTxtPhoneNo.setThreshold(1);

        mTxtPhoneNo.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        mTxtPhoneNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View arg1, int index,
                                    long arg3) {
                Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);

                String name  = map.get("Name");
                //String number = map.get("Phone");

                mTxtPhoneNo.setText(original + name + ", ");
                original = mTxtPhoneNo.getText().toString();
                int pos = mTxtPhoneNo.getText().toString().length();
                mTxtPhoneNo.setSelection(pos);
                //myTxtPhoneNo.getText().setSelection(position);

            }



        });




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
        this.username = username;



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
    public void PopulatePeopleList()
    {
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        */


        mPeopleList.clear();

        people = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (people.moveToNext())
        {
            String contactName = people.getString(people.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));

            String contactId = people.getString(people.getColumnIndex(
                    ContactsContract.Contacts._ID));
            String hasPhone = people.getString(people.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if ((Integer.parseInt(hasPhone) > 0))
            {

                // You know have the number so now query it like this
                phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                        null, null);
                while (phones.moveToNext()) {

                    //store numbers and display a dialog letting the user select which.
                    String phoneNumber = phones.getString(
                            phones.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                    String numberType = phones.getString(phones.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.TYPE));

                    Map<String, String> NamePhoneType = new HashMap<String, String>();

                    NamePhoneType.put("Name", contactName);
                    NamePhoneType.put("Phone", phoneNumber);



                    if(numberType.equals("0"))
                        NamePhoneType.put("Type", "Work");
                    else if(numberType.equals("1"))
                        NamePhoneType.put("Type", "Home");
                    else if(numberType.equals("2"))
                        NamePhoneType.put("Type",  "Mobile");
                    else
                        NamePhoneType.put("Type", "Other");


                    //Then add this map to the list.
                    mPeopleList.add(NamePhoneType);
                }
                phones.close();
            }
        }
        people.close();

        //startManagingCursor(people);
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                //showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
    */

    public void onDestroy() {
        super.onDestroy();
        if (people != null) {
            people.close();
        }
        if (phones != null) {
            phones.close();
        }
    }

    public void onStop() {
        super.onStop();
        if (people != null) {
            people.close();
        }
        if (phones != null) {
            phones.close();
        }
    }

}
