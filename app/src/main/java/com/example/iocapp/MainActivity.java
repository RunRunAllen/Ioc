package com.example.iocapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.btn1)
    Button btn1;

    @ViewInject(R.id.btn2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn1.setText("哈哈哈");
        btn2.setText("啦啦啦啦");

    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "啦啦啦啦啦", Toast.LENGTH_LONG).show();
    }

}
