package link.webarata3.dro.housewifi.dao;

public class SettingServiceTestImpl implements SettingService {
    @Override
    public boolean checkFirstAccess() {
        return false;
    }

    @Override
    public void saveNotFirstAccess() {
    }
}
