package com.example.jamiepatel.pusherchat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 8/24/16.
 */

public class ContactActivity extends Activity{

    EditText name;
    EditText phone;
    Button add;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;
    DatabaseHandler dbHandler;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        name = (EditText) findViewById(R.id.Name);
        phone = (EditText) findViewById(R.id.Phone);
        add = (Button) findViewById(R.id.addContact);
        dbHandler = new DatabaseHandler(getApplicationContext());
        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Contact contact = new Contact(dbHandler.getContactsCount(), name.getText().toString(), phone.getText().toString());
                dbHandler.createContact(contact);
                Contacts.add(contact);
                //addContact(0, name.getText().toString(), phone.getText().toString());
                populateList();
                Toast.makeText(ContactActivity.this, name.getText().toString() + " has been added", Toast.LENGTH_LONG).show();
            }
        });
        contactListView = (ListView) findViewById(R.id.listView);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.creatorTab);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.listTab);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                add.setEnabled(!name.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        List<Contact> addableContacts = dbHandler.getAllContacts();
        int contactCount = dbHandler.getContactsCount();

        for(int i = 0; i < contactCount; i++){
            Contacts.add(addableContacts.get(i));
        }

        if(!addableContacts.isEmpty()){
            populateList();
        }
    }

    private void addContact(int id, String name, String phone) {
        Contacts.add(new Contact(id, name,phone));
    }

    private void populateList(){
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }


    private class ContactListAdapter extends ArrayAdapter<Contact>{
        public ContactListAdapter(){
            super(ContactActivity.this, R.layout.listview_item, Contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null){
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
            }

            Contact currentContact = Contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.getName());
            TextView phone = (TextView) view.findViewById(R.id.phone);
            phone.setText(currentContact.getPhone());

            return view;

        }
    }


}
