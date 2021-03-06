package com.jiakaiyang.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
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

    private Map<Class, List<SpanConfig>> mSpans = new HashMap<>();


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
            mSpans.put(ForegroundColorSpan.class, foregroundColorConfig);
        }


        String backgroundSrc = typedArray.getString(R.styleable.SpannableTextView_addBackgroundColorSpan);
        if (backgroundSrc != null) {
            List<SpanConfig> backgroundColorConfig = SpanConfig.createBackgroundSpanConfigs(backgroundSrc);
            mSpans.put(BackgroundColorSpan.class, backgroundColorConfig);
        }


        String absoluteSizeSrc = typedArray.getString(R.styleable.SpannableTextView_addAbsoluteSizeSpan);
        if (absoluteSizeSrc != null) {
            List<SpanConfig> absoluteSizeConfig = SpanConfig.createAbsoluteSizeSpanConfigs(absoluteSizeSrc);
            mSpans.put(AbsoluteSizeSpan.class, absoluteSizeConfig);
        }


        String relativeSizeSrc = typedArray.getString(R.styleable.SpannableTextView_addRelativeSizeSpan);
        if (relativeSizeSrc != null) {
            List<SpanConfig> relativeSizeConfig = SpanConfig.createRelativeSizeSpanConfigs(relativeSizeSrc);
            mSpans.put(RelativeSizeSpan.class, relativeSizeConfig);
        }


        String urlSrc = typedArray.getString(R.styleable.SpannableTextView_addURLSpan);
        if (urlSrc != null) {
            List<SpanConfig> urlConfig = SpanConfig.createURLSpanConfigs(urlSrc);
            mSpans.put(URLSpan.class, urlConfig);
        }


        String strikethroughSrc = typedArray.getString(R.styleable.SpannableTextView_addStrikethroughSpan);
        if (strikethroughSrc != null) {
            List<SpanConfig> strikethroughConfig = SpanConfig.createStrikethroughSpanConfigs(strikethroughSrc);
            mSpans.put(StrikethroughSpan.class, strikethroughConfig);
        }


        String underlineSrc = typedArray.getString(R.styleable.SpannableTextView_addUnderlineSpan);
        if (underlineSrc != null) {
            List<SpanConfig> underlineConfig = SpanConfig.createUnderlineSpanConfigs(underlineSrc);
            mSpans.put(UnderlineSpan.class, underlineConfig);
        }


        String styleSrc = typedArray.getString(R.styleable.SpannableTextView_addStyleSpan);
        if (styleSrc != null) {
            List<SpanConfig> styleConfig = SpanConfig.createStyleSpanConfigs(styleSrc);
            mSpans.put(StyleSpan.class, styleConfig);
        }


        String subscriptSrc = typedArray.getString(R.styleable.SpannableTextView_addSubscriptSpan);
        if (subscriptSrc != null) {
            List<SpanConfig> subscriptConfig = SpanConfig.createSubscriptConfigs(subscriptSrc);
            mSpans.put(SubscriptSpan.class, subscriptConfig);
        }


        String superscriptSrc = typedArray.getString(R.styleable.SpannableTextView_addSuperscriptSpan);
        if (superscriptSrc != null) {
            List<SpanConfig> superscriptConfig = SpanConfig.createSuperscriptConfigs(superscriptSrc);
            mSpans.put(SuperscriptSpan.class, superscriptConfig);
        }


        String typefaceSrc = typedArray.getString(R.styleable.SpannableTextView_addTypefaceSpan);
        if (typefaceSrc != null) {
            List<SpanConfig> typefaceConfig = SpanConfig.createTypefaceConfigs(typefaceSrc);
            mSpans.put(TypefaceSpan.class, typefaceConfig);
        }

        typedArray.recycle();

        // init text
        CharSequence charSequence = getText();
        if (charSequence != null) {
            this.setText(charSequence);
        }
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

    /**
     * get specified Span
     *
     * @param id
     * @param spanClass
     * @return
     */
    public Object getSpan(int id, Class spanClass) {
        if (!mSpans.containsKey(spanClass)) {
            return null;
        }

        List<SpanConfig> spanList = mSpans.get(spanClass);

        Object result = null;
        for (SpanConfig config : spanList) {
            int targetId = config.getId();
            if (targetId == id) {
                result = config.getSpan();
            }
        }

        return result;
    }

    /* public method end */
}
