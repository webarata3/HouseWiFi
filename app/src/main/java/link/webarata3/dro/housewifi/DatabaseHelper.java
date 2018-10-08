package link.webarata3.dro.housewifi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ssid.db";

    private static final int DATABASE_VERSION = 1;

    private static final String[] INIT_DATA = {
            "INSERT INTO ssid(ssid) VALUES('a_kappa_wifi2')",
            "INSERT INTO ssid(ssid) VALUES('g_kappa_wifi2')",
            "INSERT INTO ssid(ssid) VALUES('a_kappa_wifi2f')",
            "INSERT INTO ssid(ssid) VALUES('g_kappa_wifi2f')"
    };

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE ssid("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "ssid TEXT"
                + ")";

        db.execSQL(ddl);

        for (String initData : INIT_DATA) {
            db.execSQL(initData);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}