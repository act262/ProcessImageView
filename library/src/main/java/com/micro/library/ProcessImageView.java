package com.micro.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 上传进度展示的ImageView
 * <br/>
 * 下载进度
 *
 * @author zhangchaoxian@kankan.com
 */
public class ProcessImageView extends ImageView {

    // 遮罩画笔
    private Paint mMaskPaint;
    // 文字画笔
    private Paint mTextPaint;

    // 当前进度
    private int progress = 0;
    // 是否需要展示进度
    private boolean isNeedProgress = false;

    // 未完成颜色
    private int unFinishedColor = Color.parseColor("#70000000");

    // 文字区域
    private final Rect mTextRect = new Rect();

    public ProcessImageView(Context context) {
        super(context);
    }

    public ProcessImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProcessImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMaskPaint = new Paint();
        mMaskPaint.setColor(unFinishedColor);
        mMaskPaint.setAntiAlias(true); // 消除锯齿
        mMaskPaint.setStyle(Paint.Style.FILL); //填充

        mTextPaint = new Paint();
        mTextPaint.setTextSize(30);
        mTextPaint.setAntiAlias(true);
        mTextPaint.getTextBounds("100%", 0, 4, mTextRect);// 确定文字的宽度
        System.out.println(mTextRect);

        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 需要展示进度
        if (isNeedProgress) {
            // 已完成部分的高度
            float height = getHeight() * progress / 100;

            // 绘制未完成部分遮罩
            canvas.drawRect(0, height, getWidth(), getHeight(), mMaskPaint);

            // 0进度不显示百分比
            if (progress == 0) return;

            int left = (getWidth() - mTextRect.width()) >> 1;  // getHeight() / 2 - mTextRect.height() / 2
            int top = (getHeight() + mTextRect.height()) >> 1; // getHeight() / 2 + mTextRect.height() / 2

            // 中间显示进度百分比
            canvas.drawText(progress + "%", left, top, mTextPaint);
        }
    }

    /**
     * 更新当前进度
     *
     * @param progress 0~100
     */
    public void setProgress(int progress) {
        this.progress = progress;
        if (progress >= 100) {
            this.isNeedProgress = false;
            this.progress = 100;
        } else {
            this.isNeedProgress = true;
        }
        postInvalidate();
    }
}