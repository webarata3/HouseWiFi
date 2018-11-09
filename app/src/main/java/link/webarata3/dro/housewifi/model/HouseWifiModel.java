package link.webarata3.dro.housewifi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import link.webarata3.dro.housewifi.dao.SettingService;
import link.webarata3.dro.housewifi.dao.SsidService;
import link.webarata3.dro.housewifi.dao.impl.SettingServiceImpl;
import link.webarata3.dro.housewifi.dao.impl.SsidServiceImpl;

public class HouseWifiModel {
    private static HouseWifiModel model;

    private List<HouseWifiObserver> houseWifiObserverList;
    private boolean acceptPermission;
    private List<Ssid> ssidList;
    private SettingService settingService;
    private SsidService ssidService;

    private HouseWifiModel(SettingService settingService, SsidService ssidService) {
        houseWifiObserverList = new ArrayList<>();
        this.settingService = settingService;
        this.ssidService = ssidService;
    }

    public static synchronized HouseWifiModel getDefaultInstance(Context context) {
        if (model != null) return model;
        return getInstance(new SettingServiceImpl(context), new SsidServiceImpl(context));
    }

    public static synchronized HouseWifiModel getInstance(SettingService settingService, SsidService ssidService) {
        if (model == null) {
            model = new HouseWifiModel(settingService, ssidService);
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

    public boolean checkFirstAccess() {
        return settingService.checkFirstAccess();
    }

    public void saveNotFirstAccess() {
        settingService.saveNotFirstAccess();
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

    public void readAllSsid() {
        ssidService.readAll(ssidList -> {
            this.ssidList = ssidList;

            notifyObservers(Event.UPDATE_LIST);
        });
    }

    public void registerSsid(Ssid ssid) {
        ssidService.register(ssid, (alreadyRegistered) -> {
            if (alreadyRegistered) {
                notifyObservers(Event.ALREADY_REGISTERED);
            } else {
                notifyObservers(Event.REGISTER);
            }
        });
    }

    public enum Event {
        REGISTER, ALREADY_REGISTERED, UPDATE_LIST
    }

    public interface HouseWifiObserver {
        void update(Event event);
    }
}
