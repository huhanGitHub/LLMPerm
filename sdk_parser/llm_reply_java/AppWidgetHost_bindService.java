public class AppWidgetHost_bindService {

    // Please replace Context with actual Context (for example Activity) depending on your application
    private Context context;
       
    public void test_AppWidgetHost_bindService() {
        AppWidgetHost host = new AppWidgetHost(context, R.id.appwidget_host);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        
        int widgetId = host.allocateAppWidgetId();
        Intent intent = new Intent(context, AppWidgetService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (appWidgetManager.bindAppWidgetIdIfAllowed(widgetId,
                    new ComponentName(context, MyWidgetProvider.class))) {
                Toast.makeText(context, "AppWidgetHost successfully bound to the AppWidgetService", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (appWidgetManager.bindAppWidgetId(widgetId,
                    new ComponentName(context, MyWidgetProvider.class))) {
                Toast.makeText(context, "AppWidgetHost successfully bound to the AppWidgetService", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}