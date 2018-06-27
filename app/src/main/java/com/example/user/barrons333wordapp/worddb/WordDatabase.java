package com.example.user.barrons333wordapp.worddb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.user.barrons333wordapp.worddb.dao.WordDao;
import com.example.user.barrons333wordapp.worddb.model.Word;
import com.example.user.barrons333wordapp.worddb.util.Constants;
import com.example.user.barrons333wordapp.worddb.util.DateRoomConverter;

/**
 *  Developed by absak on 27/06/2018
 */

@Database(entities = { Word.class }, version = 1)
@TypeConverters({DateRoomConverter.class})
public abstract class WordDatabase extends RoomDatabase {

    public abstract WordDao getWordDao();


    private static WordDatabase wordDb;

    // synchronized is use to avoid concurrent access in multithreaded environment
    public static /*synchronized*/ WordDatabase getInstance(Context context) {
        if (null == wordDb) {
            wordDb = buildDatabaseInstance(context);
        }
        return wordDb;
    }

    private static WordDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                WordDatabase.class,
                Constants.DB_NAME).allowMainThreadQueries().build();
    }

    public  void cleanUp(){
        wordDb = null;
    }
}