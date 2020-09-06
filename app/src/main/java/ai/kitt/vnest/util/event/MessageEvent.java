package ai.kitt.vnest.util.event;

import java.util.ArrayList;

import ai.kitt.vnest.basedata.entity.Contact;

public class MessageEvent {
    String contact = null;
    ArrayList<Contact> contacts;
    String message;

    public MessageEvent(Contact contact2, String str) {
        this.message = str;
        ArrayList<Contact> arrayList = new ArrayList<>();
        this.contacts = arrayList;
        arrayList.add(contact2);
    }

    public MessageEvent(ArrayList<Contact> arrayList, String str, String str2) {
        this.message = str2;
        this.contact = str;
        this.contacts = arrayList;
    }

    public ArrayList<Contact> getContacts() {
        return this.contacts;
    }

    public String getContact() {
        return this.contact;
    }

    public String getMessage() {
        return this.message;
    }
}
