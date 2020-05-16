package com.example.todayhistory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todayhistory.bean.HistoryAdapter;
import com.example.todayhistory.bean.HistoryBean;
import com.example.todayhistory.bean.HistoryDescribeBean;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private TextView tvEmpty;
    private ListView historyLv;
    private ImageView imgBack;
    private List<HistoryBean.ResultBean> datas;
    private HistoryAdapter adapter;
    private HistoryBean historyBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIbasic();
        setContentView(R.layout.activity_history);
        //初始化
        init();
    }
    private void UIbasic(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    //初始化控件
    public void init() {
        tvEmpty = findViewById(R.id.history_tvEmpty);
        historyLv = findViewById(R.id.historyLv);
        imgBack = findViewById(R.id.history_imgBack);

        //添加点击事件，返回上一界面
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //listview添加适配器
        datas = new ArrayList<>();
        adapter = new HistoryAdapter(this, datas);
        historyLv.setAdapter(adapter);
        //添加点击事件
        historyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryActivity.this, HistoryDescribeActivity.class);
                HistoryBean.ResultBean resultBean = datas.get(position);
                String beanId = resultBean.get_id();
                intent.putExtra("historyId", beanId);
                startActivity(intent);
            }
        });


        try {
            //获取启动该Activity的intent对象
            Intent intent = getIntent();
            //获取intent额外的数据
            Bundle bundle = intent.getExtras();
            historyBean = (HistoryBean) bundle.getSerializable("history");
            List<HistoryBean.ResultBean> list = historyBean.getResult();
            datas.addAll(list);
            //更新数据
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            //提示暂无数据
            tvEmpty.setVisibility(View.VISIBLE);
        }

    }
}
