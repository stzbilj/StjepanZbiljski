package hr.math.web.ponovljenikolokvij;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by stjepan on 2/23/18.
 */

public class DBAdapter {
    static final String KEY_ROWID = "id";
    static final String KEY_NAME = "ime";
    static final String KEY_EMAIL = "email";
    static final String KEY_SALARY = "placa";

    static final String KEY_ROWIDP = "id_mjesta";
    static final String KEY_LEVEL = "nivo_odgovornosti";
    static final String KEY_MIN_SALARY = "minimalna_placa";

    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE1 = "zaposlenik";
    static final String DATABASE_TABLE2 = "radnomjesto";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE1 =
            "create table zaposlenik (id integer primary key autoincrement, "
                    + "ime text not null, email text not null, placa integer);";
    static final String DATABASE_CREATE2 =
            "create table radnomjesto (id_mjesta integer primary key autoincrement, "
                    + "nivo_odgovornosti integer, minimalna_placa integer);";


    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);
                //Add workers
                ContentValues initialValues = new ContentValues();
                initialValues.put(KEY_NAME, "Marko");
                initialValues.put(KEY_EMAIL, "marko@work.hr");
                initialValues.put(KEY_SALARY, 2000);
                db.insert(DATABASE_TABLE1, null, initialValues);
                initialValues = new ContentValues();
                initialValues.put(KEY_NAME, "Ivan");
                initialValues.put(KEY_EMAIL, "ivan@work.hr");
                initialValues.put(KEY_SALARY, 3000);
                db.insert(DATABASE_TABLE1, null, initialValues);
                initialValues = new ContentValues();
                initialValues.put(KEY_NAME, "Nina");
                initialValues.put(KEY_EMAIL, "nina@work.hr");
                initialValues.put(KEY_SALARY, 12000);
                db.insert(DATABASE_TABLE1, null, initialValues);
                initialValues = new ContentValues();
                initialValues.put(KEY_NAME, "Marija");
                initialValues.put(KEY_EMAIL, "marija@work.hr");
                initialValues.put(KEY_SALARY, 5000);
                db.insert(DATABASE_TABLE1, null, initialValues);
                initialValues = new ContentValues();
                initialValues.put(KEY_NAME, "Petar");
                initialValues.put(KEY_EMAIL, "petar@work.hr");
                initialValues.put(KEY_SALARY, 7600);
                db.insert(DATABASE_TABLE1, null, initialValues);
                //Add workstation
                initialValues = new ContentValues();
                initialValues.put(KEY_LEVEL, 1);
                initialValues.put(KEY_MIN_SALARY, 1000);
                db.insert(DATABASE_TABLE2, null, initialValues);
                initialValues = new ContentValues();
                initialValues.put(KEY_LEVEL, 3);
                initialValues.put(KEY_MIN_SALARY, 5000);
                db.insert(DATABASE_TABLE2, null, initialValues);
                initialValues = new ContentValues();
                initialValues.put(KEY_LEVEL, 5);
                initialValues.put(KEY_MIN_SALARY, 6500);
                db.insert(DATABASE_TABLE2, null, initialValues);
                initialValues = new ContentValues();
                initialValues.put(KEY_LEVEL, 7);
                initialValues.put(KEY_MIN_SALARY, 9000);
                db.insert(DATABASE_TABLE2, null, initialValues);
                initialValues = new ContentValues();
                initialValues.put(KEY_LEVEL, 10);
                initialValues.put(KEY_MIN_SALARY, 10000);
                db.insert(DATABASE_TABLE2, null, initialValues);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS zaposlenik");
            db.execSQL("DROP TABLE IF EXISTS radnomjesto");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---deletes a particular worker---
    public boolean deleteWorker(long rowId)
    {
        return db.delete(DATABASE_TABLE1, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---deletes a particular work station---
    public boolean deleteWorkStation(long rowId)
    {
        return db.delete(DATABASE_TABLE2, KEY_ROWIDP + "=" + rowId, null) > 0;
    }

    //---retrieves all the workers---
    public Cursor getAllWorkers()
    {
        return db.query(DATABASE_TABLE1, new String[] {KEY_ROWID, KEY_NAME,
                KEY_EMAIL, KEY_SALARY}, null, null, null, null, null);
    }

    //---retrieves all the work stations---
    public Cursor getAllWorkStations()
    {
        return db.query(DATABASE_TABLE2, new String[] {KEY_ROWIDP, KEY_LEVEL,
                KEY_MIN_SALARY}, null, null, null, null, null);
    }

    //---retrieves a highest paid worker---
    public Cursor getWorker() throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE1, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_EMAIL}, null, null,
                        null, null, KEY_SALARY+" DESC", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
