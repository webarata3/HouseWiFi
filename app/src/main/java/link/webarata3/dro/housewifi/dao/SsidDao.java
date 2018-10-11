package link.webarata3.dro.housewifi.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import link.webarata3.dro.housewifi.model.Ssid;

public class SsidDao {
    private SQLiteDatabase db;

    public SsidDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(Ssid ssid) {
        String INSERT_SQL = "INSERT INTO ssid(ssid) VALUES(?)";

        SQLiteStatement stmt = db.compileStatement(INSERT_SQL);
        stmt.bindString(1, ssid.getSsid());
        stmt.executeInsert();
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
