package com.example.thinkpad.kh10a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    private RadioGroup radioGroup;
    private RadioButton btn_image;
    private RadioButton btn_inner;
    private RadioButton btn_sd;
    private RadioButton btn_cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
        initListener();
        Intent intent=getIntent();
    }

    private void initListener() {
        radioGroup.setOnCheckedChangeListener(new MyRadioButtonListener());
    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btn_image = (RadioButton) findViewById(R.id.activity2_image);
        btn_inner = (RadioButton) findViewById(R.id.activity2_inner);
        btn_sd = (RadioButton) findViewById(R.id.activity2_sd);
        btn_cache = (RadioButton) findViewById(R.id.activty2_cache);

    }

    private class MyRadioButtonListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            switch (checkedId) {
                case R.id.activity2_image:
                    Log.d(TAG, "activity2_image...");
                    skip2MainActivity("image");
                    break;
                case R.id.activity2_inner:
                    Log.d(TAG, "activity2_inner...");
                    skip2MainActivity("inner");
                    break;
                case R.id.activity2_sd:
                    Log.d(TAG, "activity2_sd...");
                    skip2MainActivity("sd");
                    break;
                case R.id.activty2_cache:
                    Log.d(TAG, "activity2_cache...");
                    skip2MainActivity("cache");
                    break;
            }
        }
    }

    public void skip2MainActivity(String tag){
        Intent intent2 = new Intent(Main2Activity.this, MainActivity.class);
        intent2.putExtra("btn_tag", tag);
        startActivity(intent2);
    }
}
