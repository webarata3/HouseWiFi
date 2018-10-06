package link.webarata3.dro.housewifi;

import android.content.Intent;
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

        private static final String TAG = "SampleViewFactory";
        private List<String> reservedSsidList;
        private Map<String, Ssid> ssidMap;

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
            Ssid ssidData = ssidMap.get(ssid);

            RemoteViews remoteViews = null;
            remoteViews = new RemoteViews(getPackageName(), R.layout.widget_listview_row);
            remoteViews.setTextViewText(R.id.ssid, ssid);
            remoteViews.setTextViewText(R.id.quality, (ssidData == null ? 0 : ssidData.getQuality()) + "%");
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
            ssidMap = SsidUtil.getCurrentSsid(getApplicationContext());
        }
    }
}