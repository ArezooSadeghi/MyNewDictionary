package com.example.mynewdictionary.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynewdictionary.R;
public class DetailWordFragment extends DialogFragment {

    public DetailWordFragment() {

    }


    public static DetailWordFragment newInstance() {
        DetailWordFragment fragment = new DetailWordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_detail_word, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .create();
        return dialog;
    }
}