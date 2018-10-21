package link.webarata3.dro.housewifi.dao.impl;

import android.content.Context;

import link.webarata3.dro.housewifi.dao.SsidDao;
import link.webarata3.dro.housewifi.dao.SsidService;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;
import link.webarata3.dro.housewifi.model.Ssid;

public class SsidServiceImpl implements SsidService {
    private Context context;

    public SsidServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void readAll(CallbackReadAll callbackReadAll) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.executeInTransaction(db -> {
            SsidDao ssidDao = new SsidDao(db);

            callbackReadAll.execute(ssidDao.selectAll());
        });
    }

    @Override
    public void register(Ssid ssid, CallbackRegister callbackRegister) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.executeQuery(db -> {
            SsidDao ssidDao = new SsidDao(db);
            ssidDao.insert(ssid);

            callbackRegister.execute();
        });
    }
}
