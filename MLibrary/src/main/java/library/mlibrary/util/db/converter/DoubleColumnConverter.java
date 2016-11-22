package library.mlibrary.util.db.converter;

import android.database.Cursor;

import library.mlibrary.util.db.sqlite.ColumnDbType;


public class DoubleColumnConverter implements ColumnConverter<Double> {
    @Override
    public Double getFieldValue(final Cursor cursor, int index) {
        return cursor.isNull(index) ? null : cursor.getDouble(index);
    }

    @Override
    public Object fieldValue2DbValue(Double fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.REAL;
    }
}
