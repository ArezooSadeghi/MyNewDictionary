package com.example.mynewdictionary.diffutil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.mynewdictionary.model.Word;

import java.util.List;

public class MyDiffUtilCallBack extends DiffUtil.Callback {
    public static final String BUNDLE_WORD = "bundleWord";
    private List<Word> mOldList;
    private List<Word> mNewList;

    public MyDiffUtilCallBack(List<Word> oldList, List<Word> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }

    public void setOldList(List<Word> oldList) {
        this.mOldList = oldList;
    }

    public void setNewList(List<Word> newList) {
        this.mNewList = newList;
    }

    public List<Word> getOldList() {
        return mOldList;
    }

    public List<Word> getNewList() {
        return mNewList;
    }

    @Override
    public int getOldListSize() {
        return mOldList == null ? 0 : mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList == null ? 0 : mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = mNewList.get(newItemPosition).compareTo(mOldList.get(oldItemPosition));
        if (result == 0) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Word oldWord = mOldList.get(oldItemPosition);
        Word newWord = mNewList.get(newItemPosition);

        Bundle bundle = new Bundle();

        if (!(oldWord == newWord)) {
            bundle.putSerializable(BUNDLE_WORD, newWord);
        }

        if (bundle.size() == 0) {
            return null;
        }
        return bundle;
    }
}
