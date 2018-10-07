package link.webarata3.dro.housewifi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MainWidget extends AppWidgetProvider {
    private static final String UPDATE_ACTION = "link.webarata3.dro.housewifi.UPDATE_ACTION";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent remoteViewsFactoryIntent = new Intent(context, MainService.class);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            remoteViews.setRemoteAdapter(R.id.listView, remoteViewsFactoryIntent);

            Intent updateButtonIntent = new Intent(context, MainWidget.class);
            updateButtonIntent.setAction(UPDATE_ACTION);
            PendingIntent updateButtonPendingIntent
                    = PendingIntent.getBroadcast(context, 0, updateButtonIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.updateButton, updateButtonPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.d("#########", "onReceive " + intent.getAction());
        switch (intent.getAction()) {
            case UPDATE_ACTION:
                break;
        }
    }
}
