public class AppWidgetProvider_onReceive {

   public void test_AppWidgetProvider_onReceive(){
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(this, MyAppWidgetProvider.class));
        sendBroadcast(intent);
    }
}