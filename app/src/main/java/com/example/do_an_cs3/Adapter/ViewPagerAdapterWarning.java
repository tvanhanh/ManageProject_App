package com.example.do_an_cs3.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.do_an_cs3.View.back_end.View_fragment.FragmentNotification.ExpiringSoonFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentNotification.BeInvitedFragment;

public class ViewPagerAdapterWarning extends FragmentPagerAdapter {
    public ViewPagerAdapterWarning(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BeInvitedFragment();
            case 1:
                return new ExpiringSoonFragment();
            default:
                return new ExpiringSoonFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "THÔNG BÁO CHUNG";
                break;
            case 1:
                title = "SẮP HẾT HẠN";
                break;
        }
        return title;
    }
}
