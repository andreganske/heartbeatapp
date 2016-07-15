package com.andreganske.heartbeat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 03/07/2016.
 */
public class HeartbeatDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "HeartbeatDBHelper";

    public HeartbeatDBHelper(Context context) {
        super(context, HeartbeatContract.DB_NAME, null, HeartbeatContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + HeartbeatContract.HeartbeatEntry.TABLE + " (" +
                HeartbeatContract.HeartbeatEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_SYSTOLIC + " INT NOT NULL," +
                HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_DIASTOLIC + " INT NOT NULL," +
                HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_CREATED_AT + " STRING NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HeartbeatContract.HeartbeatEntry.TABLE);
        onCreate(db);
    }

    public static void insert(SQLiteDatabase db, Heartbeat heartbeat) {
        ContentValues values = new ContentValues();

        values.put(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_SYSTOLIC, heartbeat.getSystolic());
        values.put(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_DIASTOLIC, heartbeat.getDiastolic());
        values.put(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_CREATED_AT, heartbeat.getCreatedAt());

        db.insertWithOnConflict(HeartbeatContract.HeartbeatEntry.TABLE,
                null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d(TAG, "Heartbeat " + heartbeat.toString() + " added!");
        db.close();
    }

    public static void update(SQLiteDatabase db, Heartbeat heartbeat) {
        ContentValues values = new ContentValues();

        values.put(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_SYSTOLIC, heartbeat.getSystolic());
        values.put(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_DIASTOLIC, heartbeat.getDiastolic());
        values.put(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_CREATED_AT, heartbeat.getCreatedAt());

        db.updateWithOnConflict(HeartbeatContract.HeartbeatEntry.TABLE, values,
                HeartbeatContract.HeartbeatEntry._ID + "=?",
                new String[]{heartbeat.getId().toString()},
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        Log.d(TAG, "Heartbeat " + heartbeat.toString() + " updated!");
    }

    public static void remove(SQLiteDatabase db, List<Heartbeat> heartbeats) {
        for (Heartbeat heartbeat : heartbeats) {
            remove(db, heartbeat);
        }
        db.close();
    }

    private static void remove(SQLiteDatabase db, Heartbeat heartbeat) {
        db.delete(HeartbeatContract.HeartbeatEntry.TABLE,
                HeartbeatContract.HeartbeatEntry._ID + "=?",
                new String[]{heartbeat.getId().toString()});
        Log.d(TAG, "Heartbeat " + heartbeat.toString() + " deleted!");
    }

    public static Heartbeat findById(SQLiteDatabase db, int id) {
        Heartbeat heartbeat = new Heartbeat();

        Cursor cursor = db.query(HeartbeatContract.HeartbeatEntry.TABLE,
                new String[]{
                        HeartbeatContract.HeartbeatEntry._ID,
                        HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_SYSTOLIC,
                        HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_DIASTOLIC,
                        HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_CREATED_AT,
                },
                HeartbeatContract.HeartbeatEntry._ID + "=?",
                new String[] {String.valueOf(id)},
                null, null, null);

        while (cursor.moveToNext()) {
            heartbeat.setId(cursor.getInt(cursor.getColumnIndex(HeartbeatContract.HeartbeatEntry._ID)));
            heartbeat.setSystolic(cursor.getInt(cursor.getColumnIndex(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_SYSTOLIC)));
            heartbeat.setDiastolic(cursor.getInt(cursor.getColumnIndex(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_DIASTOLIC)));
            heartbeat.setCreatedAt(cursor.getString(cursor.getColumnIndex(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_CREATED_AT)));

            Log.d(TAG, "Heartbeat " + heartbeat.toString() + " recovered!");
        }

        cursor.close();
        db.close();

        return heartbeat;
    }

    public static List<Heartbeat> fetchAllTasks(SQLiteDatabase db) {
        List<Heartbeat> heartbeats = new ArrayList<Heartbeat>();
        Heartbeat heartbeat;

        Cursor cursor = db.query(HeartbeatContract.HeartbeatEntry.TABLE,
                new String[]{
                        HeartbeatContract.HeartbeatEntry._ID,
                        HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_SYSTOLIC,
                        HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_DIASTOLIC,
                        HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_CREATED_AT,
                }, null, null, null, null, HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_CREATED_AT);

        while (cursor.moveToNext()) {
            heartbeat = new Heartbeat(
                    cursor.getInt(cursor.getColumnIndex(HeartbeatContract.HeartbeatEntry._ID)),
                    cursor.getInt(cursor.getColumnIndex(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_SYSTOLIC)),
                    cursor.getInt(cursor.getColumnIndex(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_DIASTOLIC)),
                    cursor.getString(cursor.getColumnIndex(HeartbeatContract.HeartbeatEntry.COL_HEARTBEAT_CREATED_AT)));

            Log.d(TAG, "Heartbeat " + heartbeat.toString() + " recovered!");
            heartbeats.add(heartbeat);
        }

        cursor.close();
        db.close();

        return heartbeats;
    }
}

