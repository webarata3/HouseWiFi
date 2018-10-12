package link.webarata3.dro.housewifi.activity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.Objects;

import link.webarata3.dro.housewifi.service.MainService;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.util.WiFiUtil;

public class MainWidget extends AppWidgetProvider {
    public static final String ACTION_ITEM_CLICK = "link.webarata3.dro.housewifi.ACTION_ITEM_CLICK";
    private static final String ACTION_UPDATE = "link.webarata3.dro.housewifi.ACTION_UPDATE";

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, MainWidget.class));
        context.sendBroadcast(intent);
    }

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

            // Wi-Fiの変更
            IntentFilter intentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            context.getApplicationContext().registerReceiver(
                    new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                            if (info == null) return;
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
                        }
                    }, intentFilter);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Objects.requireNonNull(intent.getAction());

        switch (intent.getAction()) {
            case ACTION_UPDATE:
                notifyNetworkChanged(context);
                break;
            case ACTION_ITEM_CLICK:
                Bundle bundle = Objects.requireNonNull(intent.getExtras());
                String ssid = bundle.getString("ssid");
                WiFiUtil.changeAccessPoint(context, ssid);
                break;
        }
    }

    private void notifyNetworkChanged(Context context) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        ComponentName component = new ComponentName(context, MainWidget.class);
        manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(component), R.id.listView);
    }
}
