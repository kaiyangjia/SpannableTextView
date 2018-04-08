# SpannableTextView
Spannable TextView for android, implements SpannableString in xml layout file.





## Usage

1. Add some Span.

   ```xml
       <com.jiakaiyang.lib.SpannableTextView
           android:id="@+id/spannable"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           spannable:addClickableSpan="1,0,4,2" />
   ```

2. Remove some Span.

   ```java
   SpannableTextView textView = findViewById(R.id.spannable);
   textView.removeSpan(1);
   ```

   ​

### Attrs

