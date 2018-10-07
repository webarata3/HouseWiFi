package link.webarata3.dro.housewifi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MainWidget extends AppWidgetProvider {
    private static final String UPDATE_ACTION = "link.webarata3.dro.housewifi.UPDATE_ACTION";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent remoteViewsFactoryIntent = new Intent(context, MainService.class);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            remoteViews.setRemoteAdapter(R.id.listView, remoteViewsFactoryIntent);

            Intent updateButtonIntent = new Intent(context, MainWidget.class);
            updateButtonIntent.setAction(UPDATE_ACTION);
            PendingIntent updateButtonPendingIntent
                    = PendingIntent.getBroadcast(context, 0, updateButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                ComponentName component = new ComponentName(context, MainWidget.class);
                manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(component), R.id.listView);
                break;
        }
    }
}
