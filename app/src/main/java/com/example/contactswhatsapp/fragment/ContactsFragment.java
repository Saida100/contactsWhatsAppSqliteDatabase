package com.example.contactswhatsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contactswhatsapp.DBHelper;
import com.example.contactswhatsapp.Listener;
import com.example.contactswhatsapp.activity.Main2ActivityShowChat;
import com.example.contactswhatsapp.R;
import com.example.contactswhatsapp.model.User;
import com.example.contactswhatsapp.adapter.AdapterContacts;

import java.util.ArrayList;
import java.util.List;
// how use permissions in fragment
// https://medium.com/programming-lite/runtime-permissions-in-android-7496a5f3de55
public class ContactsFragment extends Fragment {
    Listener listener;
    List<User> userList;
    RecyclerView recyclerView;
    AdapterContacts adapterContacts;
    public static final int REQUEST_READ_CONTACTS = 79;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("called " ,"onCreated");
        dbHelper=new DBHelper(getContext());
        userList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestLocationPermission();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview);


    }

    @Override
    public void onResume() {
        super.onResume();
        userList = dbHelper.getAllUsers2();
        adapterContacts = new AdapterContacts(userList, getContext(), new Listener() {
            @Override
            public void onItemClick(User user) {
                Log.e("Saida", String.format("username=%s number=%s", user.getUserName(), user.getTelNumber()));
                Intent intent = new Intent(getContext(), Main2ActivityShowChat.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(user.getId()));
                bundle.putString("name", user.getUserName());
                bundle.putString("number", user.getTelNumber());
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterContacts);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;
        Log.e("called " ,"onAttached");

    }


    protected void requestLocationPermission() {
        Log.e("called","requestLocationPermission");

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!

        } else {
            requestPermissions( new String[]{android.Manifest.permission.READ_CONTACTS,},
                    REQUEST_READ_CONTACTS);

        }

    }
//
    @Override
      public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact1();

                } else {

                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    public void getContact1() {
        Log.e("called","getContact1");
        int countUser=0;
        Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while (phones.moveToNext()) {

            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID));
            Log.e("contact",id+" "+name+" "+phoneNumber);
            User user = new User();
            user.setUserName(name);
            user.setTelNumber(phoneNumber);
            user.setId(Integer.parseInt(id));
            user.setProfileImage(R.drawable.ic_face_black_24dp);
            dbHelper.insertedUser(user);
            boolean isAddedUser = dbHelper.insertedUser(user);
            Log.e("isAddedUser", String.valueOf(isAddedUser));
            countUser++;
        }
        Log.e("calledGetContact", String.valueOf(countUser));

        phones.close();
    }

    public void setUserData(String name, String phoneNumber, String id) {
        User user = new User();
        user.setUserName(name);
        user.setTelNumber(phoneNumber);
        user.setId(Integer.parseInt(id));
        user.setProfileImage(R.drawable.ic_face_black_24dp);

    }

}

