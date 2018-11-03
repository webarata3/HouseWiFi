package link.webarata3.dro.housewifi.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;
import link.webarata3.dro.housewifi.model.Ssid;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SsidDaoTest {
    DatabaseHelper dbHelper;

    @Before
    public void setUp() {
        dbHelper = new DatabaseHelper(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        dbHelper.close();
    }

    @Test
    public void test_insertSsid() {
        final String TEST_DATA = "wifi_access_point";

        SsidDao ssidDao = new SsidDao(dbHelper.getReadableDatabase());
        Ssid ssid = new Ssid();
        ssid.setSsid(TEST_DATA);
        ssidDao.insert(ssid);

        List<Ssid> ssidList = ssidDao.selectAll();
        assertThat(ssidList.size(), is(1));

        Ssid resultSsid = ssidList.get(0);
        assertThat(resultSsid, is(notNullValue()));
        assertThat(resultSsid.getSsid(), is(TEST_DATA));
    }

    @Test
    public void test_alreadyRegistered() {
        final String TEST_DATA = "wifi_access_point";

        SsidDao ssidDao = new SsidDao(dbHelper.getReadableDatabase());
        assertThat(ssidDao.alreadyRegisterd(TEST_DATA), is(false));

        Ssid ssid = new Ssid();
        ssid.setSsid(TEST_DATA);
        ssidDao.insert(ssid);

        assertThat(ssidDao.alreadyRegisterd(TEST_DATA), is(true));
    }
}
