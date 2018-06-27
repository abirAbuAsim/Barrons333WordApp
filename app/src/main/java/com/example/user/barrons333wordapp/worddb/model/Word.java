package com.example.user.barrons333wordapp.worddb.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.user.barrons333wordapp.worddb.util.Constants;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *  Developed by absak on 27/06/2018
 */

@Entity(tableName = Constants.TABLE_NAME_WORD)
public class Word implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long word_id;

    @ColumnInfo(name = "bengali_meaning")
    private String bengaliMeaning;

    @ColumnInfo(name = "english_meaning")
    private String englishMeaning;

    private String title;

    private Date date;

    public Word(String bengaliMeaning, String englishMeaning, String title) {
        this.bengaliMeaning = bengaliMeaning;
        this.englishMeaning = englishMeaning;
        this.title = title;
    }

    @Ignore
    public Word(){}

    public long getWord_id() {
        return word_id;
    }

    public void setWord_id(long word_id) {
        this.word_id = word_id;
    }

    public String getBengaliMeaning() {
        return bengaliMeaning;
    }

    public void setBengaliMeaning(String bengaliMeaning) {
        this.bengaliMeaning = bengaliMeaning;
    }

    public String getEnglishMeaning() {
        return englishMeaning;
    }

    public void setEnglishMeaning(String englishMeaning) {
        this.englishMeaning = englishMeaning;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word = (Word) o;
        return getWord_id() == word.getWord_id() &&
                Objects.equals(getBengaliMeaning(), word.getBengaliMeaning()) &&
                Objects.equals(getEnglishMeaning(), word.getEnglishMeaning()) &&
                Objects.equals(getTitle(), word.getTitle());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTitle());
    }

    @Override
    public String toString() {
        return "Word{" +
                "word_id=" + word_id +
                ", title='" + title + '\'' +
                ", bengali_meaning='" + bengaliMeaning + '\'' +
                ", english_meaning='" + englishMeaning + '\'' +
                ", date=" + date +
                '}';
    }
}
