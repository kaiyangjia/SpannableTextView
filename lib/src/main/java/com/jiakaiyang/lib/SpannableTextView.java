package com.jiakaiyang.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jia on 2018/4/8.
 * SpannableTextView extends TextView
 */

@SuppressLint("AppCompatCustomView")
public class SpannableTextView extends TextView {
    public SpannableTextView(Context context) {
        this(context, null, 0);
    }

    public SpannableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpannableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpannableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }
}
