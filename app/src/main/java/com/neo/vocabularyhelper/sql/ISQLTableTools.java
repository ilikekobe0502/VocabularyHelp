package com.neo.vocabularyhelper.sql;

import android.database.Cursor;

import java.util.List;

/**
 * Created by neo_mac on 2017/7/1.
 */

public interface ISQLTableTools {
    void close();

    long insert(Object object);

    boolean update(Object object);

    boolean delete(Object object);

    List<Object> getAll();

    List<Object> getByCondition(Object object);

    Object getRecord(Cursor cursor);
}
