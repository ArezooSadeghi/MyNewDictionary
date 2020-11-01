package com.example.mynewdictionary.controller.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mynewdictionary.R;
import com.example.mynewdictionary.repository.IRepository;
import com.example.mynewdictionary.repository.WordDBRepository;

public class OtherActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private IRepository mRepository;
    private int mNumberOfWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        findViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
    }

    private void findViews() {
        mToolbar = findViewById(R.id.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                onSearchRequested();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRepository = WordDBRepository.getInstance(this);
        mNumberOfWord = mRepository.getWords().size();
        getSupportActionBar().setSubtitle("" + mNumberOfWord);
    }
}