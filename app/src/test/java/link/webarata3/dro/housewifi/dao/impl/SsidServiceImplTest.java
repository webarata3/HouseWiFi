package link.webarata3.dro.housewifi.dao.impl;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.dao.SsidService;
import link.webarata3.dro.housewifi.model.Ssid;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SsidServiceImplTest {
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void test_readAll() {
        List<Ssid> dummySsidList = new ArrayList<>();
        dummySsidList.add(new Ssid());

        SsidServiceImpl spySsidService = spy(new SsidServiceImpl(context));
        SsidDao mockSsidDao = mock(SsidDao.class);
        when(mockSsidDao.selectAll()).thenReturn(dummySsidList);
        when(spySsidService.createSsidDao(any())).thenReturn(mockSsidDao);

        SsidService.CallbackReadAll mockCallBackReadAll = mock(SsidService.CallbackReadAll.class);
        spySsidService.readAll(mockCallBackReadAll);

        verify(mockCallBackReadAll, timeout(1000).times(1)).execute(dummySsidList);
    }

    @Test
    public void test_register_not_exist() {
        final String TEST_SSID = "test_ssid";

        SsidServiceImpl spySsidService = spy(new SsidServiceImpl(context));
        SsidDao mockSsidDao = mock(SsidDao.class);
        when(mockSsidDao.alreadyRegisterd(TEST_SSID)).thenReturn(false);
        Ssid ssid = new Ssid();
        ssid.setSsid(TEST_SSID);
        doNothing().when(mockSsidDao).insert(ssid);
        when(spySsidService.createSsidDao(any())).thenReturn(mockSsidDao);

        SsidService.CallbackRegister mockCallbackRegister = mock(SsidService.CallbackRegister.class);
        spySsidService.register(ssid, mockCallbackRegister);

        verify(mockSsidDao, timeout(1000).times(1)).insert(ssid);
        verify(mockCallbackRegister, timeout(1000).times(1)).execute(false);
    }


    @Test
    public void test_register_exist() {
        final String TEST_SSID = "test_ssid";

        SsidServiceImpl spySsidService = spy(new SsidServiceImpl(context));
        SsidDao mockSsidDao = mock(SsidDao.class);
        when(mockSsidDao.alreadyRegisterd(TEST_SSID)).thenReturn(true);
        // insertが呼ばれていないことを確認するためのmock
        Ssid ssid = new Ssid();
        ssid.setSsid(TEST_SSID);
        doNothing().when(mockSsidDao).insert(ssid);
        when(spySsidService.createSsidDao(any())).thenReturn(mockSsidDao);

        SsidService.CallbackRegister mockCallbackRegister = mock(SsidService.CallbackRegister.class);
        spySsidService.register(ssid, mockCallbackRegister);

        verify(mockSsidDao, timeout(1000).times(0)).insert(ssid);
        verify(mockCallbackRegister, timeout(1000).times(1)).execute(true);
    }
}
