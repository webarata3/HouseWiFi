package link.webarata3.dro.housewifi.model;

import java.util.List;

public class HouseWiFiiModel {
    private static HouseWiFiiModel model;

    private HouseWiFiiModel() {
        // ignore
    }

    public static synchronized HouseWiFiiModel getInstance() {
        if (model == null) {
            model = new HouseWiFiiModel();
        }

        return model;
    }

    private boolean firstAccess;
    private boolean acceptPermission;
    private List<Ssid> ssidList;

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
}
