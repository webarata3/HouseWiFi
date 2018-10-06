package link.webarata3.dro.housewifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.WIFI_SERVICE;

public class SsidUtil {
    private SsidUtil() {
        // ignore
    }

    public static List<Ssid> getCurrentSsid(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        Objects.requireNonNull(wifiManager);
        wifiManager.startScan();

        List<Ssid> ssidList = new ArrayList<>();

        List<ScanResult> scanResults = wifiManager.getScanResults();

        for (ScanResult scanResult : scanResults) {
            Ssid ssid = new Ssid(scanResult.SSID, scanResult.level);
            ssidList.add(ssid);
        }

        return ssidList;
    }
}
