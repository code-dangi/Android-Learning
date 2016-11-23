package com.bt.rightalignedtextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Monika on 11/20/2016.
 */

public class RightAlignedTextViewExtendingTextView extends TextView {
    private TextPaint mTextPaint;

    public RightAlignedTextViewExtendingTextView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        mTextPaint = getPaint();
        //mTextPaint.setTextAlign(Paint.Align.LEFT);
        //mTextPaint.setTextSize(30);
       // mTextPaint.drawableState = getDrawableState();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
