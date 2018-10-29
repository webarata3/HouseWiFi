package link.webarata3.dro.housewifi.dao;

import java.util.ArrayList;
import java.util.List;

import link.webarata3.dro.housewifi.model.Ssid;

public class SsidServiceAlreadyRegisteredTest implements SsidService {
    @Override
    public void readAll(CallbackReadAll callbackReadAll) {
        List<Ssid> ssidList = new ArrayList<>();
        ssidList.add(new Ssid("dummy"));

        callbackReadAll.execute(ssidList);
    }

    @Override
    public void register(Ssid ssid, CallbackRegister callbackRegister) {
        callbackRegister.execute(true);
    }
}
