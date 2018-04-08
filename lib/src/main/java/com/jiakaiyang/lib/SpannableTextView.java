package com.jiakaiyang.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jia on 2018/4/8.
 * SpannableTextView extends TextView
 */

@SuppressLint("AppCompatCustomView")
public class SpannableTextView extends TextView {
    private static final String TAG = "SpannableTextView";

    private Map<String, List<SpanConfig>> mSpans = new HashMap<>();


    public SpannableTextView(Context context) {
        this(context, null, 0);
    }

    public SpannableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpannableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpannableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.SpannableTextView);

        String addClickableSrc = typedArray.getString(R.styleable.SpannableTextView_addClickableSpan);
        List<SpanConfig> clickableSpanConfig = SpanConfig.createClickableSpanConfigs(addClickableSrc, new SpanClickListener() {
            @Override
            public void onClick(View view, SpanConfig spanConfig) {

            }
        });


        String foregroundSrc = typedArray.getString(R.styleable.SpannableTextView_addForegroundColorSpan);
        List<SpanConfig> foregroundColorConfig = SpanConfig.createForegroundSpanConfigs(foregroundSrc);
        mSpans.put("ForegroundColorSpan", foregroundColorConfig);

        typedArray.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableString spannableString = new SpannableString(text);
        for (List<SpanConfig> spanConfigs : mSpans.values()) {
            for (SpanConfig spanConfig : spanConfigs) {
                Object span = spanConfig.getSpan();

                spannableString.setSpan(span, spanConfig.getStart(), spanConfig.getEnd(), spanConfig.getSpanFlag());
            }
        }

        super.setText(spannableString, type);
    }

    /* public method start */

    /**
     * remove Span by id
     *
     * @param id the id of the Span
     */
    public void removeSpan(int id) {

    }

    /* public method end */
}
