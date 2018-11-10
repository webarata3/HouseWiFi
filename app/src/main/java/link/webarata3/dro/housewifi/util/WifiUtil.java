package link.webarata3.dro.housewifi.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import link.webarata3.dro.housewifi.model.AccessPoint;
import link.webarata3.dro.housewifi.model.ConnectedWifi;

import static android.content.Context.WIFI_SERVICE;

public class WifiUtil {
    private WifiUtil() {
        // ignore
    }

    public static ConnectedWifi getConnectedWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        Objects.requireNonNull(wifiManager);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        return new ConnectedWifi(wifiInfo.getSSID(), wifiInfo.getIpAddress(), wifiInfo.getLinkSpeed());
    }

    public static Map<String, AccessPoint> getCurrentAccessPoint(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        Objects.requireNonNull(wifiManager);
        wifiManager.startScan();

        Map<String, AccessPoint> ssidMap = new HashMap<>();

        List<ScanResult> scanResults = wifiManager.getScanResults();

        for (ScanResult scanResult : scanResults) {
            AccessPoint accessPoint = new AccessPoint(scanResult.SSID, scanResult.level);
            ssidMap.put(accessPoint.getSsid(), accessPoint);
        }

        return ssidMap;
    }

    public static void changeAccessPoint(Context context, String ssid) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        Objects.requireNonNull(wifiManager);

        // 先に切断する
        wifiManager.disconnect();

        for (WifiConfiguration config : wifiManager.getConfiguredNetworks()) {
            if (config.SSID.replace("\"", "").equals(ssid)) {
                wifiManager.enableNetwork(config.networkId, true);
                break;
            }
        }
    }
}
