package link.webarata3.dro.housewifi.dao;

public class SettingServiceTest implements SettingService {
    @Override
    public boolean checkFirstAccess() {
        return false;
    }

    @Override
    public void saveNotFirstAccess() {
    }
}
