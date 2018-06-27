package com.example.user.barrons333wordapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.barrons333wordapp.R;
import com.example.user.barrons333wordapp.worddb.model.Word;

import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.BeanHolder> {

    private List<Word> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnWordItemClick onWordItemClick;

    public WordsAdapter(List<Word> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onWordItemClick = (OnWordItemClick) context;
    }


    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.word_list_item,parent,false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: "+ list.get(position));
        holder.textViewTitle.setText(list.get(position).getTitle());
        holder.textViewEnglishMeaning.setText(list.get(position).getEnglishMeaning());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewEnglishMeaning;
        TextView textViewTitle;
        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewEnglishMeaning = itemView.findViewById(R.id.tv_english_meaning);
            textViewTitle = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void onClick(View view) {
            onWordItemClick.onWordClick(getAdapterPosition());
        }
    }

    public interface OnWordItemClick{
        void onWordClick(int pos);
    }
}