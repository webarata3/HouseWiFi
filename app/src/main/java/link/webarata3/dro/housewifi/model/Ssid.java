package link.webarata3.dro.housewifi.model;

public class Ssid {
    private int id;
    private String ssid;

    public Ssid() {
    }

    public Ssid(String ssid) {
        this.ssid = ssid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
