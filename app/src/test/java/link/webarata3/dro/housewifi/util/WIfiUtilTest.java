package link.webarata3.dro.housewifi.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.model.ConnectedWifi;

import static android.content.Context.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class WIfiUtilTest {
    @Test
    public void test_getConnectedWifi() {
        Context context = mock(Context.class);
        Context applicationContext = mock(Context.class);

        when(context.getApplicationContext()).thenReturn(applicationContext);

        WifiManager mockWifiManager = mock(WifiManager.class);
        when(applicationContext.getSystemService(WIFI_SERVICE)).thenReturn(mockWifiManager);

        WifiInfo mockWifiInfo = mock(WifiInfo.class);

        when(mockWifiInfo.getSSID()).thenReturn("dummy_ssid");
        int ip = 45 << 8;
        ip = (ip + 123) << 8;
        ip = (ip + 168) << 8;
        ip = ip + 192;
        when(mockWifiInfo.getIpAddress()).thenReturn(ip);
        when(mockWifiInfo.getLinkSpeed()).thenReturn(866);

        when(mockWifiManager.getConnectionInfo()).thenReturn(mockWifiInfo);

        ConnectedWifi connectedWifi = WifiUtil.getConnectedWifi(context);
        assertThat(connectedWifi.getSsid(), is("dummy_ssid"));
        assertThat(connectedWifi.getIpAddress(), is("192.168.123.45"));
        assertThat(connectedWifi.getLinkSpeed(), is(866));
    }
}
