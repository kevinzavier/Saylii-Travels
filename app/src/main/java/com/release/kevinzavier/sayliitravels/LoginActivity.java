package com.release.kevinzavier.sayliitravels;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//



public class LoginActivity extends ActionBarActivity {





    //Below is for the multiautocomplete view
    private ArrayList<String> phonenumbers = new ArrayList<String>();
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Map<String, String>> mPeopleList;
    private SimpleAdapter mAdapter;
    private MultiAutoCompleteTextView mTxtPhoneNo;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private Cursor people;
    private Cursor phones;
    private String original = "";
    private boolean count = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTxtPhoneNo = (MultiAutoCompleteTextView) findViewById(R.id.mmWhoNo);

        mPeopleList = new ArrayList<Map<String, String>>();
        PopulatePeopleList();


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
                String number = map.get("Phone");

                //if()
                phonenumbers.add(number);
                names.add(name);

                if(count){

                    original = mTxtPhoneNo.getText().toString();
                    original = original.substring(0,original.lastIndexOf("{"));
                    //Log.i("THIS IS IMPORTANT", original);
                }
                count = true;
                mTxtPhoneNo.setText(original + name + ", ");
                //original = mTxtPhoneNo.getText().toString();
                int pos = mTxtPhoneNo.getText().toString().length();
                mTxtPhoneNo.setSelection(pos);


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






        if(phonenumbers.size() ==0){
            Toast myToast = Toast.makeText(getApplicationContext(),
                    "Please create a group", Toast.LENGTH_SHORT);
            myToast.show();
            return false;
        }


        String final_names = mTxtPhoneNo.getText().toString();
        String[] result = final_names.split(", ");
        ArrayList<String> result_list = new ArrayList<String>(Arrays.asList(result));
        for(int i = 0; i < result_list.size(); i++){
            Log.i("NAMES", result_list.get(i));
        }

        //checking if user deleted any contacts before the button click
        for(int i = names.size() - 1; i >= 0; --i){
            if(!(result_list.contains(names.get(i)))){
                phonenumbers.remove(i);
                names.remove(i);
            }
        }



        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        String phonenumber = "";
        String name = "";
        for(String x: phonenumbers){
            phonenumber += x + "///";
        }
        for(String x: names){
            name += x + "///";
        }

        //TODO make sure to fix these tomorrow
        Log.i("PHONE NUMBERS", phonenumber);

        intent.putExtra("phonenumber", phonenumber);
        intent.putExtra("phonenumbers", phonenumbers);
        intent.putExtra("names", names);
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
                    else if(numberType.equals("2")) {
                        NamePhoneType.put("Type", "Mobile");
                        mPeopleList.add(NamePhoneType);
                    }
                    else
                        NamePhoneType.put("Type", "Other");


                    //Then add this map to the list.
                    //mPeopleList.add(NamePhoneType);
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
