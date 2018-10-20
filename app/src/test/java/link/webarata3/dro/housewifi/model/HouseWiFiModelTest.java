package link.webarata3.dro.housewifi.model;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class HouseWiFiModelTest {
    Context mockContext;

    @Before
    public void setUp() {
        mockContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void test_readAll() {
        HouseWiFiModel houseWiFiModel = HouseWiFiModel.getInstance();
        HouseWiFiModel.HouseWifiObserver observer = mock(HouseWiFiModel.HouseWifiObserver.class);
        houseWiFiModel.addObserver(observer);

        houseWiFiModel.readAllSsid(mockContext);

        verify(observer, timeout(10000).times(1)).update(HouseWiFiModel.Event.UPDATE_LIST);
    }

    @Test
    public void test_registerSsid() {
        HouseWiFiModel houseWiFiModel = HouseWiFiModel.getInstance();
        HouseWiFiModel.HouseWifiObserver observer = mock(HouseWiFiModel.HouseWifiObserver.class);
        houseWiFiModel.addObserver(observer);

        houseWiFiModel.registerSsid(mockContext, new Ssid("dummy"));

        verify(observer, timeout(10000).times(1)).update(HouseWiFiModel.Event.REGISTER);
    }
}
