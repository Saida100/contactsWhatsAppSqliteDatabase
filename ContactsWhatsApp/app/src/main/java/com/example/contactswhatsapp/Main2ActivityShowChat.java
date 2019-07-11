package com.example.contactswhatsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2ActivityShowChat extends AppCompatActivity {
    String name, number;
    String id;
    String text = " ";
    Toolbar toolbar;
    EditText edtMessage;
    Button btnSend;
    String userId;
    DBHelper dbHelper;
    Map<Integer, String> mapList;
    ;
    boolean check;
    boolean insertedUser;
    User user;
    List<User> userList;
    RecyclerView recyclerView;
    AdapterMessages adapterMessages;
    String lastSpoketime;
    List<String> messages;
    List<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_show_chat);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        toolbar.setTitleMarginStart(30);
        mapList = new HashMap<>();
        messages = new ArrayList<>();
        nameList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        dbHelper = new DBHelper(getApplicationContext());
        userList = new ArrayList<>();

        //    chatList = dbHelper.getAllUsers();
        edtMessage = findViewById(R.id.text);
        btnSend = findViewById(R.id.send);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("id");
        name = bundle.getString("name");
        Log.e("bundleName", name);
        Log.e("bundleUserId", userId);
        number = bundle.getString("number");
//        Log.e("bundleUserNumber", number);
        setRecyclerView();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEdtText = edtMessage.getText().toString();
                Log.e("getEditText", getEdtText);
                check = dbHelper.insertedText(name, getEdtText, Integer.parseInt(userId),R.drawable.ic_face_black_24dp,
                        lastSpoketime);
                Log.e("check", String.valueOf(check));
                setRecyclerView();
                edtMessage.setText("");
                userList=dbHelper.getAllUsers2();
                Log.e("nameListSize", String.valueOf(nameList.size()));

            }
        });


    }


    public void setRecyclerView() {
        messages = dbHelper.getAllMessagesByUserId(Integer.parseInt(userId));
        Log.e("sizeMessages", String.valueOf(messages.size()));
        Log.e("messageList", messages.toString());
        adapterMessages = new AdapterMessages(messages, getApplicationContext(), new Listener() {
            @Override
            public void onItemClick(User user) {
            }

            @Override
            public void onItemClick(String message) {
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterMessages);
    }
}
