package link.webarata3.dro.housewifi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SsidDao {
    private SQLiteDatabase db;

    public SsidDao(SQLiteDatabase db) {
        this.db = db;
    }

    private Ssid selectOne(Cursor cursor) {
        Ssid ssid = new Ssid();
        ssid.setSsid(cursor.getString(cursor.getColumnIndex("ssid")));

        return ssid;
    }

    public List<Ssid> selectAll() {
        List<Ssid> ssidList = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT ssid FROM ssid", null)) {
            while (cursor.moveToNext()) {
                Ssid ssid = selectOne(cursor);
                ssidList.add(ssid);
            }
        }
        return ssidList;
    }
}
