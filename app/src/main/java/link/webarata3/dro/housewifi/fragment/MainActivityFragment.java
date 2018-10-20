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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.activity.SsidAdapter;
import link.webarata3.dro.housewifi.model.HouseWiFiModel;

public class MainActivityFragment extends Fragment implements HouseWiFiModel.HouseWifiObserver {
    private static HouseWiFiModel model;
    private final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;
    private RecyclerView recyclerView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        model = HouseWiFiModel.getInstance();
        model.addObserver(this);

        recyclerView = view.findViewById(R.id.recyclerView);
        model.readAllSsid(getActivity());

        Activity activity = Objects.requireNonNull(getActivity());
        SharedPreferences preferences = activity.getSharedPreferences("settings",
                Activity.MODE_PRIVATE);
        model.setFirstAccess(preferences.getBoolean("firstAccess", true));
        model.setAcceptPermission(checkPermission());

        if (model.isFirstAccess()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstAccess", false);
            editor.apply();
            if (!model.isAcceptPermission()) {
                requestPermission();
            }
        }

        return view;
    }

    private boolean checkPermission() {
        Activity activity = Objects.requireNonNull(getActivity());
        // versionは6.0以上なので、バージョンチェックは不要
        // if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        // 既に許可されているか確認
        return activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            model.setAcceptPermission(true);
        }
    }

    @Override
    public void update(HouseWiFiModel.Event event) {
        switch (event) {
            case UPDATE_LIST:
                SsidAdapter ssidAdapter = new SsidAdapter(model.getSsidList());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(ssidAdapter);
                break;
        }
    }
}
