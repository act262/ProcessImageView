package com.micro.processimageview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.micro.library.ProcessImageView;

public class MainActivity extends AppCompatActivity {

    private ProcessImageView imageView;

    Handler handler = new Handler() {
        int progress = 0;

        @Override
        public void handleMessage(Message msg) {
            // 重置
            if (msg.what == 0) {
                progress = 0;
            }

            imageView.setProgress(progress);
            progress++;

            handler.sendEmptyMessageDelayed(1, 200);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ProcessImageView) findViewById(R.id.processImageView);
        // 初始化进度为0
        imageView.setProgress(0);
    }

    // 开始
    public void start(View view) {
        handler.sendEmptyMessage(0);
    }
}
