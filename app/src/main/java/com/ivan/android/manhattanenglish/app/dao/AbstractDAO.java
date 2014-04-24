package com.ivan.android.manhattanenglish.app.dao;

import android.content.Context;

import com.ivan.android.manhattanenglish.app.utils.DatabaseHelper;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

/**
 * @author: Ivan Vigoss
 * Date: 14-4-2
 * Time: AM9:51
 */
public class AbstractDAO {
    protected final static String TAG = "ormlite";

    protected <T, ID> Dao<T, ID> createDao(Context context, Class<T> daoClass) throws SQLException {
        return DaoManager.createDao(new AndroidConnectionSource(DatabaseHelper.getInstance(context)), daoClass);

    }
}
