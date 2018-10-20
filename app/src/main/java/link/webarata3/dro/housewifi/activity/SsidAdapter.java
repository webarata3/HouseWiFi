package link.webarata3.dro.housewifi.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.model.Ssid;

public class SsidAdapter extends RecyclerView.Adapter<SsidViewHolder> {

    private List<Ssid> ssidList;

    public SsidAdapter(List<Ssid> ssidList) {
        this.ssidList = ssidList;
    }

    @Override
    @NonNull
    public SsidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ssid_list_row, parent, false);
        return new SsidViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SsidViewHolder holder, int position) {
        holder.getSsidTextView().setText(ssidList.get(position).getSsid());
    }

    @Override
    public int getItemCount() {
        return ssidList.size();
    }
}