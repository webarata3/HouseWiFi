package link.webarata3.dro.housewifi.model;

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
}
