# SpannableTextView
Spannable TextView for android, implements SpannableString in xml layout file.





## Usage

1. Import from jcenter

   ```groovy
   compile 'com.jiakaiyang:spannabletextview:0.9.2'
   ```

   ​

2. Add some Span.

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:spannable="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:divider="@drawable/divider"
    android:orientation="vertical"
    android:showDividers="middle"
    tools:context="com.jiakaiyang.spannabletextview.demo.MainActivity">

    <com.jiakaiyang.lib.SpannableTextView
        android:id="@+id/spannable"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World! Hello World!"
        spannable:addAbsoluteSizeSpan="3,4,6,17,25,1"
        spannable:addBackgroundColorSpan="1,8,12,18,#FF00FF00;1,2,5,33,#FFFF00FF"
        spannable:addForegroundColorSpan="2,0,-4,17,#FFFF0000" />

    <com.jiakaiyang.lib.SpannableTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World! Hello World!"
        spannable:addAbsoluteSizeSpan="4,4,7,17,25,1"
        spannable:addRelativeSizeSpan="5,9,13,17,2" />


    <com.jiakaiyang.lib.SpannableTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:clickable="true"
        android:text="Hello World! Click Here!"
        spannable:addURLSpan="6,13,23,17,https://github.com/kaiyangjia/SpannableTextView" />

    <com.jiakaiyang.lib.SpannableTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:clickable="true"
        android:text="Hello World! Delete This! Add Underline"
        spannable:addStrikethroughSpan="6,13,23,17"
        spannable:addUnderlineSpan="7,0,-13,17" />


    <!--
    public static final int NORMAL = 0;
    public static final int BOLD = 1;
    public static final int ITALIC = 2;
    public static final int BOLD_ITALIC = 3;-->
    <com.jiakaiyang.lib.SpannableTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:clickable="true"
        android:text="Hello World! BOLD ITALIC! BOLD_ITALIC"
        spannable:addStyleSpan="8,13,17,17,1;9,18,24,17,2;7,0,-11,17,3" />


    <com.jiakaiyang.lib.SpannableTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:clickable="true"
        android:text="Hello World! X2 Y3"
        spannable:addSubscriptSpan="8,13,14,17;9,16,17,17" />


    <com.jiakaiyang.lib.SpannableTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:clickable="true"
        android:text="Hello World! X2 Y3"
        spannable:addSuperscriptSpan="8,13,14,17;9,16,17,17" />


    <!-- "monospace", "serif", and "sans-serif" -->
    <com.jiakaiyang.lib.SpannableTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:clickable="true"
        android:text="Hello World! monospace serif sans-serif"
        spannable:addTypefaceSpan="8,13,22,17,monospace;9,23,27,17,serif;9,0,-10,17,sans-serif" />
</LinearLayout>
```

   The screenshot for this xml layout:

   ![screenshot1](art/screenshot1.png)

3. Remove some Span.

   ```java
   SpannableTextView textView = findViewById(R.id.spannable);
   textView.removeSpan(1);
   ```

4. If you want more, see `demo` module in this project. Or you can contact with me : kaiyangjia@163.com

   ​


### Support Spans

1. BackgroundColorSpan

2. ForegroundColorSpan

3. AbsoluteSizeSpan

4. RelativeSizeSpan

5. StrikethroughSpan

6. UnderlineSpan

7. StyleSpan

8. SubscriptSpan

9. SuperscriptSpan

10. TypefaceSpan

   ​



### Attrs

#### 1. Attrs for SpannableTextView

While you add some span, the attrs will like:

```xml
   spannable:addBackgroundColorSpan="1,8,12,12,#FF00FF00/1,2,5,12,#FFFF00FF"
```



Multi spans attires is split by "/", the args will be used to create Span instance,

```xml
   spannable:addBackgroundColorSpan="id,start,end,spanFlag,extraData"
```



| Name      | index | Desc                                     |
| :-------- | ----- | :--------------------------------------- |
| id        | 0     | Span id, every Span should has unique id. |
| start     | 1     | the start index for this Span, start will always >= 0. |
| end       | 2     | the end index for this Span, end can < 0, when end is negative, the target will start from the end of the Charsequence. |
| spanFlag  | 3     | flag for Span</br> Spanned.SPAN_EXCLUSIVE_EXCLUSIVE = 33;</br>Spanned.SPAN_EXCLUSIVE_INCLUSIVE = 34;</br>Spanned.SPAN_INCLUSIVE_EXCLUSIVE = 17;</br>Spanned.SPAN_INCLUSIVE_INCLUSIVE = 18; |
| extraData | >= 4  | extra data for specified Span. Such as BackgroundColorSpan will has `backgroundColor` in its extraData. |



#### 2. Extra Data for Every Support Span





### Change Log

#### 0.9.2

1. Add Some other Spans support, such as StrikethroughSpan,StyleSpan,UnderlineSpan etc.
2. This is a stable version.



#### 0.9.1

1. Add very base Spans support, like BackgroundSpan,ForegroundSpan.

   .

### TODO

-[ ] Add other Spans support.

