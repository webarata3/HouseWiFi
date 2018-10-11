package link.webarata3.dro.housewifi.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import link.webarata3.dro.housewifi.AppExecutors;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.activity.SsidAdapter;
import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;
import link.webarata3.dro.housewifi.model.HouseWiFiiModel;
import link.webarata3.dro.housewifi.model.Ssid;

public class MainActivityFragment extends Fragment {
    private final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;

    private HouseWiFiiModel model;
    private ListView listView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = view.findViewById(R.id.listView);
        readSsidList(listView);

        model = HouseWiFiiModel.getInstance();

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
        Activity activity = Objects.requireNonNull(getActivity());
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

    private void readSsidList(ListView listView) {
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        helper.executeQuery(db -> {
            SsidDao ssidDao = new SsidDao(db);
            List<Ssid> ssidList = ssidDao.selectAll();

            AppExecutors.getInstance().mainThread().execute(() -> {
                Activity activity = Objects.requireNonNull(getActivity());
                SsidAdapter ssidAdapter = new SsidAdapter(activity);
                ssidAdapter.setSsidList(ssidList);
                listView.setAdapter(ssidAdapter);
            });
        });
    }
}
