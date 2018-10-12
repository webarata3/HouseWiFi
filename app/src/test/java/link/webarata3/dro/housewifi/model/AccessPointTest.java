package link.webarata3.dro.housewifi.model;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
public class AccessPointTest {
    @Test
    public void test_level0() {
        AccessPoint accessPoint = new AccessPoint("ssid", 0);
        assertThat(accessPoint.getQuality(), is(100));
    }

    @Test
    public void test_levelMinus49() {
        AccessPoint accessPoint = new AccessPoint("ssid", -49);
        assertThat(accessPoint.getQuality(), is(100));
    }

    @Test
    public void test_levelMinus50() {
        AccessPoint accessPoint = new AccessPoint("ssid", -50);
        assertThat(accessPoint.getQuality(), is(100));
    }

    @Test
    public void test_levelMinus51() {
        AccessPoint accessPoint = new AccessPoint("ssid", -51);
        assertThat(accessPoint.getQuality(), is(98));
    }

    @Test
    public void test_levelMinus99() {
        AccessPoint accessPoint = new AccessPoint("ssid", -99);
        assertThat(accessPoint.getQuality(), is(2));
    }

    @Test
    public void test_levelMinus100() {
        AccessPoint accessPoint = new AccessPoint("ssid", -100);
        assertThat(accessPoint.getQuality(), is(0));
    }

    @Test
    public void test_levelMinus101() {
        AccessPoint accessPoint = new AccessPoint("ssid", -101);
        assertThat(accessPoint.getQuality(), is(0));
    }
}
