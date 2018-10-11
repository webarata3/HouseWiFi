package link.webarata3.dro.housewifi.model;

public class AccessPoint {
    private String ssid;
    private int quality;

    public AccessPoint(String ssid, int level) {
        this.ssid = ssid;
        this.quality = 2 * (level + 100);
        this.quality = this.quality > 100 ? 100 : this.quality;
    }

    public String getSsid() {
        return ssid;
    }

    public int getQuality() {
        return quality;
    }
}
