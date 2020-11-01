package com.example.mynewdictionary.controller.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynewdictionary.R;
import com.example.mynewdictionary.adapter.WordAdapter;
import com.example.mynewdictionary.controller.fragment.DetailWordFragment;
import com.example.mynewdictionary.model.Word;
import com.example.mynewdictionary.repository.IRepository;
import com.example.mynewdictionary.repository.WordDBRepository;

import java.util.List;

public class SearchableActivity extends AppCompatActivity
        implements DetailWordFragment.CallBackDelete, DetailWordFragment.CallBackSave {

    private IRepository mRepository;
    private RecyclerView mRecyclerView;
    private WordAdapter mAdapter;
    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        findViews();
        initViews();
        receiveQuery();

    }


    private void findViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void receiveQuery() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            List<Word> searchResult = doMySearch(mQuery);
            if (mAdapter == null) {
                mAdapter = new WordAdapter(searchResult, this);
                setAdapter();
            } else {
                mAdapter.updateWords(doMySearch(mQuery));
            }
        }
    }

    private List<Word> doMySearch(String query) {
        List<Word> words;
        mRepository = WordDBRepository.getInstance(getApplicationContext());
        words = mRepository.searchResult(query);
        return words;
    }

    private void setAdapter() {
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void deleteClicked() {
        mAdapter.updateWords(doMySearch(mQuery));
    }

    @Override
    public void saveClicked() {
        mAdapter.updateWords(doMySearch(mQuery));
    }
}