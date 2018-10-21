package link.webarata3.dro.housewifi.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import link.webarata3.dro.housewifi.AppExecutors;
import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;

public class HouseWiFiModel {
    private static HouseWiFiModel model;
    private List<HouseWifiObserver> houseWifiObserverList;
    private boolean acceptPermission;
    private List<Ssid> ssidList;

    private HouseWiFiModel() {
        houseWifiObserverList = new ArrayList<>();
    }

    public static synchronized HouseWiFiModel getInstance() {
        if (model == null) {
            model = new HouseWiFiModel();
        }

        return model;
    }

    public void addObserver(HouseWifiObserver houseWifiObserver) {
        houseWifiObserverList.add(houseWifiObserver);
    }

    public void notifyObservers(Event event) {
        for (HouseWifiObserver observer : houseWifiObserverList) {
            observer.update(event);
        }
    }

    protected SharedPreferences createSharedPreferences(Context context) {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public boolean checkFirstAccess(Context context) {
        SharedPreferences preferences = createSharedPreferences(context);
        return preferences.getBoolean("firstAccess", true);
    }

    public void saveNotFirstAccess(Context context) {
        SharedPreferences preferences = createSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstAccess", false);
        editor.apply();
    }

    public boolean isAcceptPermission() {
        return acceptPermission;
    }

    public void setAcceptPermission(boolean acceptPermission) {
        this.acceptPermission = acceptPermission;
    }

    public List<Ssid> getSsidList() {
        return ssidList;
    }

    public void setSsidList(List<Ssid> ssidList) {
        this.ssidList = ssidList;
    }

    protected SsidDao createSsidDao(SQLiteDatabase db) {
        return new SsidDao(db);
    }

    protected DatabaseHelper createDatabaseHelper(Context context) {
        return new DatabaseHelper(context);
    }


    public void readAllSsid(Context context) {
        AppExecutors.getInstance().diskIo().execute(() -> {
            DatabaseHelper helper = createDatabaseHelper(context);
            helper.executeQuery(this::readAllSsid);
        });
    }

    protected void readAllSsid(SQLiteDatabase db) {
        SsidDao ssidDao = createSsidDao(db);
        model.setSsidList(ssidDao.selectAll());

        notifyObservers(Event.UPDATE_LIST);
    }

    public void registerSsid(Context context, Ssid ssid) {
        AppExecutors.getInstance().diskIo().execute(() -> {
            DatabaseHelper helper = createDatabaseHelper(context);
            helper.executeInTransaction(db -> registerSsid(db, ssid));
        });
    }

    protected void registerSsid(SQLiteDatabase db, Ssid ssid) {
        SsidDao ssidDao = createSsidDao(db);
        ssidDao.insert(ssid);
        notifyObservers(Event.REGISTER);
    }

    public enum Event {
        REGISTER, UPDATE_LIST
    }

    public interface HouseWifiObserver {
        void update(Event event);
    }
}
