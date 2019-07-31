package com.example.contactswhatsapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class VHChats extends RecyclerView.ViewHolder {


    private TextView txtNameUser;
    private CircleImageView image;
    private TextView txtMessage;
    private TextView txtlastSpokeTime;
  //  private TextView txtUserMessage;

    public VHChats(@NonNull View itemView) {
        super(itemView);
        txtNameUser = itemView.findViewById(R.id.name);
        txtMessage = itemView.findViewById(R.id.message);
        txtlastSpokeTime = itemView.findViewById(R.id.lastSpokeTime);
        image=itemView.findViewById(R.id.profilImage);

    }

    public void bind(final User user, final Listener listener) {
        txtNameUser.setText(user.getUserName());
       // txtMessage.setText(user.getMessage());
        //txtlastSpokeTime.setText(user.getLastSpokeTime());
        image.setImageResource(user.getProfileImage());
        Log.e("vhChatUserId", String.valueOf(user.getId()));

        //    txtUserMessage.setText(user.getText());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(user);
            }
        });


    }
}
