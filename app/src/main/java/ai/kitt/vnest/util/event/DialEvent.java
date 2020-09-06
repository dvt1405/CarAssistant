package ai.kitt.vnest.util.event;


import java.util.ArrayList;

import ai.kitt.vnest.App;
import ai.kitt.vnest.basedata.entity.Contact;

public class DialEvent {
    private App broker;
    private ArrayList<Contact> contacts;
    private String keyword;

    public DialEvent(Contact contact) {
        ArrayList<Contact> arrayList = new ArrayList<>();
        this.contacts = arrayList;
        arrayList.add(contact);
        this.keyword = null;
        this.broker = null;
    }

    public DialEvent(Contact contact, String str) {
        ArrayList<Contact> arrayList = new ArrayList<>();
        this.contacts = arrayList;
        arrayList.add(contact);
        this.keyword = str;
        this.broker = null;
    }

    public DialEvent(ArrayList<Contact> arrayList, String str) {
        this.contacts = arrayList;
        this.keyword = str;
        this.broker = null;
    }

    public App getBroker() {
        return this.broker;
    }

    public void setBroker(App app) {
        this.broker = app;
    }

    public ArrayList<Contact> getContactList() {
        return this.contacts;
    }

    public String getKeyword() {
        return this.keyword;
    }
}
