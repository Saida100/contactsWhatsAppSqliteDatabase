package com.example.contactswhatsapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.contactswhatsapp.fragment.ChatsFragment;
import com.example.contactswhatsapp.fragment.ContactsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ContactsFragment();
            default:
                return new ChatsFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Contacts";
            default:
                return "Chats";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
