package com.example.mynewdictionary.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mynewdictionary.R;
import com.example.mynewdictionary.controller.fragment.NewWordFragment;
import com.example.mynewdictionary.repository.IRepository;
import com.example.mynewdictionary.repository.WordDBRepository;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OtherActivity extends AppCompatActivity implements NewWordFragment.PassDataCallBack {

    private static final String TAG = "otheractivity";
    public static final String BUNDLE = "bundle";
    private ExtendedFloatingActionButton mButtonAdd;
    private Toolbar mToolbar;
    private IRepository mRepository;
    private int mNumberOfWord;
    private boolean mClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        mRepository = WordDBRepository.getInstance(this);

        Intent intent = getIntent();
        mClicked = intent.getBooleanExtra("clicked", false);

        findViews();
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewWordFragment fragment = NewWordFragment.newInstance();
                fragment.show(getSupportFragmentManager(), TAG);
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
    }

    private void findViews() {
        mToolbar = findViewById(R.id.toolbar);
        mButtonAdd = findViewById(R.id.fab_add);
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

    private void updateNumberOfWords() {
        mNumberOfWord = mRepository.getWords().size();
        getSupportActionBar().setSubtitle("" + mNumberOfWord);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNumberOfWords();
    }

    @Override
    public void insertClicked() {
        updateNumberOfWords();
    }

    public static Intent newIntent(Context context, boolean clicked) {
        Intent intent = new Intent(context, OtherActivity.class);
        intent.putExtra("clicked", clicked);
        return intent;
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putBoolean(BUNDLE, mClicked);
        startSearch(null, false, appData, false);
        return true;
    }
}