package link.webarata3.dro.housewifi.model;

public class ConnectedWifi {
    private String ssid;
    private String ipAddress;
    private int linkSpeed;

    public ConnectedWifi(String ssid, int ipAddress, int linkSpeed) {
        this.ssid = ssid.replace("\"", "");
        this.ipAddress = (ipAddress & 0xFF) + "."
                + ((ipAddress >> 8) & 0xFF) + "."
                + ((ipAddress >> 16) & 0xFF) + "."
                + ((ipAddress >> 24) & 0xFF);

        this.linkSpeed = linkSpeed;
    }

    public String getSsid() {
        return ssid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getLinkSpeed() {
        return linkSpeed;
    }
}
