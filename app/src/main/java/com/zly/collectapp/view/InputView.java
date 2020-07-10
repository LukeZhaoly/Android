package com.zly.collectapp.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.zly.collectapp.R;

public class InputView extends AppCompatEditText implements View.OnFocusChangeListener {

    /**
     * 删除图标
     */
    private Drawable clearDraw;
    /**
     * 是否焦点
     */
    private boolean isFocus;

    private Context context;

    public InputView(Context context) {
        this(context,null);
    }

    public InputView(Context context, AttributeSet attrs) {
        this(context,attrs,android.R.attr.editTextStyle);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    private void init() {
        clearDraw = getCompoundDrawables()[2];
        if (clearDraw==null){
//            Resources resources=getResources();
            Resources.Theme theme=context.getTheme();
           // clearDraw=VectorDrawableCompat.create(resources,R.drawable.ic_baseline_clear_24,theme);
           clearDraw= getResources().getDrawable(R.drawable.icon_clear);
        }
        setClearIconVisiable(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setClearIconVisiable(s.length()>0);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.clearDraw=null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (clearDraw!=null && event.getAction()==MotionEvent.ACTION_UP){
            int leftEdgeOfRightDrawable =getRight()-getPaddingRight()-clearDraw.getIntrinsicWidth();
            if (event.getRawX()>=leftEdgeOfRightDrawable){
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.isFocus=hasFocus;
        if (isFocus){
            setClearIconVisiable(getText().length()>0);
        }else {
            setClearIconVisiable(false);
        }
    }

    private void setClearIconVisiable(boolean b) {
        Drawable right = b ? clearDraw : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }
}
