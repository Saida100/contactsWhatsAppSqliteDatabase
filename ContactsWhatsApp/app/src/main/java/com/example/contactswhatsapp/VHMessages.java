package com.example.contactswhatsapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class VHMessages extends RecyclerView.ViewHolder {


  //  private TextView txtNameUser;
    private TextView txtUserMessage;

    public VHMessages(@NonNull View itemView) {
        super(itemView);
     //   txtNameUser = itemView.findViewById(R.id.name);
        txtUserMessage = itemView.findViewById(R.id.message);

    }

    public void bind(final String message, final Listener listener) {
   //     txtNameUser.setText(user.getUserName());
        txtUserMessage.setText(message);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(message);
            }
        });


    }
}
