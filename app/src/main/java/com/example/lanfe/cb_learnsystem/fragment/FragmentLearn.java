package com.example.lanfe.cb_learnsystem.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.lanfe.cb_learnsystem.R;
import com.example.lanfe.cb_learnsystem.utils.FileUtil;
import com.example.lanfe.cb_learnsystem.utils.WordUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lanfe on 2018/4/5.
 */

public class FragmentLearn extends Fragment {

    private final String PATH = Environment.getExternalStorageDirectory().getPath() + "/learnSystem/";
    private View view;
    private FloatingActionButton collection_on;
    private FloatingActionButton collection_off;
    private Spinner spinner;
    private TextView index;
    private ViewPager viewpager;
    //doc文件路径数组
    private List<String> docPATH = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.learn_content, null);
        collection_on = view.findViewById(R.id.collection_on);
        collection_off = view.findViewById(R.id.collection_off);
        spinner = view.findViewById(R.id.TV_title);
        index = view.findViewById(R.id.TV_index);
        viewpager = view.findViewById(R.id.viewpager);

        //获取文件夹下的全部doc文件路径
        docPATH = FileUtil.GetDocFileName(PATH);
        //对路径数组排序
        docPATH.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1.substring(0, 2)) - Integer.parseInt(o2.substring(0, 2));
            }
        });

        //设置viewpager
        setViewpager();
        //设置spinner，标题下拉框
        setTitle();
        //设置收藏FAB监听
        setFAB();

        return view;
    }

    private void setFAB() {
        collection_on.setTag(1);
        collection_off.setTag(0);
        collection_off.setVisibility(View.VISIBLE);
        collection_on.setVisibility(View.INVISIBLE);
        FloatingActionButton.OnClickListener listener = new FloatingActionButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getTag().equals(1)) {
                    collection_off.setVisibility(View.VISIBLE);
                    collection_on.setVisibility(View.INVISIBLE);
                } else {

                    collection_off.setVisibility(View.INVISIBLE);
                    collection_on.setVisibility(View.VISIBLE);
                }
            }
        };
        collection_off.setOnClickListener(listener);
        collection_on.setOnClickListener(listener);
    }

    private void setTitle() {
        List<String> title = new ArrayList<>();
        for (String filepath : docPATH) {
            title.add(FileUtil.getFileName(PATH + filepath));
        }
        //System.out.println(title);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, title);

        //为适配器添加样式
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setViewpager() {
        //获取webViews
        final List<View> views = addView();
        //需要给ViewPager设置适配器
        PagerAdapter adapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            //有多少个切换页
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return views.size();
            }

            //对超出范围的资源进行销毁
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                //super.destroyItem(container, position, object);
                container.removeView(views.get(position));
            }

            //对显示的资源进行初始化
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                //return super.instantiateItem(container, position);
                container.addView(views.get(position));
                return views.get(position);
            }

        };
        viewpager.setAdapter(adapter);

        //给ViewPager添加事件监听
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                index.setText((arg0 + 1) + "/" + docPATH.size());
                spinner.setSelection(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private List<View> addView() {
        //填充viewpager的view数组
        List<View> views = new ArrayList<>();
        for (int i = 0; i < docPATH.size(); i++) {
            //System.out.println(docPATH.get(i));
            WordUtil wu = new WordUtil(PATH + docPATH.get(i));
            WebView webView = new WebView(view.getContext());
            WebSettings settings = webView.getSettings();

            settings.setUseWideViewPort(true); //将图片调整到适合webView的大小
            settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
            settings.setDefaultFontSize(24);
            webView.loadUrl("file:///" + wu.htmlPath);
            views.add(webView);
        }
        index.setText(1 + "/" + docPATH.size());
        return views;
    }


}
