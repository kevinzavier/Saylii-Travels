package com.example.jamiepatel.pusherchat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    ArrayAdapter<Contact> contentAdapter;
    int longClickedItemIndex;


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
                if(!contactExists(contact)) {
                    dbHandler.createContact(contact);
                    Contacts.add(contact);
                    contentAdapter.notifyDataSetChanged();
                    Toast.makeText(ContactActivity.this, name.getText().toString() + " has been added", Toast.LENGTH_LONG).show();
                    name.setText("");
                    phone.setText("");
                    return;
                }
                Toast.makeText(ContactActivity.this, name.getText().toString() + " already exists. Please use a different name", Toast.LENGTH_LONG).show();


            }
        });
        contactListView = (ListView) findViewById(R.id.listView);

        registerForContextMenu(contactListView);
        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });

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
                //instead of toString maybe use String.valueOf
                add.setEnabled(!name.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(dbHandler.getContactsCount()!=0){
            Contacts.addAll(dbHandler.getAllContacts());
        }

        populateList();

    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);

        //menu.add();
    }

    private boolean contactExists(Contact contact){
        String name = contact.getName();
        int contactCount = Contacts.size();
        for(int i = 0; i < contactCount; i++){
            if(name.compareToIgnoreCase(Contacts.get(i).getName()) == 0){
                return true;
            }
        }
        return false;
    }

    private void addContact(int id, String name, String phone) {
        Contacts.add(new Contact(id, name,phone));
    }

    private void populateList(){
        contentAdapter = new ContactListAdapter();
        contactListView.setAdapter(contentAdapter);
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
