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
    RecyclerView recyclerView;
    AdapterChats adapterChats;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }



    @Override
    public void onResume() {
        super.onResume();
        chatList = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
        chatList = dbHelper.getAllUsersByReceiverId();
        adapterChats = new AdapterChats(chatList, getContext(), new Listener() {
            @Override
            public void onItemClick(User user) {
                Log.e("Saida", String.format("size=%s ", chatList.size()));
                Intent intent = new Intent(getContext(), Main2ActivityShowChat.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(user.getId()));
                bundle.putString("name", user.getUserName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterChats);

    }
}
