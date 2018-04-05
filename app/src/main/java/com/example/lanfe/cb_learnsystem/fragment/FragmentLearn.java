package com.example.lanfe.cb_learnsystem.fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lanfe.cb_learnsystem.R;

/**
 * Created by lanfe on 2018/4/5.
 */

public class FragmentLearn extends Fragment {
    //    // 查询列表
//    private ListView translateList;
//
//    private TranslateAdapter adapter;
//
//    private List<TranslateData> list = new ArrayList<TranslateData>();
//    private List<Translate> trslist = new ArrayList<Translate>();
//
//
//    private ProgressDialog progressDialog = null;
//
//    private Handler waitHandler = new Handler();
//
//    private EditText fanyiInputText;
//
//    private InputMethodManager imm;
//
//    private TextView fanyiBtn;
    private View view;
//
//    TextView languageSelectFrom;
//
//    TextView languageSelectTo;
//
//    private Translator translator;
//    Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.learn_content, null);
//
//        fanyiInputText = (EditText) view.findViewById(R.id.fanyiInputText);
//        fanyiBtn = (TextView) view.findViewById(R.id.fanyiBtn);
//        translateList = (ListView) view.findViewById(R.id.commentList);
//        imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
//        adapter = new TranslateAdapter(view.getContext(), list, trslist);
//        translateList.setAdapter(adapter);
//        fanyiBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                query();
//            }
//        });
//
//        languageSelectFrom = (TextView) view.findViewById(R.id.languageSelectFrom);
//        languageSelectTo = (TextView) view.findViewById(R.id.languageSelectTo);
        return view;
    }

//    private void query() {
//        showLoadingView("正在查询");
//
//        // 源语言或者目标语言其中之一必须为中文,目前只支持中文与其他几个语种的互译
//        //String from = languageSelectFrom.getText().toString();
//        //String to = languageSelectTo.getText().toString();
//        final String input = fanyiInputText.getText().toString();
//        Language langFrom;
//        Language langTo;
//        if (isContainChinese(input)) {
//            langFrom = LanguageUtils.getLangByName("中文");
//            // 若设置为自动，则查询自动识别源语言，自动识别不能保证完全正确，最好传源语言类型
//            // Language langFrom = LanguageUtils.getLangByName("自动");
//            langTo = LanguageUtils.getLangByName("英文");
//        } else {
//            langFrom = LanguageUtils.getLangByName("英文");
//            langTo = LanguageUtils.getLangByName("中文");
//        }
//
//        TranslateParameters tps = new TranslateParameters.Builder()
//                .source("youdao").from(langFrom).to(langTo).timeout(3000).build();// appkey可以省略
//
//        translator = Translator.getInstance(tps);
//        translator.lookup(input, "requestId", new TranslateListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onResult(final Translate result, String input, String requestId) {
//                //将Translate result 转化为Json
//                Gson gson = new Gson();
//                String translateJson = gson.toJson(result);
//                DBHelper db = new DBHelper(getContext(), "history");
//                ContentValues cv = new ContentValues();
//                cv.put("translate", translateJson);
//                db.insert(cv);
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        TranslateData td = new TranslateData(
//                                System.currentTimeMillis(), result);
//                        list.add(td);
//                        trslist.add(result);
//                        adapter.notifyDataSetChanged();
//                        translateList.setSelection(list.size() - 1);
//                        dismissLoadingView();
//                        fanyiInputText.setText("");
//                        imm.hideSoftInputFromWindow(fanyiInputText.getWindowToken(), 0);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(final TranslateErrorCode error, String requestId) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        ToastUtils.show(view.getContext(), "查询错误:" + error.name());
//                        dismissLoadingView();
//                    }
//                });
//            }
//        });
//    }
//
//    private void showLoadingView(final String text) {
//        waitHandler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                if (progressDialog != null && !progressDialog.isShowing()) {
//                    progressDialog.setMessage(text);
//                    progressDialog.show();
//                }
//            }
//        });
//
//    }
//
//    private void dismissLoadingView() {
//        waitHandler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                if (progressDialog != null && progressDialog.isShowing())
//                    progressDialog.dismiss();
//            }
//        });
//
//    }
//
//    public void postQuery(final Translate bean) {
//        showLoadingView("正在翻译，请稍等");
//    }
//
//    public boolean isContainChinese(String str) {
//
//
//        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//        Matcher m = p.matcher(str);
//        if (m.find()) {
//            return true;
//        }
//        return false;
//    }
}
