package link.webarata3.dro.housewifi.model;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import link.webarata3.dro.housewifi.dao.SettingServiceTestImpl;
import link.webarata3.dro.housewifi.dao.SsidServiceAlreadyRegisteredTest;
import link.webarata3.dro.housewifi.dao.SsidServiceTestImpl;

import static link.webarata3.dro.housewifi.model.HouseWifiModel.Event.ALREADY_REGISTERED;
import static link.webarata3.dro.housewifi.model.HouseWifiModel.Event.REGISTER;
import static link.webarata3.dro.housewifi.model.HouseWifiModel.Event.UPDATE_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HouseWifiModelTest {
    @Mock
    private Context context;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        // シングルトンのオブジェクトを無理やりnullにする
        Field f = HouseWifiModel.class.getDeclaredField("model");
        f.setAccessible(true);
        f.set(null, null);
    }

    private HouseWifiModel getDefaultModel() {
        return HouseWifiModel.getInstance(new SettingServiceTestImpl(), new SsidServiceTestImpl());
    }

    @Test
    public void test_getDefaultInstance() {
        HouseWifiModel firstInstance = HouseWifiModel.getDefaultInstance(context);
        assertThat(firstInstance, is(notNullValue()));
        // 2回動かして1回目と同じインスタンスが取得できているかを確認する
        HouseWifiModel secondInstance = HouseWifiModel.getDefaultInstance(context);
        assertThat(secondInstance, is(notNullValue()));
        assertThat(firstInstance == secondInstance, is(true));
    }

    @Test
    public void test_checkFirstAccess() {
        HouseWifiModel model = getDefaultModel();

        assertThat(model.checkFirstAccess(), is(false));
    }

    @Test
    public void test_saveNotFirstAccess() {
        HouseWifiModel model = getDefaultModel();

        model.saveNotFirstAccess();
    }

    @Test
    public void test_is_set_acceptPermission() {
        HouseWifiModel model = getDefaultModel();

        model.setAcceptPermission(true);

        assertThat(model.isAcceptPermission(), is(true));
    }

    @Test
    public void test_get_set_ssidList() {
        List<Ssid> ssidList = new ArrayList<>();
        ssidList.add(new Ssid("dummy"));

        HouseWifiModel model = getDefaultModel();
        model.setSsidList(ssidList);
        List<Ssid> resultSsidList = model.getSsidList();

        assertThat(resultSsidList, is(notNullValue()));
        assertThat(resultSsidList.size(), is(1));
        assertThat(resultSsidList.get(0).getSsid(), is("dummy"));
    }

    @Test
    public void test_readAllSsid() {
        HouseWifiModel model = getDefaultModel();
        HouseWifiModel.HouseWifiObserver mockObserver = mock(HouseWifiModel.HouseWifiObserver.class);
        model.addObserver(mockObserver);

        model.readAllSsid();

        verify(mockObserver, timeout(2000).times(1)).update(UPDATE_LIST);
    }

    @Test
    public void test_registerSsid_REGISTER() {
        HouseWifiModel model = getDefaultModel();
        HouseWifiModel.HouseWifiObserver mockObserver = mock(HouseWifiModel.HouseWifiObserver.class);
        model.addObserver(mockObserver);

        model.registerSsid(new Ssid("dummy"));

        verify(mockObserver, timeout(1000).times(1)).update(REGISTER);
    }

    @Test
    public void test_registerSsid_ALREADY_REGISTERED() {
        HouseWifiModel model = HouseWifiModel.getInstance(new SettingServiceTestImpl(), new SsidServiceAlreadyRegisteredTest());

        HouseWifiModel.HouseWifiObserver mockObserver = mock(HouseWifiModel.HouseWifiObserver.class);
        model.addObserver(mockObserver);

        model.registerSsid(new Ssid("dummy"));

        verify(mockObserver, timeout(1000).times(1)).update(ALREADY_REGISTERED);
    }
}
