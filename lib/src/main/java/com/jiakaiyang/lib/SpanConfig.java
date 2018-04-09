package com.jiakaiyang.lib;

import android.graphics.Color;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 2018/4/8.
 * Config for a Span
 */

public class SpanConfig {

    private static final String DIVIDER = ",";
    private static final String GROUP_VIDIER = "/";

    private int id;
    private int start;
    private int end;
    private int spanFlag;
    private Object mSpan;


    // just for ForegroundColorSpan
    private int foregroundColor;

    // just for BackgroundColorSpan
    private int backgroundColor;


    private SpanConfig() {
    }

    /**
     * Create SpanConfig
     *
     * @param srcConfig the raw string from xml layout.
     * @return
     */
    public static SpanConfig createInstance(String srcConfig
            , Class type) {
        SpanConfig spanConfig = new SpanConfig();

        String[] strings = srcConfig.split(DIVIDER);

        // 最小长度是4
        if (strings.length < 4) {
            throw new IllegalArgumentException("srcConfig format error");
        }

        int[] ints = new int[strings.length];
        for (int i = 0; i < ints.length; i++) {
            try {
                String str = strings[i];
                if (str.startsWith("#")) {
                    // color int, hex
                    ints[i] = Color.parseColor(str);
                } else {
                    ints[i] = Integer.valueOf(str);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("attr should be integer");
            }
        }

        // base attrs
        spanConfig.setId(ints[0]);
        spanConfig.setStart(ints[1]);
        spanConfig.setEnd(ints[2]);
        spanConfig.setSpanFlag(ints[3]);

        // set extra data
        if (type.equals(ForegroundColorSpan.class)) {
            spanConfig.setForegroundColor(ints[4]);
        } else if (type.equals(BackgroundColorSpan.class)) {
            spanConfig.setBackgroundColor(ints[4]);
        }

        return spanConfig;
    }


    /**
     * Create SpanConfig List
     *
     * @param srcConfig
     * @return
     */
    public static List<SpanConfig> createClickableSpanConfigs(String srcConfig
            , final SpanClickListener listener) {
        if (srcConfig == null) {
            throw new IllegalArgumentException("srcConfig should not be null");
        }

        List<SpanConfig> result = new ArrayList<>();
        String[] srcList = srcConfig.split("|");

        for (String src : srcList) {
            final SpanConfig spanConfig = createInstance(src
                    , ClickableSpan.class);

            Object span = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    listener.onClick(widget, spanConfig);
                }
            };

            spanConfig.setSpan(span);
            result.add(spanConfig);
        }

        return result;
    }


    /**
     * Create ForegroudnSpan configs
     *
     * @param srcConfig
     * @return
     */
    public static List<SpanConfig> createForegroundSpanConfigs(String srcConfig) {
        if (srcConfig == null) {
            throw new IllegalArgumentException("srcConfig should not be null");
        }

        List<SpanConfig> result = new ArrayList<>();
        String[] srcList = srcConfig.split(GROUP_VIDIER);

        for (String src : srcList) {
            final SpanConfig spanConfig = createInstance(src, ForegroundColorSpan.class);
            ForegroundColorSpan span = new ForegroundColorSpan(spanConfig.getForegroundColor());
            spanConfig.setSpan(span);
            result.add(spanConfig);
        }

        return result;
    }


    public static List<SpanConfig> createBackgroundSpanConfigs(String srcConfig) {
        if (srcConfig == null) {
            throw new IllegalArgumentException("srcConfig should not be null");
        }

        List<SpanConfig> result = new ArrayList<>();
        String[] srcList = srcConfig.split(GROUP_VIDIER);

        for (String src : srcList) {
            final SpanConfig spanConfig = createInstance(src, BackgroundColorSpan.class);
            BackgroundColorSpan span = new BackgroundColorSpan(spanConfig.getBackgroundColor());
            spanConfig.setSpan(span);
            result.add(spanConfig);
        }

        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getSpanFlag() {
        return spanFlag;
    }

    public void setSpanFlag(int spanFlag) {
        this.spanFlag = spanFlag;
    }

    public Object getSpan() {
        return mSpan;
    }

    public void setSpan(Object mSpan) {
        this.mSpan = mSpan;
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
