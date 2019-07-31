package com.example.contactswhatsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdapterChats extends RecyclerView.Adapter<VHChats>{

    List<User> userList;
    Context context;
    Listener listener;

    public AdapterChats(List<User> userList, Context context, Listener listener) {
        this.userList = userList;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public VHChats onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_chats,viewGroup,false);
        return new VHChats(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHChats vhChats, int i) {
      vhChats.bind(userList.get(i),listener);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
