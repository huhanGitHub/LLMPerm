import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URL_getContent {
    
    public void test_URL_getContent(String urlStr) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... urls) {
                StringBuilder content = new StringBuilder();
                try {
                    URL url = new URL(urls[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line);
                    }
                    
                    bufferedReader.close();
                    urlConnection.disconnect();
                    
                } catch (Exception e) {
                    Log.e("URLContentTask", "Error" + e.getMessage());
                }
                
                return content.toString();
            }
            
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("URLContentTask", "Result: "+result);
            }
        }.execute(urlStr);
    }
}