package com.jiakaiyang.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
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

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpannableTextView);

        // set ClickableSpan attrs
        String addClickableSrc = typedArray.getString(R.styleable.SpannableTextView_addClickableSpan);
        Log.i(TAG, "init: addClickableSrc: " + addClickableSrc);
        if (addClickableSrc != null) {
            List<SpanConfig> clickableSpanConfig = SpanConfig.createClickableSpanConfigs(addClickableSrc, new SpanClickListener() {
                @Override
                public void onClick(View view, SpanConfig spanConfig) {

                }
            });
        }


        // set ForegroundColorSpan attrs
        String foregroundSrc = typedArray.getString(R.styleable.SpannableTextView_addForegroundColorSpan);
        Log.i(TAG, "init: foregroundSrc: " + foregroundSrc);
        if (foregroundSrc != null) {
            List<SpanConfig> foregroundColorConfig = SpanConfig.createForegroundSpanConfigs(foregroundSrc);
            mSpans.put("ForegroundColorSpan", foregroundColorConfig);
        }


        String backgroundSrc = typedArray.getString(R.styleable.SpannableTextView_addBackgroundColorSpan);
        if (backgroundSrc != null) {
            List<SpanConfig> backgroundColorConfig = SpanConfig.createBackgroundSpanConfigs(backgroundSrc);
            mSpans.put("BackgroundColorSpan", backgroundColorConfig);
        }


        String absoluteSizeSrc = typedArray.getString(R.styleable.SpannableTextView_addAbsoluteSizeSpan);
        if (absoluteSizeSrc != null) {
            List<SpanConfig> absoluteSizeConfig = SpanConfig.createAbsoluteSizeSpanConfigs(absoluteSizeSrc);
            mSpans.put("AbsoluteSizeSpan", absoluteSizeConfig);
        }

        typedArray.recycle();

        // init text
        CharSequence charSequence = getText();
        this.setText(charSequence);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        Log.i(TAG, "setText: text: " + text);
        if (mSpans != null) {
            SpannableString spannableString = createSpannableString(text);
            super.setText(spannableString, type);
        } else {
            super.setText(text, type);
        }
    }

    /**
     * Create spannable string
     * <p>
     * a d j f o r p
     * 0 1 2 3 4 5 6
     * 0 -6 -5 -4 -3 -2 -1
     *
     * @param text
     * @return
     */
    protected SpannableString createSpannableString(CharSequence text) {
        SpannableString spannableString = new SpannableString(text);

        for (List<SpanConfig> spanConfigs : mSpans.values()) {
            for (SpanConfig spanConfig : spanConfigs) {
                Object span = spanConfig.getSpan();
                int end = spanConfig.getEnd();
                int start = spanConfig.getStart();

                checkArgs(start, end);

                int realStart = spanConfig.getStart();
                int realEnd = spanConfig.getEnd();

                int length = text.length();

                // if end < start, the direction will reverse
                if (start == 0
                        && end < 0) {
                    realStart = length + end;
                    realEnd = length;
                }

                spannableString.setSpan(span
                        , realStart
                        , realEnd
                        , spanConfig.getSpanFlag());
            }
        }

        return spannableString;
    }

    protected void checkArgs(int start, int end) {
        if (start < 0) {
            throw new IllegalArgumentException("start attr error");
        }
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
