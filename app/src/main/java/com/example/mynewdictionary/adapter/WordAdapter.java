package com.example.mynewdictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynewdictionary.R;
import com.example.mynewdictionary.controller.fragment.DetailWordFragment;
import com.example.mynewdictionary.diffutil.MyDiffUtilCallBack;
import com.example.mynewdictionary.model.Word;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private List<Word> mWords;
    private Word mWord;
    private FragmentActivity mContext;

    public WordAdapter(List<Word> words, FragmentActivity context) {
        mWords = words;
        mContext = context;
    }

    public List<Word> getWords() {
        return mWords;
    }

    public void setWords(List<Word> words) {
        mWords = words;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.detail_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.bindWord(mWords.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position,
                                 @NonNull List<Object> payloads) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads);
        else {
            Bundle bundle = (Bundle) payloads.get(0);
            for (String key : bundle.keySet()) {
                holder.bindWord((Word) bundle.getSerializable(MyDiffUtilCallBack.BUNDLE_WORD));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public void updateWords(List<Word> newWords) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new MyDiffUtilCallBack(mWords, newWords));
        diffResult.dispatchUpdatesTo(this);
        mWords.clear();
        mWords.addAll(newWords);
    }


    public class WordViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "detailwordfragmet";
        private TextView mTextViewWord;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewWord = itemView.findViewById(R.id.txt_word_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailWordFragment fragment = DetailWordFragment.newInstance(mWord.getId());
                    fragment.show(mContext.getSupportFragmentManager(), TAG);
                }
            });
        }

        public void bindWord(Word word) {
            mWord = word;
            mTextViewWord.setText(word.getName());
        }
    }
}