package link.webarata3.dro.housewifi.dao.impl;

import android.content.Context;
import android.content.SharedPreferences;

import link.webarata3.dro.housewifi.dao.SettingService;

public class SettingServiceImpl implements SettingService {
    private static final String KEY_FIRST_ACCESS = "firstAccess";
    private Context context;

    public SettingServiceImpl(Context context) {
        this.context = context;
    }

    private SharedPreferences createSharedPreferences(Context context) {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    @Override
    public boolean checkFirstAccess() {
        SharedPreferences preferences = createSharedPreferences(context);
        return preferences.getBoolean(KEY_FIRST_ACCESS, true);
    }

    @Override
    public void saveNotFirstAccess() {
        SharedPreferences preferences = createSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_FIRST_ACCESS, false);
        editor.apply();
    }
}
