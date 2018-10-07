package link.webarata3.dro.housewifi;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MainWidgetFactory();
    }

    private class MainWidgetFactory implements RemoteViewsFactory {
        private List<String> reservedSsidList;
        private ConnectedWifi connectedWifi;
        private Map<String, AccessPoint> ssidMap;

        @Override
        public void onCreate() {
            reservedSsidList = new ArrayList<>();
            reservedSsidList.add("a_kappa_wifi2");
            reservedSsidList.add("g_kappa_wifi2");
            reservedSsidList.add("a_kappa_wifi2f");
            reservedSsidList.add("g_kappa_wifi2f");
        }

        @Override
        public void onDataSetChanged() {
            fetchSsid();
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (reservedSsidList.size() <= 0) {
                return null;
            }

            String ssid = reservedSsidList.get(position);
            AccessPoint ssidData = ssidMap.get(ssid);

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_listview_row);
            remoteViews.setTextViewText(R.id.ssid, ssid);
            remoteViews.setTextViewText(R.id.quality, (ssidData == null ? 0 : ssidData.getQuality()) + "%");

            if (connectedWifi != null && ssid.equals(connectedWifi.getSsid())) {
                String other = connectedWifi.getLinkSpeed() + "Mbps";
                remoteViews.setTextViewText(R.id.linkSpeed, other);

                remoteViews.setTextColor(R.id.ssid, getResources().getColor(R.color.enableAccessPointColor));
            } else {
                remoteViews.setTextViewText(R.id.linkSpeed, "");
                remoteViews.setTextColor(R.id.ssid, getResources().getColor(R.color.disableAccessPointColor));
            }

            return remoteViews;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return reservedSsidList.size();
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }


        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private void fetchSsid() {
            connectedWifi = WiFiUtil.getConnectedWifi(getApplicationContext());
            ssidMap = WiFiUtil.getCurrentAccessPoint(getApplicationContext());
        }
    }
}