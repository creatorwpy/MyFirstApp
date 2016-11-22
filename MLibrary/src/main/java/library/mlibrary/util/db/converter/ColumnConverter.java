package library.mlibrary.util.db.converter;

import android.database.Cursor;

import library.mlibrary.util.db.sqlite.ColumnDbType;

public interface ColumnConverter<T> {

    T getFieldValue(final Cursor cursor, int index);

    Object fieldValue2DbValue(T fieldValue);

    ColumnDbType getColumnDbType();
}
