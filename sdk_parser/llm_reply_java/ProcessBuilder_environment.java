import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class ProcessBuilder_environment {
    public void test_ProcessBuilder_environment() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ls");
            Map<String, String> envVariables = processBuilder.environment();
            envVariables.put("Variable1", "Value1");
            envVariables.put("Variable2", "Value2");

            Process process = processBuilder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                Log.v("ProcessBuilderTest", line);
            }
            process.waitFor();
        } catch (Exception e) {
            Log.e("ProcessBuilderTest", "Error while running the ProcessBuilder", e);
        }
    }
}