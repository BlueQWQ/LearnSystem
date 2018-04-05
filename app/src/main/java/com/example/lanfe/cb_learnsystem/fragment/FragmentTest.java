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

public class FragmentTest extends Fragment {
    private View view;
//
//    private TextView TV_title;
//    private TextView TV_index;
//    private CheckBox checkBox_1;
//    private CheckBox checkBox_2;
//    private CheckBox checkBox_3;
//    private CheckBox checkBox_4;
//
//    private int answer_index = -1;
//    private int answer_id = -1;
//    private int num = 0;
//    private int pre_num = 0;
//    private int flag = 0;
//    private String answer = "";
//    private List<String> interference = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test_content, null);
//        TV_title = view.findViewById(R.id.TV_title);
//        TV_index = view.findViewById(R.id.test_index);
//
//        checkBox_1 = view.findViewById(R.id.checkbox_1);
//        checkBox_2 = view.findViewById(R.id.checkbox_2);
//        checkBox_3 = view.findViewById(R.id.checkbox_3);
//        checkBox_4 = view.findViewById(R.id.checkbox_4);
//        checkBox_1.setTag(R.id.test_id, 1);
//        checkBox_2.setTag(R.id.test_id, 2);
//        checkBox_3.setTag(R.id.test_id, 3);
//        checkBox_4.setTag(R.id.test_id, 4);
//        checkBox_1.setOnCheckedChangeListener(checkBox_listener);
//        checkBox_2.setOnCheckedChangeListener(checkBox_listener);
//        checkBox_3.setOnCheckedChangeListener(checkBox_listener);
//        checkBox_4.setOnCheckedChangeListener(checkBox_listener);
//
//        interference.add("n. 表示问候， 惊奇或唤起注意时的用语");
//        interference.add("vt. 解决；解答；溶解");
//        interference.add("n. 翻译；译文；转化；调任");
//        interference.add("adj. 传统的；惯例的");
//        interference.add("adv. 实际上；事实上");
//        interference.add("n. 优势；利益；有利条件");
//        interference.add("n. 一个；单打；单程票");
//        interference.add("n. 现款；预备好的状态");
//        interference.add("adj. 美味的；可口的");
//        interference.add("adj. 困难的；不随和的；执拗的");

//        setNum();
//        createQuestion();
        return view;
    }

