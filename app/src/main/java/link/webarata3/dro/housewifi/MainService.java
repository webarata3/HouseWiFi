package link.webarata3.dro.housewifi;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

public class MainService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new SampleWidgetFactory();
    }

    private class SampleWidgetFactory implements RemoteViewsFactory {

        private static final String TAG = "SampleViewFactory";
        private List<String> list;

        public void onCreate() {
            Log.v(TAG, "[onCreate]");
        }

        public void onDataSetChanged() {
            Log.v(TAG, "[onDataSetChanged]");

            fetchTimelines();
        }

        public void onDestroy() {
            Log.v(TAG, "[onDestroy]");
        }

        public RemoteViews getViewAt(int position) {
            Log.v(TAG, "[getViewAt]: " + position);

            if (list.size() <= 0) {
                return null;
            }

            RemoteViews rv = null;

            String text = list.get(position);

            rv = new RemoteViews(getPackageName(), R.layout.widget_listview_row);
            rv.setTextViewText(R.id.text1, text);
            return rv;
        }

        public long getItemId(int position) {
            Log.v(TAG, "[getItemId]: " + position);

            return position;
        }

        public int getCount() {
            Log.v(TAG, "[getCount]");

            return list.size();
        }

        public RemoteViews getLoadingView() {
            Log.v(TAG, "[getLoadingView]");

            return null;
        }


        public int getViewTypeCount() {
            Log.v(TAG, "[getViewTypeCount]");

            return 1;
        }

        public boolean hasStableIds() {
            Log.v(TAG, "[hasStableIds]");

            return true;
        }

        private void fetchTimelines() {
            list = new ArrayList<>();
            list.add("SSID");
            list.add("SSID");
            list.add("SSID");
            list.add("SSID");
            list.add("SSID");
            list.add("SSID");
        }
    }
}