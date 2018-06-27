package com.example.user.barrons333wordapp.worddb.util;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 *  Developed by absak on 27/06/2018
 */

public class DateRoomConverter {

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
