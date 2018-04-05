package com.example.lanfe.cb_learnsystem.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lanfe.cb_learnsystem.R;

/**
 * Created by lanfe on 2018/4/5.
 */

public class FragmentReview extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_content, null);
        //添加floating按钮的监听
        //floatingActionButtonListener();
        return view;
    }

    @Override
    public void onResume() {
        //从数据库更新页面
        //loadingView();
        super.onResume();
    }
}
