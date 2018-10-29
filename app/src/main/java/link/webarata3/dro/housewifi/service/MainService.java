package link.webarata3.dro.housewifi.service;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.core.content.ContextCompat;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.activity.MainWidget;
import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;
import link.webarata3.dro.housewifi.model.AccessPoint;
import link.webarata3.dro.housewifi.model.ConnectedWiFi;
import link.webarata3.dro.housewifi.model.Ssid;
import link.webarata3.dro.housewifi.util.WiFiUtil;

public class MainService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MainWidgetFactory();
    }

    private class MainWidgetFactory implements RemoteViewsFactory {
        private List<Ssid> reservedSsidList;
        private ConnectedWiFi connectedWiFi;
        private Map<String, AccessPoint> ssidMap;

        @Override
        public void onCreate() {
            reservedSsidList = new ArrayList<>();
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
            if (reservedSsidList.size() <= 0) return null;

            String ssid = reservedSsidList.get(position).getSsid();
            AccessPoint ssidData = ssidMap.get(ssid);

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_list_row);
            remoteViews.setTextViewText(R.id.ssid, ssid);
            remoteViews.setTextViewText(R.id.quality, (ssidData == null ? 0 : ssidData.getQuality()) + "%");

            if (connectedWiFi != null && ssid.equals(connectedWiFi.getSsid())) {
                String other = getString(R.string.link_speed, connectedWiFi.getLinkSpeed());
                remoteViews.setTextViewText(R.id.linkSpeed, other);

                remoteViews.setTextColor(R.id.ssid,
                        ContextCompat.getColor(getApplication(), R.color.colorEnableAccessPoint));
            } else {
                remoteViews.setTextViewText(R.id.linkSpeed, "");
                remoteViews.setTextColor(R.id.ssid,
                        ContextCompat.getColor(getApplication(), R.color.colorDisableAccessPoint));
            }

            // https://developer.android.com/guide/topics/appwidgets/
            Intent listIntent = new Intent();
            listIntent.setAction(MainWidget.ACTION_ITEM_CLICK);
            listIntent.putExtra("ssid", ssid);
            remoteViews.setOnClickFillInIntent(R.id.container, listIntent);

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
            connectedWiFi = WiFiUtil.getConnectedWiFi(getApplicationContext());
            ssidMap = WiFiUtil.getCurrentAccessPoint(getApplicationContext());

            reservedSsidList = new ArrayList<>();
            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            SsidDao ssidDao = new SsidDao(helper.getReadableDatabase());
            reservedSsidList = ssidDao.selectAll();
        }
    }
}
