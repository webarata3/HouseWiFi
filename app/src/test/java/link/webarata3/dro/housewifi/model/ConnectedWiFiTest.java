package link.webarata3.dro.housewifi.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ConnectedWiFiTest {
    @Test
    public void test_get() {
        int ip = 45 << 8;
        ip = (ip + 123) << 8;
        ip = (ip + 168) << 8;
        ip = ip + 192;

        ConnectedWiFi connectedWiFi = new ConnectedWiFi("test_ssid", ip, 256);

        assertThat(connectedWiFi.getSsid(), is("test_ssid"));
        assertThat(connectedWiFi.getIpAddress(), is("192.168.123.45"));
        assertThat(connectedWiFi.getLinkSpeed(), is(256));
    }
}
