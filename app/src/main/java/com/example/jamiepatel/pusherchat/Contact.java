package com.example.jamiepatel.pusherchat;

/**
 * Created by kevin on 8/24/16.
 */
public class Contact {
    private String myname;
    private String myphone;
    private int myid;

    public Contact(int id, String name, String phone){
        myid = id;
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
