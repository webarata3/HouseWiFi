package link.webarata3.dro.housewifi.model;

import java.util.List;

public class HouseWiFiModel {
    private static HouseWiFiModel model;

    private HouseWiFiModel() {
        // ignore
    }

    public static synchronized HouseWiFiModel getInstance() {
        if (model == null) {
            model = new HouseWiFiModel();
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
