package link.webarata3.dro.housewifi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Objects;

public class MainWidget extends AppWidgetProvider {
    public static final String ITEM_CLICK_ACTION = "link.webarata3.dro.housewifi.ITEM_CLICK_ACTION";
    private static final String UPDATE_ACTION = "link.webarata3.dro.housewifi.UPDATE_ACTION";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            Intent remoteViewsFactoryIntent = new Intent(context, MainService.class);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            remoteViews.setRemoteAdapter(R.id.listView, remoteViewsFactoryIntent);

            // 更新ボタン
            Intent updateButtonIntent = new Intent(context, MainWidget.class);
            updateButtonIntent.setAction(UPDATE_ACTION);
            PendingIntent updateButtonPendingIntent = PendingIntent.getBroadcast(
                    context, 0, updateButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.updateButton, updateButtonPendingIntent);

            // リスト
            Intent itemClickIntent = new Intent(context, MainWidget.class);
            itemClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            PendingIntent itemClickPendingIntent = PendingIntent.getBroadcast(
                    context, 0, itemClickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.listView, itemClickPendingIntent);

            IntentFilter intentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            context.getApplicationContext().registerReceiver(this, intentFilter);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Objects.requireNonNull(intent.getAction());

        switch (intent.getAction()) {
            case UPDATE_ACTION:
                notifyNetworkChanged(context);
                break;
            case ITEM_CLICK_ACTION:
                WiFiUtil.changeAccessPoint(context, "g_kappa_wifi2f");
            case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                switch (info.getState()) {
                    case CONNECTED:
                    case DISCONNECTED:
                        notifyNetworkChanged(context);
                        break;
                    case SUSPENDED:
                        break;
                    case CONNECTING:
                        break;
                    case DISCONNECTING:
                        break;
                    case UNKNOWN:
                        break;
                }
                break;
        }
    }

    private void notifyNetworkChanged(Context context) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        ComponentName component = new ComponentName(context, MainWidget.class);
        manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(component), R.id.listView);
    }
}
