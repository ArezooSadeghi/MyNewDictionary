package com.example.mynewdictionary.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mynewdictionary.R;
import com.example.mynewdictionary.controller.fragment.SelectLanguageFragment;

public class MainActivity extends AppCompatActivity implements
        SelectLanguageFragment.PerToEngCallback, SelectLanguageFragment.EngToPerCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, SelectLanguageFragment.newInstance())
                    .commit();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void perToEngClicked(boolean perToEngClicked) {
        Intent intent = OtherActivity.newIntent(this, false);
        startActivity(intent);
    }

    @Override
    public void engToPerClicked(boolean engToPerClicked) {
        Intent intent = OtherActivity.newIntent(this, true);
        startActivity(intent);
    }
}
