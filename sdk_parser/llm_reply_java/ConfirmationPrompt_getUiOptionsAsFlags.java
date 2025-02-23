import android.security.ConfirmationPrompt;
import android.util.Log;

public class ConfirmationPrompt_getUiOptionsAsFlags {
    private void test_ConfirmationPrompt_getUiOptionsAsFlags() {
        ConfirmationPrompt.Builder confirmationPromptBuilder = new ConfirmationPrompt.Builder(this);
        ConfirmationPrompt confirmationPrompt = confirmationPromptBuilder.setPromptText("Test Confirmation Prompt").build();

        try {
            int uiOptions = confirmationPrompt.getUiOptionsAsFlags();
            Log.d("ConfirmationPrompt_getUiOptionsAsFlags", "The UI options flags are: " + uiOptions);
        } catch (Exception e) {
            Log.e("ConfirmationPrompt_getUiOptionsAsFlags", "Error getting UI options flags", e);
        }
    }
}