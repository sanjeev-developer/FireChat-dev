package com.firechat.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.firechat.R;

public class FetchContact extends AppCompatActivity {

    private static final String TAG = "FetchContact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_contact);
        long start = System.currentTimeMillis();

        Log.e(TAG, "onCreate: start : " + start);
        ContentResolver cr = getContentResolver();
        int i = 0;
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String name, number;
                while (cursor.moveToNext()) {
                    i++;
                    name = cursor.getString(nameIndex);
                    number = cursor.getString(numberIndex);

                    System.out.println("name = " + name + " nuber " + number + " --- " + i);
                }
            } finally {
                cursor.close();
            }

            Log.e(TAG, "onCreate: end : " + (System.currentTimeMillis() - start));
        }
    }

    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };
}
