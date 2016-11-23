package com.bt.rightalignedtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Monika on 11/20/2016.
 */

public class RightAlignedTextViewExtendingView extends View {
    private String mName;
    private String mText="Default Text";
    private Boolean mLeftAlign;
    private Boolean mRightAlign;
    private float mHeight;
    private float mWidth;
    private int mColor;
    private float mCornerRadius;
    private Paint mTextPaint;
    private Paint mPaint;

    public RightAlignedTextViewExtendingView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.RightAlignedTextViewExtendingView, 0,0);
        try {
            mHeight = typedArray.getDimension(R.styleable.RightAlignedTextViewExtendingView_height, 200);
            mWidth = typedArray.getDimension(R.styleable.RightAlignedTextViewExtendingView_width, 200);
            mLeftAlign = typedArray.getBoolean(R.styleable.RightAlignedTextViewExtendingView_left_align, false);
            mRightAlign = typedArray.getBoolean(R.styleable.RightAlignedTextViewExtendingView_right_align, true);
            mText = typedArray.getString(R.styleable.RightAlignedTextViewExtendingView_text);
            mName = typedArray.getString(R.styleable.RightAlignedTextViewExtendingView_name);
            mColor = typedArray.getColor(R.styleable.RightAlignedTextViewExtendingView_background_color, Color.YELLOW);
            mCornerRadius = typedArray.getFloat(R.styleable.RightAlignedTextViewExtendingView_corner_radius, 10);
        }
        finally {
            typedArray.recycle();
        }
        init();
    }

    public void init(){

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(50);
        if(mLeftAlign){
            mTextPaint.setTextAlign(Paint.Align.LEFT);
        }
        else {
            mTextPaint.setTextAlign(Paint.Align.RIGHT);
        }
        //mTextPaint.setStyle(Paint.Style.FILL);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw a rectangle
        RectF rectF = new RectF(100,200,100+mWidth,200+mHeight);
        canvas.drawRoundRect( rectF, mCornerRadius, mCornerRadius, mPaint);
        canvas.drawText(mText, 100+mWidth,200+mHeight, mTextPaint);
        // canvas.drawRect(rectF,mPaint);
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
        invalidate();
        requestLayout();
    }

    public float getmWidth() {
        return mWidth;
    }

    public void setmWidth(float mWidth) {
        this.mWidth = mWidth;
        invalidate();
        requestLayout();
    }

    public float getmHeight() {
        return mHeight;
    }

    public void setmHeight(float mHeight) {
        this.mHeight = mHeight;
        invalidate();
        requestLayout();
    }

    public Boolean getmRightAlign() {
        return mRightAlign;
    }

    public void setmRightAlign(Boolean mRightAlign) {
        this.mRightAlign = mRightAlign;
        invalidate();
        requestLayout();
    }

    public Boolean getmLeftAlign() {
        return mLeftAlign;
    }

    public void setmLeftAlign(Boolean mLeftAlign) {
        this.mLeftAlign = mLeftAlign;
        invalidate();
        requestLayout();
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
        invalidate();
        requestLayout();
    }
}
