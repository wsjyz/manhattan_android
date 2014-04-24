package com.ivan.android.manhattanenglish.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * @author: Ivan Vigoss
 * Date: 14-3-6
 * Time: PM2:29
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private final static String DB_NAME = "/sdcard/manhattan.db";

    private final static int DEFAULT_VERSION = 1;

    private static DatabaseHelper instance;

    private Context context;


    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DEFAULT_VERSION);
        this.context = context;
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
