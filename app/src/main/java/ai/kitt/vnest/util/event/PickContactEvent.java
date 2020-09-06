package ai.kitt.vnest.util.event;

import java.util.List;

import ai.kitt.vnest.basedata.entity.Contact;

public class PickContactEvent {
    int action;
    List<Contact> contacts;

    public PickContactEvent(List<Contact> list, int i) {
        this.contacts = list;
        this.action = i;
    }

    public int getAction() {
        return this.action;
    }

    public List<Contact> getContacts() {
        return this.contacts;
    }
}
