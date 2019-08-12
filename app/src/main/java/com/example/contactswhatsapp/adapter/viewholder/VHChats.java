package com.example.contactswhatsapp.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.contactswhatsapp.Listener;
import com.example.contactswhatsapp.R;
import com.example.contactswhatsapp.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class VHChats extends RecyclerView.ViewHolder {


    private TextView userNameTextView;
    private CircleImageView profileUserImageView;

    public VHChats(@NonNull View itemView) {
        super(itemView);
        userNameTextView = itemView.findViewById(R.id.name);
        profileUserImageView =itemView.findViewById(R.id.profilImage);

    }

    public void bind(final User user, final Listener listener) {
        userNameTextView.setText(user.getUserName());
        profileUserImageView.setImageResource(R.drawable.ic_face_black_24dp);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(user);
            }
        });


    }
}
