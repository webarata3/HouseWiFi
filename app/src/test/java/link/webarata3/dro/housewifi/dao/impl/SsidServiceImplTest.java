package link.webarata3.dro.housewifi.dao.impl;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.dao.SsidService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class SsidServiceImplTest {
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void test_readAll() {
        SsidService ssidService = new SsidServiceImpl(context);
        SsidService.CallbackReadAll mockCallBackReadAll = mock(SsidService.CallbackReadAll.class);

        ssidService.readAll(mockCallBackReadAll);

        verify(mockCallBackReadAll, timeout(1000).times(1)).execute(any());
    }
}
