package link.webarata3.dro.housewifi.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HouseWiFiModelTest {
    @Mock
    private Context mockContext;

    @Mock
    private SQLiteDatabase mockDb;

    @Test
    public void test_checkFirstAccess() {
        SharedPreferences mockPrefs = mock(SharedPreferences.class);
        when(mockPrefs.getBoolean("firstAccess", true)).thenReturn(true);

        HouseWiFiModel mockHouseWiFiModel = spy(HouseWiFiModel.getInstance());
        doReturn(mockPrefs).when(mockHouseWiFiModel).createSharedPreferences(mockContext);

        boolean isFirstAccess = mockHouseWiFiModel.checkFirstAccess(mockContext);
        assertThat(isFirstAccess, is(true));
    }

    @Test
    public void test_saveNotFirstAccess() {
        SharedPreferences mockPrefs = mock(SharedPreferences.class);
        SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
        when(mockPrefs.edit()).thenReturn(mockEditor);
        when(mockEditor.putBoolean("firstAccess", false)).thenReturn(mockEditor);

        HouseWiFiModel mockHouseWiFiModel = spy(HouseWiFiModel.getInstance());
        doReturn(mockPrefs).when(mockHouseWiFiModel).createSharedPreferences(mockContext);

        mockHouseWiFiModel.saveNotFirstAccess(mockContext);

        verify(mockEditor).apply();
    }

    @Test
    public void test_is_set_acceptPermission() {
        HouseWiFiModel houseWiFiModel = HouseWiFiModel.getInstance();
        houseWiFiModel.setAcceptPermission(true);

        assertThat(houseWiFiModel.isAcceptPermission(), is(true));
    }

    @Test
    public void test_get_set_ssidList() {
        List<Ssid> ssidList = new ArrayList<>();
        ssidList.add(new Ssid("dummy"));

        HouseWiFiModel houseWiFiModel = HouseWiFiModel.getInstance();
        houseWiFiModel.setSsidList(ssidList);
        List<Ssid> retSsidList = houseWiFiModel.getSsidList();
        assertThat(retSsidList, is(notNullValue()));
        assertThat(retSsidList.size(), is(1));
        assertThat(retSsidList.get(0).getSsid(), is("dummy"));
    }

    @Test
    public void test_readAllSsid_context() {
        HouseWiFiModel mockHouseWiFiModel = spy(HouseWiFiModel.getInstance());
        HouseWiFiModel.HouseWifiObserver observer = mock(HouseWiFiModel.HouseWifiObserver.class);
        mockHouseWiFiModel.addObserver(observer);

        List<Ssid> ssidList = new ArrayList<>();
        ssidList.add(new Ssid("dummy"));

        SsidDao mockSsidDao = mock(SsidDao.class, withSettings().useConstructor(mockDb));
        doReturn(mockSsidDao).when(mockHouseWiFiModel).createSsidDao(mockDb);
        DatabaseHelper mockHelper = mock(DatabaseHelper.class, withSettings().useConstructor(mockContext));
        doReturn(mockHelper).when(mockHouseWiFiModel).createDatabaseHelper(mockContext);
        when(mockSsidDao.selectAll()).thenReturn(ssidList);

        mockHouseWiFiModel.readAllSsid(mockContext);

        verify(observer, timeout(1000).times(1)).update(HouseWiFiModel.Event.UPDATE_LIST);
    }

    @Test
    public void test_readAllSsid_db() {
        HouseWiFiModel mockHouseWiFiModel = spy(HouseWiFiModel.getInstance());

        List<Ssid> ssidList = new ArrayList<>();
        ssidList.add(new Ssid("dummy"));

        SsidDao mockSsidDao = mock(SsidDao.class, withSettings().useConstructor(mockDb));
        when(mockSsidDao.selectAll()).thenReturn(ssidList);
        doReturn(mockSsidDao).when(mockHouseWiFiModel).createSsidDao(mockDb);

        mockHouseWiFiModel.readAllSsid(mockDb);

        List<Ssid> resultSsidList = HouseWiFiModel.getInstance().getSsidList();
        assertThat(resultSsidList, is(notNullValue()));
        assertThat(resultSsidList.size(), is(1));
        assertThat(resultSsidList.get(0).getSsid(), is("dummy"));
    }

    @Test
    public void test_registerSsid_db_ssid() {
        HouseWiFiModel mockHouseWiFiModel = spy(HouseWiFiModel.getInstance());

        Ssid ssid = new Ssid("dummy");

        SsidDao mockSsidDao = mock(SsidDao.class, withSettings().useConstructor(mockDb));
        doNothing().when(mockSsidDao).insert(ssid);
        doReturn(mockSsidDao).when(mockHouseWiFiModel).createSsidDao(mockDb);

        mockHouseWiFiModel.registerSsid(mockDb, ssid);

        verify(mockSsidDao).insert(ssid);
    }

    @Test
    public void test_registerSsid_context() {
        HouseWiFiModel mockHouseWiFiModel = spy(HouseWiFiModel.getInstance());
        HouseWiFiModel.HouseWifiObserver observer = mock(HouseWiFiModel.HouseWifiObserver.class);
        mockHouseWiFiModel.addObserver(observer);

        Ssid ssid = new Ssid("dummy");

        SsidDao mockSsidDao = mock(SsidDao.class, withSettings().useConstructor(mockDb));
        doNothing().when(mockSsidDao).insert(ssid);
        doReturn(mockSsidDao).when(mockHouseWiFiModel).createSsidDao(mockDb);
        doNothing().when(mockDb).beginTransaction();
        doNothing().when(mockDb).setTransactionSuccessful();
        doNothing().when(mockDb).endTransaction();

        mockHouseWiFiModel.registerSsid(mockContext, ssid);

        verify(mockDb, timeout(1000).times(1)).beginTransaction();
        verify(mockDb, timeout(1000).times(1)).setTransactionSuccessful();
        verify(mockDb, timeout(1000).times(1)).endTransaction();
        verify(observer, timeout(1000).times(1)).update(HouseWiFiModel.Event.REGISTER);
    }
}
