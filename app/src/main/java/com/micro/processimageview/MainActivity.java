package com.micro.processimageview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.micro.library.ProcessImageView;

/**
 * ProcessImageView Demo
 *
 * @author zhangchaoxian@kankan.com
 */
public class MainActivity extends AppCompatActivity {

    private ProcessImageView mProcessView;
    private EditText mIntervalEditor;

    // 模拟下载速率
    private int mInterval = 100;

    // 下载模拟
    Handler handler = new Handler() {

        // 0~100 当前下载进度
        private int mProgress = 0;

        @Override
        public void handleMessage(Message msg) {
            // 重置
            if (msg.what == 0) {
                mProgress = 0;
            }

            // download progress ++
            mProgress++;
            // update download progress
            mProcessView.setProgress(mProgress);

            // 完成
            if (mProgress >= 100) return;

            mockDownloadFile();
        }

        private void mockDownloadFile() {
            handler.sendEmptyMessageDelayed(1, mInterval);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProcessView = (ProcessImageView) findViewById(R.id.processImageView);
        mIntervalEditor = (EditText) findViewById(R.id.rate);

        // 初始化进度为0
        mProcessView.setProgress(0);
    }

    public void start(View view) {
        String s = mIntervalEditor.getText().toString();
        mInterval = Integer.valueOf(s);
        if (mInterval < 0)
            mInterval = 100;

        start();
    }

    public void resume(View view) {
        resume();
    }

    public void pause(View view) {
        pause();
    }

    // 开始下载
    private void start() {
        handler.removeMessages(1);
        handler.sendEmptyMessage(0);
    }

    // 恢复下载
    private void resume() {
        handler.removeMessages(1);
        handler.sendEmptyMessage(1);
    }

    // 暂停下载
    private void pause() {
        handler.removeMessages(1);
    }
}
