package com.jiakaiyang.lib;

import android.graphics.Color;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 2018/4/8.
 * Config for a Span
 */

public class SpanConfig {

    private static final String DIVIDER = ",";
    private static final String GROUP_VIDIER = ";";

    private int id;
    private int start;
    private int end;
    private int spanFlag;
    private Object mSpan;


    // just for ForegroundColorSpan
    private int foregroundColor;

    // just for BackgroundColorSpan
    private int backgroundColor;

    // just for AbsoluteSizeSpan
    private int absoluteSizeSpanSize;
    private boolean absoluteSizeSpanDip;

    // just for RelativeSizeSpan
    private float proportion;

    // just for URLSpan
    private String url;

    private SpanConfig() {
    }

    /**
     * Create SpanConfig
     *
     * @param srcConfig the raw string from xml layout.
     * @return
     */
    private static SpanConfig createInstance(String srcConfig
            , Class type) {
        SpanConfig spanConfig = new SpanConfig();

        String[] strings = srcConfig.split(DIVIDER);

        // 最小长度是4
        if (strings.length < 4) {
            throw new IllegalArgumentException("srcConfig format error");
        }

        // base attrs
        spanConfig.setId(parseInt(strings[0]));
        spanConfig.setStart(parseInt(strings[1]));
        spanConfig.setEnd(parseInt(strings[2]));
        spanConfig.setSpanFlag(parseInt(strings[3]));

        // set extra data
        if (type.equals(ForegroundColorSpan.class)) {
            spanConfig.setForegroundColor(parseInt(strings[4]));
        } else if (type.equals(BackgroundColorSpan.class)) {
            spanConfig.setBackgroundColor(parseInt(strings[4]));
        } else if (type.equals(AbsoluteSizeSpan.class)) {
            spanConfig.setAbsoluteSizeSpanSize(parseInt(strings[4]));

            boolean isDip = false;
            if (strings.length >= 6) {
                isDip = parseInt(strings[5]) == 1;
            }
            spanConfig.setAbsoluteSizeSpanDip(isDip);
        } else if (type.equals(RelativeSizeSpan.class)) {
            spanConfig.setProportion(parseFloat(strings[4]));
        } else if (type.equals(URLSpan.class)) {
            spanConfig.setUrl(strings[4]);
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
        String[] srcList = createSrcList(srcConfig);
        List<SpanConfig> result = new ArrayList<>();

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
     * Create ForegroundSpan configs
     *
     * @param srcConfig
     * @return
     */
    public static List<SpanConfig> createForegroundSpanConfigs(String srcConfig) {
        String[] srcList = createSrcList(srcConfig);
        List<SpanConfig> result = new ArrayList<>();

        for (String src : srcList) {
            final SpanConfig spanConfig = createInstance(src, ForegroundColorSpan.class);
            ForegroundColorSpan span = new ForegroundColorSpan(spanConfig.getForegroundColor());
            spanConfig.setSpan(span);
            result.add(spanConfig);
        }

        return result;
    }


    /**
     * create BackgroundSpan configs
     *
     * @param srcConfig
     * @return
     */
    public static List<SpanConfig> createBackgroundSpanConfigs(String srcConfig) {
        String[] srcList = createSrcList(srcConfig);
        List<SpanConfig> result = new ArrayList<>();

        for (String src : srcList) {
            final SpanConfig spanConfig = createInstance(src, BackgroundColorSpan.class);
            BackgroundColorSpan span = new BackgroundColorSpan(spanConfig.getBackgroundColor());
            spanConfig.setSpan(span);
            result.add(spanConfig);
        }

        return result;
    }


    /**
     * create AbsoluteSizeSpan configs
     *
     * @param srcConfig
     * @return
     */
    public static List<SpanConfig> createAbsoluteSizeSpanConfigs(String srcConfig) {
        String[] srcList = createSrcList(srcConfig);
        List<SpanConfig> result = new ArrayList<>();

        for (String src : srcList) {
            final SpanConfig spanConfig = createInstance(src, AbsoluteSizeSpan.class);
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(spanConfig.getAbsoluteSizeSpanSize()
                    , spanConfig.isAbsoluteSizeSpanDip());
            spanConfig.setSpan(span);
            result.add(spanConfig);
        }

        return result;
    }


    /**
     * create RelativeSizeSpan configs
     *
     * @param srcConfig
     * @return
     */
    public static List<SpanConfig> createRelativeSizeSpanConfigs(String srcConfig) {
        String[] srcList = createSrcList(srcConfig);
        List<SpanConfig> result = new ArrayList<>();
        for (String src : srcList) {
            final SpanConfig spanConfig = createInstance(src, RelativeSizeSpan.class);
            RelativeSizeSpan span = new RelativeSizeSpan(spanConfig.getProportion());
            spanConfig.setSpan(span);
            result.add(spanConfig);
        }

        return result;
    }


    /**
     * Create URLSpan configs
     *
     * @param srcConfig
     * @return
     */
    public static List<SpanConfig> createURLSpanConfigs(String srcConfig) {
        String[] srcList = createSrcList(srcConfig);
        List<SpanConfig> result = new ArrayList<>();
        for (String src : srcList) {
            final SpanConfig spanConfig = createInstance(src, URLSpan.class);
            URLSpan span = new URLSpan(spanConfig.getUrl());
            spanConfig.setSpan(span);
            result.add(spanConfig);
        }

        return result;
    }


    private static String[] createSrcList(String srcConfig) {
        checkArgNotNull(srcConfig);
        return srcConfig.split(GROUP_VIDIER);
    }


    private static void checkArgNotNull(String srcConfig) {
        if (srcConfig == null) {
            throw new IllegalArgumentException("srcConfig should not be null");
        }
    }

    /**
     * Parse string to int, this String maybe a color such as "#ff0000"
     *
     * @param str
     * @return
     */
    private static int parseInt(String str) {
        int result = -1;
        try {
            if (str.startsWith("#")) {
                // color int, hex
                result = Color.parseColor(str);
            } else {
                result = Integer.valueOf(str);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("parseInt, attr should be integer");
        }

        return result;
    }

    /**
     * Parse a string to float
     *
     * @param str
     * @return
     */
    private static float parseFloat(String str) {
        float result = -1;
        try {
            result = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("parseInt, attr should be integer");
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

    public int getAbsoluteSizeSpanSize() {
        return absoluteSizeSpanSize;
    }

    public void setAbsoluteSizeSpanSize(int absoluteSizeSpanSize) {
        this.absoluteSizeSpanSize = absoluteSizeSpanSize;
    }

    public boolean isAbsoluteSizeSpanDip() {
        return absoluteSizeSpanDip;
    }

    public void setAbsoluteSizeSpanDip(boolean absoluteSizeSpanDip) {
        this.absoluteSizeSpanDip = absoluteSizeSpanDip;
    }

    public float getProportion() {
        return proportion;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