//    private CheckBox.OnCheckedChangeListener checkBox_listener = new CheckBox.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if (isChecked) {
//                int checkBox = (int) buttonView.getTag(R.id.test_id);
//                if (checkBox == answer_index) {
//                    pre_num++;
//                    ToastUtils.show(buttonView.getContext(), "选择正确");
//                    DBHelper db = new DBHelper(buttonView.getContext(), "history");
//                    db.update(answer_id, "isTest", "1");
//                    returnCheckBox(checkBox).setChecked(false);
//                    createQuestion();
//                } else {
//                    ToastUtils.show(buttonView.getContext(), "选择错误");
//                    returnCheckBox(checkBox).setChecked(false);
//                }
//            }
//        }
//    };
//
//    private void createQuestion() {
//        Log.e("gang", "createQuestion");
//        DBHelper db = new DBHelper(view.getContext(), "history");
//        Cursor cursor = db.search();
//        Gson gson = new Gson();
//        int pre_id = answer_id;
//
//        //生成答案
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                int id = cursor.getInt(0);
//                String translateJson = cursor.getString(1);
//                int isTest = cursor.getInt(3);
//                Translate result = gson.fromJson(translateJson, Translate.class);
//                //Log.v("gang", "语言:" + result.getFrom());
//                //Log.v("gang", "翻译:" + result.getExplains().get(0));
//
//                if (result.getFrom().equals("EN")) {
//                    if (flag == 0)
//                        pre_num++;
//                    if (isTest == 0) {
//                        flag = 1;
//                        TV_title.setText(result.getQuery());
//                        answer = result.getExplains().get(0);
//                        returnCheckBox(-1).setText(answer);
//                        answer_id = id;
//                        break;
//                    }
//                }
//                //cursor指针+1
//                cursor.moveToNext();
//            }
//        }
//
//        setIndex();
//        if (pre_id == answer_id) {
//            showResults();
//            return;
//        }
//
//
//        //生成干扰项
//        List<String> interfere = new ArrayList<>();
//        int n = 3;
//        while (n-- > 0) {
//            Random random = new Random();
//            int r = random.nextInt(cursor.getCount() - 1);
//            int i = 0;
//
//            if (cursor.moveToFirst()) {
//                while (!cursor.isAfterLast()) {
//                    if (i == r) {
//                        String translateJson = cursor.getString(1);
//                        Translate result = gson.fromJson(translateJson, Translate.class);
//                        String item;
//                        if (result.getExplains() != null && isContainChinese(result.getExplains().get(0)))
//                            item = result.getExplains().get(0);
//                        else
//                            item = "";
//                        interfere.add(item);
//                    }
//                    //cursor指针+1
//                    cursor.moveToNext();
//                    i++;
//                }
//
//            }
//        }
//        Log.v("gang", interfere.toString());
//        interfere = replace(interfere);
//        Log.v("gang", interfere.toString());
//        int l = 0;
//        int j = 1;
//
//        while (true) {
//            if (j == answer_index)
//                j++;
//            returnCheckBox(j++).setText(interfere.get(l++));
//            if (l == 3)
//                break;
//        }
//
//        cursor.close();
//        db.closeDB();
//    }
//
//    private List<String> replace(List<String> interfere) {
//        //Log.v("gang", "answer : "+answer);
//        //Log.v("gang", "interfere : "+interfere);
//        for (int i = 0; i < 3; i++) {
//            if (interfere.get(i).equals(answer) || interfere.get(i).equals("")) {
//                interfere.set(i, interference.get(new Random().nextInt(interference.size())));
//                interfere = replace(interfere);
//            }
//        }
//
//        if (interfere.get(0).equals(interfere.get(1)) || interfere.get(1).equals(interfere.get(2))) {
//            interfere.set(1, interference.get(new Random().nextInt(interference.size())));
//            interfere = replace(interfere);
//        } else if (interfere.get(0).equals(interfere.get(2))) {
//            interfere.set(0, interference.get(new Random().nextInt(interference.size())));
//            interfere = replace(interfere);
//        }
//        return interfere;
//    }
//
//
//    //返回指定序号的CheckBox,如果index为-1则随机生成一个序号
//    private CheckBox returnCheckBox(int index) {
//        if (index == -1) {
//            Random random = new Random();
//            answer_index = random.nextInt(4) + 1;
//            index = answer_index;
//            Log.v("gang", "答案是第" + answer_index);
//        }
//        switch (index) {
//            case 1:
//                return checkBox_1;
//            case 2:
//                return checkBox_2;
//            case 3:
//                return checkBox_3;
//            case 4:
//                return checkBox_4;
//            default:
//                return checkBox_1;
//        }
//    }
//
//    private boolean isContainChinese(String str) {
//
//
//        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//        Matcher m = p.matcher(str);
//        if (m.find()) {
//            return true;
//        }
//        return false;
//    }
//
//    private void showResults() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//        builder.setTitle("恭喜你测试通过所有个单词！总共" + num + "个");
//        builder.setMessage("点击确定重置所有单词并开始下一轮复习");
//        builder.setCancelable(false);
//        builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                DBHelper db = new DBHelper(view.getContext(), "history");
//                Cursor cursor = db.search();
//                pre_num = 1;
//                for (int i = 0; i < cursor.getCount(); i++) {
//                    db.update(i, "isTest", "" + 0);
//                }
//                cursor.close();
//                db.closeDB();
//                createQuestion();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    private void setNum() {
//        DBHelper db = new DBHelper(view.getContext(), "history");
//        Cursor cursor = db.search();
//        Gson gson = new Gson();
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                String translateJson = cursor.getString(1);
//                Translate result = gson.fromJson(translateJson, Translate.class);
//                if (result.getFrom().equals("EN")) {
//                    num++;
//                }
//                //cursor指针+1
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        db.closeDB();
//    }
//
//    private void setIndex() {
//        String str = "" + pre_num + "/" + num;
//        TV_index.setText(str);
//    }
}