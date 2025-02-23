public class ImageSwitcher_setImageURI {
    public void test_ImageSwitcher_setImageURI() {
        // Create ImageSwitcher
        ImageSwitcher imageSwitcher = new ImageSwitcher(this);

        // Set the factory required to create the views for ImageSwitcher
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });

        // Set the animation to use when switching images
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));

        // Set the URI of the image that should be displayed
        imageSwitcher.setImageURI(Uri.parse("YOUR_URI_HERE"));

        // Add imageSwitcher to your activity layout
        // Let's assume we have a LinearLayout with an id of "linearLayout"
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.addView(imageSwitcher);
    }
}