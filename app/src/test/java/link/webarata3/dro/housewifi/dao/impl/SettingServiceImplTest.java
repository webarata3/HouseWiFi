package link.webarata3.dro.housewifi.dao.impl;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.dao.SettingService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SettingServiceImplTest {
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void test_checkFirstAccess_true() {
        Context mockContext = mock(Context.class);

        SharedPreferences prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        when(mockContext.getSharedPreferences("settings", Context.MODE_PRIVATE)).thenReturn(prefs);

        SettingService settingService = new SettingServiceImpl(mockContext);
        assertThat(settingService.checkFirstAccess(), is(true));
    }

    @Test
    public void test_checkFirstAccess_false (){
        Context mockContext = mock(Context.class);

        SharedPreferences prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        when(mockContext.getSharedPreferences("settings", Context.MODE_PRIVATE)).thenReturn(prefs);

        SettingService settingService = new SettingServiceImpl(mockContext);
        settingService.saveNotFirstAccess();
        assertThat(settingService.checkFirstAccess(), is(false));
    }
}
