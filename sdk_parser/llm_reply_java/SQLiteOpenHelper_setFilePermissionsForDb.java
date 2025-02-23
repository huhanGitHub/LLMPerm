import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;

public class SQLiteOpenHelper_setFilePermissionsForDb {

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "testDb";
        private static final int DATABASE_VERSION = 1;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE test (id INTEGER PRIMARY KEY, name TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS test");
            onCreate(db);
        }
    }

    private DatabaseHelper dbHelper;
    private Context context;

    public SQLiteOpenHelper_setFilePermissionsForDb(Context context) {
        this.context = context;
    }

    public void test_SQLiteOpenHelper_setFilePermissionsForDb() {
        dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        File databasePath = context.getDatabasePath(DatabaseHelper.DATABASE_NAME);
        databasePath.setReadable(true, true);
        databasePath.setWritable(true, true);

        db.close();
    }
}