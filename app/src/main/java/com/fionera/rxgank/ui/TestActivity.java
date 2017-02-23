package com.fionera.rxgank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fionera.base.activity.BaseActivity;
import com.fionera.rxgank.R;
import com.fionera.rxgank.test.TestEntity;
import com.fionera.rxgank.test.TestModel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

public class TestActivity
        extends BaseActivity {

    private Button btnTest;
    private TestModel testModel;
    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();

        testModel = new TestModel();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "";
                try {
                    content = URLDecoder.decode("鱼", "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String, String> options = new HashMap<>();
                options.put("key", "f86ed9f21931cd311deffada92b58ac7");
                options.put("full", "1");
                options.put("q", content);
                testModel.test(options).compose(
                        TestActivity.this.<List<TestEntity>>bindToLifecycle()).subscribe(
                        new Action1<List<TestEntity>>() {
                            @Override
                            public void call(List<TestEntity> testEntities) {
                                tvTest.setText(testEntities.get(0).getDes());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                tvTest.setText(throwable.getMessage());
                            }
                        });
            }
        });
    }

    private void initView() {
        btnTest = (Button) findViewById(R.id.btn_test);
        tvTest = (TextView) findViewById(R.id.tv_test);
    }
}
