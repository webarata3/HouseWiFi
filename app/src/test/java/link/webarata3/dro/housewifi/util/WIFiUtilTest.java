package link.webarata3.dro.housewifi.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.model.ConnectedWiFi;

import static android.content.Context.WIFI_SERVICE;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class WIFiUtilTest {
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void test_getConnectedWiFi() {
        WifiManager spyWifiManager = (WifiManager) spy(context.getSystemService(WIFI_SERVICE));
        WifiInfo mockWifiInfo = spy(spyWifiManager.getConnectionInfo());

        when(mockWifiInfo.getSSID()).thenReturn("dummy_ssid");
        int ip = 45 << 8;
        ip = (ip + 123) << 8;
        ip = (ip + 168) << 8;
        ip = ip + 192;
        when(mockWifiInfo.getIpAddress()).thenReturn(ip);
        when(mockWifiInfo.getLinkSpeed()).thenReturn(866);

        ConnectedWiFi connectedWifi = WiFiUtil.getConnectedWiFi(context);
    }
}
