package com.example.do_an_cs3.Ardapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.DocumentsFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.ImportantFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.UpdateNewFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UpdateNewFragment();
            case 1:
                return new ImportantFragment();
            case 2:
                return new DocumentsFragment();
            default:
                return new UpdateNewFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "CẬP NHẬT MỚI";
                break;
            case 1:
                title = "QUAN TRỌNG";
                break;
            case 2:
                title = "TÀI LIỆU";
                break;
        }
        return title;
    }

}
