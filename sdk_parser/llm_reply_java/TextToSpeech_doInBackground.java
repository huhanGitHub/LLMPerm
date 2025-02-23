public class TextToSpeech_doInBackground {
    public void test_TextToSpeech_doInBackground() {
        new TextToSpeechAsyncTask().execute("Hello world");
    }

    private class TextToSpeechAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            if (textToSpeech != null) {
                textToSpeech.speak(strings[0], TextToSpeech.QUEUE_FLUSH, null, null);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}