package link.webarata3.dro.housewifi;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MainWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for(int appWidgetId : appWidgetIds) {
            Intent remoteViewsFactoryIntent = new Intent(context, MainService.class);
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            rv.setRemoteAdapter(R.id.listView, remoteViewsFactoryIntent);

            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}