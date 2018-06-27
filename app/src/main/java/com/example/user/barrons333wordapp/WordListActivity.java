package com.example.user.barrons333wordapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.user.barrons333wordapp.adapter.WordsAdapter;
import com.example.user.barrons333wordapp.worddb.WordDatabase;
import com.example.user.barrons333wordapp.worddb.model.Word;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 *  Developed by absak on 27/06/2018
 */

public class WordListActivity extends AppCompatActivity implements WordsAdapter.OnWordItemClick{

    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private WordDatabase noteDatabase;
    private List<Word> words;
    private WordsAdapter wordsAdapter;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        displayList();
    }

    private void displayList(){
        noteDatabase = WordDatabase.getInstance(WordListActivity.this);
        new RetrieveTask(this).execute();
    }

     private static class RetrieveTask extends AsyncTask<Void,Void,List<Word>> {

        private WeakReference<WordListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(WordListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Word> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().noteDatabase.getWordDao().getWords();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Word> words) {
            if (words!=null && words.size()>0 ){
                activityReference.get().words.clear();
                activityReference.get().words.addAll(words);
                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().wordsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewMsg =  (TextView) findViewById(R.id.tv__empty);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(WordListActivity.this));
        words = new ArrayList<>();
        wordsAdapter = new WordsAdapter(words,WordListActivity.this);
        recyclerView.setAdapter(wordsAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(WordListActivity.this,AddWordActivity.class),100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                words.add((Word) data.getSerializableExtra("word"));
            }else if( resultCode == 2){
                words.set(pos,(Word) data.getSerializableExtra("word"));
            }
            listVisibility();
        }
    }

    @Override
    public void onWordClick(final int pos) {
            new AlertDialog.Builder(WordListActivity.this)
            .setTitle("Select Options")
            .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case 0:
                            noteDatabase.getWordDao().deleteWord(words.get(pos));
                            words.remove(pos);
                            listVisibility();
                            break;
                        case 1:
                            WordListActivity.this.pos = pos;
                            startActivityForResult(
                                    new Intent(WordListActivity.this,
                                            AddWordActivity.class).putExtra("word",words.get(pos)),
                                    100);

                            break;
                    }
                }
            }).show();

    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (words.size() == 0){ // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        wordsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        noteDatabase.cleanUp();
        super.onDestroy();
    }
}
