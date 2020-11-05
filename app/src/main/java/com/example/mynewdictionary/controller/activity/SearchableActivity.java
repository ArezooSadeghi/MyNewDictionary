package com.example.mynewdictionary.controller.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynewdictionary.R;
import com.example.mynewdictionary.adapter.WordAdapter;
import com.example.mynewdictionary.controller.fragment.DetailWordFragment;
import com.example.mynewdictionary.model.Word;
import com.example.mynewdictionary.repository.IRepository;
import com.example.mynewdictionary.repository.WordDBRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends AppCompatActivity
        implements DetailWordFragment.CallBackDelete, DetailWordFragment.CallBackSave {

    private IRepository mRepository;
    private RecyclerView mRecyclerView;
    private WordAdapter mAdapter;
    private String mQuery;
    private boolean mJargon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
        if (appData != null) {
            mJargon = appData.getBoolean(OtherActivity.BUNDLE);
        }

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
            List<Word> searchResult = new ArrayList<>();
            if (mJargon) {
                searchResult = doMySearch(mQuery);
            } else {
                searchResult = doAnotherSearch(mQuery);
            }
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
        words = mRepository.searchResultEngToPer(query);
        return words;
    }

    private List<Word> doAnotherSearch(String query) {
        List<Word> words;
        mRepository = WordDBRepository.getInstance(getApplicationContext());
        words = mRepository.searchResultPerToEng(query);
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

    public static Intent newIntent(Context context, boolean clicked) {
        Intent intent = new Intent(context, SearchableActivity.class);
        intent.putExtra("clicked", clicked);
        return intent;
    }
}