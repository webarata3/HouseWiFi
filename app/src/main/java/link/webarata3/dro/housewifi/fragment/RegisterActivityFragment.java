package link.webarata3.dro.housewifi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.model.HouseWifiModel;
import link.webarata3.dro.housewifi.model.Ssid;
import link.webarata3.dro.housewifi.validator.ValidateResult;

public class RegisterActivityFragment extends Fragment implements HouseWifiModel.HouseWifiObserver {
    private static HouseWifiModel model;
    private EditText ssidEditText;
    private OnRegisterFragmentListener onRegisterFragmentListener;

    public RegisterActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        model = HouseWifiModel.getDefaultInstance(getActivity());
        model.addObserver(this);

        ssidEditText = view.findViewById(R.id.ssidEditText);

        view.findViewById(R.id.registerButton).setOnClickListener(v -> {
            ValidateResult validateResult = validate();
            if (!validateResult.isValid()) {
                ssidEditText.setError(validateResult.getErrorMessage());
            } else {
                model.registerSsid(new Ssid(ssidEditText.getText().toString()));
            }
        });

        return view;
    }

    @NonNull
    private ValidateResult validate() {
        String ssid = ssidEditText.getText().toString();
        if (ssid.length() == 0) {
            return new ValidateResult("入力必須です");
        }

        return new ValidateResult();
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

    @Override
    public void onDetach() {
        super.onDetach();
        onRegisterFragmentListener = null;
    }

    @Override
    public void update(HouseWifiModel.Event event) {
        // 非同期イベントのため、detachされている恐れがあるため。
        if (onRegisterFragmentListener == null || getView() == null) {
            return;
        }

        switch (event) {
            case REGISTER:
                onRegisterFragmentListener.onClickRegisterButton();
                break;
            case ALREADY_REGISTERED:
                Snackbar.make(getView(), "すでに登録済みのSSIDです", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    public interface OnRegisterFragmentListener {
        void onClickRegisterButton();
    }
}
