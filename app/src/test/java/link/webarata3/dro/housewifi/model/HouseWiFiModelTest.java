package link.webarata3.dro.housewifi.model;

import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import link.webarata3.dro.housewifi.dao.SsidService;
import link.webarata3.dro.housewifi.dao.SsidServiceTest;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HouseWiFiModelTest {
    @Mock
    private SQLiteDatabase mockDb;

    @Test
    public void test_checkFirstAccess() {
    }

    @Test
    public void test_saveNotFirstAccess() {
    }

    @Test
    public void test_is_set_acceptPermission() {
    }

    @Test
    public void test_get_set_ssidList() {
    }

    @Test
    public void test_readAllSsid() {
        HouseWiFiModel model = HouseWiFiModel.getInstance(new SsidServiceTest());
        HouseWiFiModel.HouseWifiObserver mockObserver = mock(HouseWiFiModel.HouseWifiObserver.class);
        model.addObserver(mockObserver);

        model.readAllSsid();

        verify(mockObserver, timeout(1000).times(1)).update(HouseWiFiModel.Event.UPDATE_LIST);
    }

    @Test
    public void test_registerSsid() {

    }
}
