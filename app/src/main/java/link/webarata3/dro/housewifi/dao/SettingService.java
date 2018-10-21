package link.webarata3.dro.housewifi.dao;

public interface SettingService {
    boolean checkFirstAccess();

    void saveNotFirstAccess();
}
