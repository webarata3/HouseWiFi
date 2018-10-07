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
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Objects;

public class MainWidget extends AppWidgetProvider {
    public static final String ACTION_ITEM_CLICK = "link.webarata3.dro.housewifi.ACTION_ITEM_CLICK";
    private static final String ACTION_UPDATE = "link.webarata3.dro.housewifi.ACTION_UPDATE";

    public static final String ACTION_CHANGE_LIST = "link.webarata3.dro.housewifi.ACTION_CHANGE_LIST";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            Intent remoteViewsFactoryIntent = new Intent(context, MainService.class);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            remoteViews.setRemoteAdapter(R.id.listView, remoteViewsFactoryIntent);

            // 更新ボタン
            Intent updateButtonIntent = new Intent(context, MainWidget.class);
            updateButtonIntent.setAction(ACTION_UPDATE);
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

            IntentFilter myIntentFilter = new IntentFilter(ACTION_CHANGE_LIST);
            LocalBroadcastManager.getInstance(context).registerReceiver(this, myIntentFilter);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Objects.requireNonNull(intent.getAction());

        Log.d("############", intent.getAction());
        switch (intent.getAction()) {
            case ACTION_UPDATE:
                notifyNetworkChanged(context);
                break;
            case ACTION_ITEM_CLICK:
                WiFiUtil.changeAccessPoint(context, "g_kappa_wifi2f");
            case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (info == null) break;
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
