package com.example.contactswhatsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdapterMessages extends RecyclerView.Adapter<VHMessages>{

    List<String> messages;
    Context context;
    Listener listener;

    public AdapterMessages(List<String> messages, Context context, Listener listener) {
        this.messages = messages;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public VHMessages onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_messages,viewGroup,false);
        return new VHMessages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHMessages vhMessages, int i) {
      vhMessages.bind(messages.get(i),listener);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
