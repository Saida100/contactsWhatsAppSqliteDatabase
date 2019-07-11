package com.example.contactswhatsapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class VHContacts extends RecyclerView.ViewHolder {


    private TextView txtNameUser;
    private TextView txtTelNumber;
    private CircleImageView image;

    public VHContacts(@NonNull View itemView) {
        super(itemView);
        txtNameUser = itemView.findViewById(R.id.name);
        txtTelNumber = itemView.findViewById(R.id.tel_number);
        image=itemView.findViewById(R.id.profilImage);

    }

    public void bind(final User user, final Listener listener) {
        txtNameUser.setText(user.getUserName());
        txtTelNumber.setText(user.getTelNumber());
        image.setImageResource(user.getProfileImage());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(user);
            }
        });


    }
}
