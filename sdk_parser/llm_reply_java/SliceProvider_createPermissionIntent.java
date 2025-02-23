public class SliceProvider_createPermissionIntent {
    
    import android.content.Intent;
    import android.net.Uri;
    import android.app.slice.SliceProvider;

    public class TestSliceProvider extends SliceProvider {

        @Override
        public boolean onCreateSliceProvider() {
            return false;
        }

        public Intent test_createPermissionIntent() {
            Uri uri = Uri.parse("content://com.example.test/slices/test");
            return createPermissionIntent(uri); //Note: this method is available since api 28
        }
    }
    
    import android.content.Intent;
    import android.support.test.InstrumentationRegistry;
    import android.support.test.runner.AndroidJUnit4;

    import org.junit.Test;
    import org.junit.runner.RunWith;

    import static org.junit.Assert.*;

    @RunWith(AndroidJUnit4.class)
    public class TestSliceProviderInstrumentedTest {
        @Test
        public void test_SliceProvider_createPermissionIntent() {
            TestSliceProvider provider = new TestSliceProvider();
            provider.attachInfo(InstrumentationRegistry.getTargetContext(), null);

            Intent intent = provider.test_createPermissionIntent();

            assertEquals("android.app.slice.action.REQUEST_SLICE_PERMISSION", intent.getAction());
        }
    }
}