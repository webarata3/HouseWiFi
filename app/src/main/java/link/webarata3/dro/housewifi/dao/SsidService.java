package link.webarata3.dro.housewifi.dao;

import java.util.List;

import link.webarata3.dro.housewifi.model.Ssid;

public interface SsidService {
    void readAll(SsidService.CallbackReadAll callbackReadAll);

    void register(Ssid ssid, CallbackRegister callbackRegister);

    interface CallbackReadAll {
        void execute(List<Ssid> ssidList);
    }

    interface CallbackRegister {
        void execute();
    }
}
