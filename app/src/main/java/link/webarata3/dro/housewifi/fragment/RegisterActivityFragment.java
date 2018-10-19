package link.webarata3.dro.housewifi.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import link.webarata3.dro.housewifi.AppExecutors;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.activity.SsidAdapter;
import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;
import link.webarata3.dro.housewifi.model.HouseWiFiModel;

public class RegisterActivityFragment extends Fragment {
    private final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;

    public RegisterActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }
}
