package link.webarata3.dro.housewifi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import link.webarata3.dro.housewifi.AppExecutors;
import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;

public class HouseWiFiModel {
    private static HouseWiFiModel model;
    private AppExecutors appExecutors;
    private List<HouseWifiObserver> houseWifiObserverList;
    private boolean firstAccess;
    private boolean acceptPermission;
    private List<Ssid> ssidList;

    private HouseWiFiModel() {
        houseWifiObserverList = new ArrayList<>();
        appExecutors = AppExecutors.getInstance();
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

    public boolean isFirstAccess() {
        return firstAccess;
    }

    public void setFirstAccess(boolean firstAccess) {
        this.firstAccess = firstAccess;
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

    public void readAllSsid(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.executeQuery(db -> {
            SsidDao ssidDao = new SsidDao(db);
            model.setSsidList(ssidDao.selectAll());

            notifyObservers(Event.updateList);
        });
    }

    public void registerSsid(Context context, Ssid ssid) {
        appExecutors.diskIo().execute(() -> {
            DatabaseHelper helper = new DatabaseHelper(context);
            helper.executeInTransaction(db -> {
                SsidDao ssidDao = new SsidDao(helper.getWritableDatabase());
                ssidDao.insert(ssid);
                notifyObservers(Event.Register);
            });
        });
    }

    public enum Event {
        Register, updateList;
    }

    public interface HouseWifiObserver {
        void update(Event event);
    }
}
