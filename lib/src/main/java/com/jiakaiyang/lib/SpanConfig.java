package com.jiakaiyang.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 2018/4/8.
 * Config for a Span
 */

public class SpanConfig {

    private int id;
    private int start;
    private int end;
    private int spanFlag;
    private Object mSpan;

    private SpanConfig(Object mSpan) {
        this.mSpan = mSpan;
    }

    /**
     * Create SpanConfig
     *
     * @param srcConfig the raw string from xml layout.
     * @param mSpan
     * @return
     */
    public static SpanConfig createInstance(String srcConfig, Object mSpan) {
        SpanConfig spanConfig = new SpanConfig(mSpan);

        String[] strings = srcConfig.split(",");
        if (strings.length != 4) {
            throw new IllegalArgumentException("srcConfig format error");
        }

        int[] ints = new int[strings.length];
        for (int i = 0; i < ints.length; i++) {
            try {
                String str = strings[i];
                ints[i] = Integer.valueOf(str);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("attr should be integer");
            }
        }

        spanConfig.setId(ints[0]);
        spanConfig.setStart(ints[1]);
        spanConfig.setEnd(ints[2]);
        spanConfig.setSpanFlag(ints[3]);

        return spanConfig;
    }


    /**
     * Create SpanConfig List
     *
     * @param srcConfig
     * @param mSpan
     * @return
     */
    public static List<SpanConfig> createInstances(String srcConfig, Object mSpan) {
        List<SpanConfig> result = new ArrayList<>();
        String[] srcList = srcConfig.split("|");

        for (String src : srcList) {
            SpanConfig spanConfig = createInstance(src, mSpan);
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

    public Object getmSpan() {
        return mSpan;
    }

    public void setmSpan(Object mSpan) {
        this.mSpan = mSpan;
    }
}
