package com.example.contactswhatsapp.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.contactswhatsapp.Listener;
import com.example.contactswhatsapp.R;
import com.example.contactswhatsapp.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class VHContacts extends RecyclerView.ViewHolder {


    private TextView userNameTextView;
    private TextView phoneNumberTextView;
    private CircleImageView profileImageView;

    public VHContacts(@NonNull View itemView) {
        super(itemView);
        userNameTextView = itemView.findViewById(R.id.name);
        phoneNumberTextView = itemView.findViewById(R.id.tel_number);
        profileImageView =itemView.findViewById(R.id.profilImage);

    }

    public void bind(final User user, final Listener listener) {
        userNameTextView.setText(user.getUserName());
        phoneNumberTextView.setText(user.getTelNumber());
        profileImageView.setImageResource(R.drawable.ic_face_black_24dp);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(user);
            }
        });


    }
}
