package link.webarata3.dro.housewifi.dao.impl;

import android.content.Context;

import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.dao.SsidService;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;
import link.webarata3.dro.housewifi.model.Ssid;

public class SsidServiceImpl implements SsidService {
    private DatabaseHelper helper;

    public SsidServiceImpl(Context context) {
        helper = new DatabaseHelper(context);
    }

    @Override
    public void readAll(CallbackReadAll callbackReadAll) {
        helper.executeInTransaction(db -> {
            SsidDao ssidDao = new SsidDao(db);

            callbackReadAll.execute(ssidDao.selectAll());
        });
    }

    @Override
    public void register(Ssid ssid, CallbackRegister callbackRegister) {
        helper.executeQuery(db -> {
            SsidDao ssidDao = new SsidDao(db);

            if (ssidDao.alreadyRegisterd(ssid.getSsid())) {
                callbackRegister.execute(true);
                return;
            }

            ssidDao.insert(ssid);

            callbackRegister.execute(false);
        });
    }
}
