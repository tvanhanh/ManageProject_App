package com.example.do_an_cs3.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.do_an_cs3.View.back_end.View_fragment.FragmentJob.CompleteFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentJob.NewjobFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentJob.PauseFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentJob.PendingJobFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentJob.ProcessingJobFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.UpdateNewFragment;

public class ViewPagerAdapterJob extends FragmentPagerAdapter {
    public ViewPagerAdapterJob(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewjobFragment();
            case 1:
                return new PendingJobFragment();
            case 2:
                return new ProcessingJobFragment();
            case 3:
                return new CompleteFragment();
            case 4:
                return new PauseFragment();

            default:
                return new UpdateNewFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "VIỆC MỚI";
                break;
            case 1:
                title = "ĐANG THỰC HIỆN";
                break;
            case 2:
                title = "CHỜ DUYỆT";
                break;
            case 3:
                title = "HOÀN THÀNH";
                break;
            case 4:
                title = "TẠM DỪNG";
                break;
        }
        return title;
    }
}
