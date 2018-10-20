package link.webarata3.dro.housewifi.model;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class HouseWiFiModelTest {
    Context mockContext;

    @Before
    public void setUp() {
        mockContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void test_level0() {
        HouseWiFiModel houseWiFiModel = HouseWiFiModel.getInstance();
        houseWiFiModel.addObserver(event -> {
            assertThat(event, is(HouseWiFiModel.Event.updateList));

            List<Ssid> ssidList = houseWiFiModel.getSsidList();
            assertThat(ssidList, is(notNullValue()));
        });

        houseWiFiModel.readAllSsid(mockContext);
    }

    @Test
    public void test2() {
        HouseWiFiModel houseWiFiModel = HouseWiFiModel.getInstance();
        HouseWiFiModel.HouseWifiObserver observer = mock(HouseWiFiModel.HouseWifiObserver.class);
        houseWiFiModel.addObserver(observer);

        houseWiFiModel.readAllSsid(mockContext);

        verify(observer, timeout(100).times(1)).update(HouseWiFiModel.Event.updateList);
    }
}
