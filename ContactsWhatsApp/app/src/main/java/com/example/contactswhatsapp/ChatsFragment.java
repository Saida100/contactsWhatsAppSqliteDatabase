package com.example.contactswhatsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatsFragment extends Fragment {
    Listener listener;
    List<User> chatList;
    List<String> chatList2;
    RecyclerView recyclerView;
    AdapterChats adapterChats;
    User user = new User();
    DBHelper dbHelper;
    Map<Integer, String> mapList = new HashMap<>();
    String name, message;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("log", "onCreate called");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("log", "onCreateView called");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("log", "onVieCreated called");
        recyclerView = view.findViewById(R.id.recyclerview);
        dbHelper = new DBHelper(getContext());


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("log", "onAttach called");
        listener = (Listener) context;

    }



    @Override
    public void onResume() {
        super.onResume();
        Log.e("log", "onResume called");
        chatList = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
        chatList = dbHelper.getAllUsers2();

        //  chatList2 = dbHelper.getAllUsers();
        Log.e("chatlistSize", String.valueOf(chatList.size()));
        adapterChats = new AdapterChats(chatList, getContext(), new Listener() {
            @Override
            public void onItemClick(User user) {
                Log.e("Saida", String.format("size=%s ", chatList.size()));
                Log.e("Saida", String.format("name=%s ", user.getUserName()));
                Log.e("Saida", String.format("id=%s ", user.getId()));
                Log.e("Saida", String.format("message=%s ", user.getMessage()));
                Intent intent = new Intent(getContext(), Main2ActivityShowChat.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(user.getId()));
                bundle.putString("name", user.getUserName());
                bundle.putString("message", user.getMessage());
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onItemClick(String message) {

            }


        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterChats);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("log", "onPause called");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("log", "onStop called");

    }
}
