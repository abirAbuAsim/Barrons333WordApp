package com.example.user.barrons333wordapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.user.barrons333wordapp.worddb.WordDatabase;
import com.example.user.barrons333wordapp.worddb.model.Word;

import java.lang.ref.WeakReference;

public class AddWordActivity extends AppCompatActivity {

    private TextInputEditText inputEditTextTitle, inputEditTextEnglishMeaning;
    private WordDatabase wordDatabase;
    private Word word;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        inputEditTextTitle = findViewById(R.id.et_title);
        inputEditTextEnglishMeaning = findViewById(R.id.et_eng_meaning);
        wordDatabase = WordDatabase.getInstance(AddWordActivity.this);
        Button button = findViewById(R.id.but_save);
        if ( (word = (Word) getIntent().getSerializableExtra("word"))!=null ){
            getSupportActionBar().setTitle("Update Word");
            update = true;
            button.setText("Update");
            inputEditTextTitle.setText(word.getTitle());
            inputEditTextEnglishMeaning.setText(word.getEnglishMeaning());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    word.setEnglishMeaning(inputEditTextEnglishMeaning.getText().toString());
                    word.setTitle(inputEditTextTitle.getText().toString());
                    wordDatabase.getWordDao().updateWord(word);
                    setResult(word,2);
                }else {
                    word = new Word("bengali", inputEditTextEnglishMeaning.getText().toString(), inputEditTextTitle.getText().toString());
                    new InsertTask(AddWordActivity.this,word).execute();
                }
            }
        });
    }

    private void setResult(Word word, int flag){
        setResult(flag,new Intent().putExtra("word",word));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddWordActivity> activityReference;
        private Word word;

        // only retain a weak reference to the activity
        InsertTask(AddWordActivity context, Word word) {
            activityReference = new WeakReference<>(context);
            this.word = word;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented word id
            long j = activityReference.get().wordDatabase.getWordDao().insertWord(word);
            word.setWord_id(j);
            Log.e("ID ", "doInBackground: "+j );
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(word,1);
                activityReference.get().finish();
            }
        }
    }


}
