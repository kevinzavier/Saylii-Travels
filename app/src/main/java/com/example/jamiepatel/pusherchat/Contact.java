package com.example.jamiepatel.pusherchat;

/**
 * Created by kevin on 8/24/16.
 */
public class Contact {
    private String myname;
    private String myphone;

    public Contact(String name, String phone){
        myname = name;
        myphone = phone;

    }

    public String getName(){
        return myname;
    }
    public String getPhone(){
        return myphone;
    }
}
