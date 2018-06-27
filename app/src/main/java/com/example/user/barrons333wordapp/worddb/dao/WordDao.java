package com.example.user.barrons333wordapp.worddb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.user.barrons333wordapp.worddb.model.Word;
import com.example.user.barrons333wordapp.worddb.util.Constants;

import java.util.List;

/**
 *  Developed by absak on 27/06/2018
 */

@Dao
public interface WordDao {

    @Query("SELECT * FROM " + Constants.TABLE_NAME_WORD)
    List<Word> getWords();

    /*
     * Insert the object in database
     * @param word, object to be inserted
     */
    @Insert
    long insertWord(Word word);

    /*
     * update the object in database
     * @param word, object to be updated
     */
    @Update
    void updateWord(Word repos);

    /*
     * delete the object from database
     * @param word, object to be deleted
     */
    @Delete
    void deleteWord(Word word);

    // Word... is varargs, here word is an array
    /*
     * delete list of objects from database
     * @param word, array of oject to be deleted
     */
    @Delete
    void deleteWords(Word... word);
}
