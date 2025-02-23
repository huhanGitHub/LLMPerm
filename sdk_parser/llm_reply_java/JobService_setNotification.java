public class JobService_setNotification extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        setNotification("Your custom notification message here");
        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private void setNotification(String message) {
        String channelId = "test_channel";
        String channelName = "Test Channel";
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Test JobService")
                        .setContentText(message)
                        .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());
    }
}