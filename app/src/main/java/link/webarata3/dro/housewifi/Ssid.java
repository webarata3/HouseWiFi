package link.webarata3.dro.housewifi;

public class Ssid {
    private String ssid;
    private int level;
    private int quality;

    public Ssid(String ssid, int level) {
        this.ssid = ssid;
        this.level = level;
        this.quality = 2 * (this.level + 100);
        this.quality = this.quality > 100 ? 100 : this.quality;
    }

    public String getSsid() {
        return ssid;
    }

    public int getLevel() {
        return level;
    }

    public int getQuality() {
        return quality;
    }
}
