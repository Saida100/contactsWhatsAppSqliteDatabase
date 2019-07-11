package com.example.contactswhatsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdapterContacts  extends RecyclerView.Adapter<VHContacts>{

    List<User> userList;
    Context context;
    Listener listener;

    public AdapterContacts(List<User> userList, Context context,Listener listener) {
        this.userList = userList;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public VHContacts onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_contacts,viewGroup,false);
        return new VHContacts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHContacts vhContacts, int i) {
      vhContacts.bind(userList.get(i),listener);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
