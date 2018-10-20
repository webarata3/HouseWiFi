package link.webarata3.dro.housewifi.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import link.webarata3.dro.housewifi.AppExecutors;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.activity.SsidAdapter;
import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;
import link.webarata3.dro.housewifi.model.HouseWiFiModel;
import link.webarata3.dro.housewifi.model.Ssid;

public class RegisterActivityFragment extends Fragment implements HouseWiFiModel.HouseWifiObserver {
    private static HouseWiFiModel model;

    private EditText ssidEditText;

    private OnRegisterFragmentListener onRegisterFragmentListener;

    public interface OnRegisterFragmentListener {
        void onClickRegisterButton();
    }

    public RegisterActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        model = HouseWiFiModel.getInstance();
        model.addObserver(this);

        ssidEditText = view.findViewById(R.id.ssidEditText);
        view.findViewById(R.id.registerButton).setOnClickListener(v -> onClickRegisterButton());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnRegisterFragmentListener) {
            onRegisterFragmentListener = (OnRegisterFragmentListener) context;
        } else {
            throw new ClassCastException();
        }
    }

    private void onClickRegisterButton() {
        model.registerSsid(getActivity(), new Ssid(ssidEditText.getText().toString()));

        onRegisterFragmentListener.onClickRegisterButton();
    }

    @Override
    public void update(HouseWiFiModel.Event event) {
        switch (event) {
            case Register:
                Snackbar.make(getView(), "登録しました。", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }
}
