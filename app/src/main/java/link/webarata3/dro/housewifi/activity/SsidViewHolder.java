package link.webarata3.dro.housewifi.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import link.webarata3.dro.housewifi.R;

public class SsidViewHolder extends RecyclerView.ViewHolder {
    private TextView ssidTextView;

    public SsidViewHolder(View itemView) {
        super(itemView);
        ssidTextView = itemView.findViewById(R.id.ssidTextView);
    }

    public TextView getSsidTextView() {
        return ssidTextView;
    }

    public void setSsidTextView(TextView ssidTextView) {
        this.ssidTextView = ssidTextView;
    }
}
