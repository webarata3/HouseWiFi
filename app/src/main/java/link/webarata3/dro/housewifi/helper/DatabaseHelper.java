package link.webarata3.dro.housewifi.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import link.webarata3.dro.housewifi.AppExecutors;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ssid.db";

    private static final int DATABASE_VERSION = 1;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void executeInTransaction(CallbackSql callbackSql) {
        SQLiteDatabase db = getWritableDatabase();

        AppExecutors.getInstance().diskIo().execute(() -> {
            db.beginTransaction();
            try {
                callbackSql.execute(db);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        });
    }

    public void executeQuery(CallbackSql callbackSql) {
        SQLiteDatabase db = getReadableDatabase();

        AppExecutors.getInstance().diskIo().execute(() -> callbackSql.execute(db));
    }
}
