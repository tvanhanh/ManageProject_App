package com.example.do_an_cs3.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.do_an_cs3.View.Departments.ListDeparmentFragment;
import com.example.do_an_cs3.FragmentPersonnal.ListPersonnalFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.UpdateNewFragment;

public class ViewPagerAdapterDepartment extends FragmentPagerAdapter {
    private boolean isListDepartment;
    private Context context;

    public ViewPagerAdapterDepartment(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment, Context context) {
        super(fm);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        isListDepartment = sharedPreferences.getBoolean("isListDepartment", false);
        this.context = context;
    }

    public ViewPagerAdapterDepartment(@NonNull FragmentManager fm) {
        super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (isListDepartment) {
            switch (position) {
                case 0:
                    return new ListPersonnalFragment(); // Nếu đang ở chế độ danh sách bộ phận, trả về fragment danh sách nhân sự
                case 1:
                    return new ListDeparmentFragment(); // Nếu đang ở chế độ danh sách bộ phận, trả về fragment danh sách bộ phận
                default:
                    return new ListPersonnalFragment();
            }
        } else {
            switch (position) {
                case 0:
                    return new ListDeparmentFragment(); // Nếu đang ở chế độ danh sách nhân sự, trả về fragment danh sách bộ phận
                case 1:
                    return new ListPersonnalFragment(); // Nếu đang ở chế độ danh sách nhân sự, trả về fragment danh sách nhân sự
                default:
                    return new ListDeparmentFragment();
            }
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (isListDepartment) {
            switch (position) {
                case 0:
                    return "NHÂN SỰ";
                case 1:
                    return "CÁC BỘ PHẬN";
            }
        } else {
            switch (position) {
                case 0:
                    return "CÁC BỘ PHẬN";
                case 1:
                    return "NHÂN SỰ";
            }
        }
        return null;
    }
}
