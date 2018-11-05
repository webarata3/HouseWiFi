package link.webarata3.dro.housewifi.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.dao.SsidService;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;
import link.webarata3.dro.housewifi.model.Ssid;

public class SsidServiceImpl implements SsidService {
    private DatabaseHelper helper;

    public SsidServiceImpl(Context context) {
        helper = new DatabaseHelper(context);
    }

    protected SsidDao createSsidDao(SQLiteDatabase db) {
        return new SsidDao(db);
    }

    @Override
    public void readAll(CallbackReadAll callbackReadAll) {
        helper.executeQuery(db -> {
            SsidDao ssidDao = createSsidDao(db);

            callbackReadAll.execute(ssidDao.selectAll());
        });
    }

    @Override
    public void register(Ssid ssid, CallbackRegister callbackRegister) {
        helper.executeInTransaction(db -> {
            SsidDao ssidDao = createSsidDao(db);

            if (ssidDao.alreadyRegisterd(ssid.getSsid())) {
                callbackRegister.execute(true);
                return;
            }

            ssidDao.insert(ssid);

            callbackRegister.execute(false);
        });
    }
}
