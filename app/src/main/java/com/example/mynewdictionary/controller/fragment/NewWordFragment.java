package com.example.mynewdictionary.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mynewdictionary.R;
import com.example.mynewdictionary.model.Word;
import com.example.mynewdictionary.repository.IRepository;
import com.example.mynewdictionary.repository.WordDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewWordFragment extends DialogFragment {

    private FloatingActionButton mButtonInsert;
    private EditText mEditTextWord, mEditTextMean;
    private IRepository mRepository;
    private PassDataCallBack mCallBack;


    public NewWordFragment() {
    }

    public static NewWordFragment newInstance() {
        NewWordFragment fragment = new NewWordFragment();
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
                .inflate(R.layout.fragment_new_word, null);
        findViews(view);
        setListeners();
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .create();
        return dialog;
    }

    private void findViews(View view) {
        mButtonInsert = view.findViewById(R.id.fab_insert);
        mEditTextWord = view.findViewById(R.id.txt_new_word);
        mEditTextMean = view.findViewById(R.id.txt_new_mean);
    }

    private void setListeners() {
        mButtonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word = new Word();
                word.setName(mEditTextWord.getText().toString());
                word.setMean(mEditTextMean.getText().toString());
                mRepository = WordDBRepository.getInstance(getContext());
                mRepository.insertWord(word);
                mCallBack.insertClicked();
                dismiss();
            }
        });
    }

    public interface PassDataCallBack {
        void insertClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PassDataCallBack) {
            mCallBack = (PassDataCallBack) context;
        }
    }
}