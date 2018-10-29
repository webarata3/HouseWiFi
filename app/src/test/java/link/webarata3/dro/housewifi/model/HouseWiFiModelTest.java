package link.webarata3.dro.housewifi.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import link.webarata3.dro.housewifi.dao.SettingServiceTest;
import link.webarata3.dro.housewifi.dao.SsidServiceTest;

import static link.webarata3.dro.housewifi.model.HouseWiFiModel.Event.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HouseWiFiModelTest {
    @Mock
    private Context context;


    private HouseWiFiModel model;
    @Mock
    private SQLiteDatabase mockDb;

    @Before
    public void setup() {
        model = HouseWiFiModel.getInstance(new SettingServiceTest(), new SsidServiceTest());
    }

    @Test
    public void test_getDefaultInstance() {
        HouseWiFiModel firstInstance = HouseWiFiModel.getDefaultInstance(context);
        assertThat(firstInstance, is(notNullValue()));
        // 2回動かして1回目と同じインスタンスが取得できているかを確認する
        HouseWiFiModel secondInstance = HouseWiFiModel.getDefaultInstance(context);
        assertThat(secondInstance, is(notNullValue()));
        assertThat(firstInstance == secondInstance, is(true));
    }

    @Test
    public void test_checkFirstAccess() {
        assertThat(model.checkFirstAccess(), is(false));
    }

    @Test
    public void test_saveNotFirstAccess() {
        model.saveNotFirstAccess();
    }

    @Test
    public void test_is_set_acceptPermission() {
        model.setAcceptPermission(true);

        assertThat(model.isAcceptPermission(), is(true));
    }

    @Test
    public void test_get_set_ssidList() {
        List<Ssid> ssidList = new ArrayList<>();
        ssidList.add(new Ssid("dummy"));

        model.setSsidList(ssidList);
        List<Ssid> resultSsidList = model.getSsidList();

        assertThat(resultSsidList, is(notNullValue()));
        assertThat(resultSsidList.size(), is(1));
        assertThat(resultSsidList.get(0).getSsid(), is("dummy"));
    }

    @Test
    public void test_readAllSsid() {
        HouseWiFiModel.HouseWifiObserver mockObserver = mock(HouseWiFiModel.HouseWifiObserver.class);
        model.addObserver(mockObserver);

        model.readAllSsid();

        verify(mockObserver, timeout(2000).times(1)).update(UPDATE_LIST);
    }

    @Test
    public void test_registerSsid() {
        HouseWiFiModel.HouseWifiObserver mockObserver = mock(HouseWiFiModel.HouseWifiObserver.class);
        model.addObserver(mockObserver);

        model.registerSsid(new Ssid("dummy"));

        verify(mockObserver, timeout(1000).times(1)).update(REGISTER);
    }
}
