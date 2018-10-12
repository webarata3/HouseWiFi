package link.webarata3.dro.housewifi.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.model.Ssid;

public class SsidAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Ssid> ssidList;

    public SsidAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSsidList(List<Ssid> ssidList) {
        this.ssidList = ssidList;
    }

    @Override
    public int getCount() {
        return ssidList.size();
    }

    @Override
    public Object getItem(int position) {
        return ssidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ssidList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.ssid_listview_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ssidView = convertView.findViewById(R.id.ssidTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ssidView.setText(ssidList.get(position).getSsid());

        return convertView;
    }

    private static class ViewHolder {
        TextView ssidView;
    }
}
