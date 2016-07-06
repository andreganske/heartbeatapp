package com.andreganske.heartbeat;

import android.provider.BaseColumns;

/**
 * Created by Andre on 03/07/2016.
 */
public class HeartbeatContract {

    public static final String DB_NAME = "HEARTBEAT_DB";
    public static final int DB_VERSION = 1;

    public class HeartbeatEntry implements BaseColumns {
        public static final String TABLE = "HEARTBEAT";
        public static final String COL_HEARTBEAT_SYSTOLIC = "SYSTOLIC";
        public static final String COL_HEARTBEAT_DIASTOLIC = "DIASTOLIC";
        public static final String COL_HEARTBEAT_CREATED_AT = "CREATED_AT";
    }
}
