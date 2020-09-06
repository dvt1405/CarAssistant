package ai.kitt.vnest.basedata.entity;

public class Contact {
    public String phoneNumber, contactName, email, contactId;

    public Contact(String contactId,String phoneNumber, String contactName, String email) {
        this.contactId = contactId;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.email = email;
    }
}
