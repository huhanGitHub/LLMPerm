public class URLSpan_onClick {

    public void test_URLSpan_onClick(SpannableString string, URLSpan urlSpan) {
        int start = string.toString().indexOf("http");
        int end = start + urlSpan.getURL().length();

        string.setSpan(urlSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView textView = findViewById(R.id.your_text_view);
        textView.setText(string);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}