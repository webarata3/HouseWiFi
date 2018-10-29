package link.webarata3.dro.housewifi.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SsidTest {
    @Test
    public void test_id() {
        Ssid ssid = new Ssid();
        ssid.setId(123);
        assertThat(ssid.getId(), is(123));
    }

    @Test
    public void test_ssid() {
        Ssid ssid = new Ssid();
        ssid.setSsid("test_ssid");
        assertThat(ssid.getSsid(), is("test_ssid"));
    }
}
