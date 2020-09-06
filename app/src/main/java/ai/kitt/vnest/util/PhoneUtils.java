package ai.kitt.vnest.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import ai.kitt.vnest.basedata.entity.Contact;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class PhoneUtils {
    static final String LOG_TAG = "PhoneContact";
    static final String NO_CONTACT = "No contact found!";
    static ArrayList<Contact> listContact = new ArrayList<>();

    public static Contact findContact(Context context, String name, OnFindListener onFindListener) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.DISPLAY_NAME};

        String selection = ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME.toLowerCase() + " LIKE ?";
        String[] selectionArguments = {name};
        try (Cursor cursor = contentResolver.query(uri, projection, null, null, null)) {
            if (cursor != null) {
                listContact.clear();
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).toLowerCase();
                    Log.e("name",displayName);
                    listContact.add(new Contact(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)),
                            null, displayName, null));
                    if (displayName.contains(name.toLowerCase())) {
                        return getContactNumber(context, cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)));
                    }
                }
            } else {
                Log.e(LOG_TAG, NO_CONTACT);
            }
        } catch (Exception e) {
            onFindListener.onError(e);
        }
        for (Contact contact : listContact) {
            if (deAccent(contact.contactName).toLowerCase().contains(deAccent(name)))
                return getContactNumber(context, contact.contactId);
        }
        return null;
    }

    public static void callToContact(Context context, String name, OnCallListener onCallListener) {
        Contact contact = findContact(context, name, ex -> {
            onCallListener.onError(ex);
        });
        if (contact != null && contact.phoneNumber != null) {
            callToContact(context, contact);
            onCallListener.onSuccess();
        } else {
            onCallListener.onError(new NullPointerException(NO_CONTACT));
        }
    }

    public static void dialToContact(Context context, Contact contact) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contact.phoneNumber));
        context.startActivity(intent);
    }

    public static void callToContact(Context context, Contact contact) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + contact.phoneNumber));
        context.startActivity(intent);
    }

    private static Contact getContactNumber(Context context, String contactID) {
        String contactNumber = null;
        Log.e(LOG_TAG, "Contact ID: " + contactID);
        String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
//        AND " +
//                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
//                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        Cursor cursorPhone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                selection,
                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
        cursorPhone.close();
        Log.e(LOG_TAG, "Contact Phone Number: " + contactNumber);
        return new Contact(contactID, contactNumber, null, null);
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }



    interface OnFindListener {
        void onError(Exception ex);
    }

    public interface OnCallListener {
        void onSuccess();

        void onError(Exception ex);
    }
}
