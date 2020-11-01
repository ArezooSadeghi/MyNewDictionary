package com.example.mynewdictionary.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mynewdictionary.R;
import com.example.mynewdictionary.model.Word;
import com.example.mynewdictionary.repository.IRepository;
import com.example.mynewdictionary.repository.WordDBRepository;

import java.util.UUID;

public class DetailWordFragment extends DialogFragment {

    private static final String ARGS_WORD_ID = "wordId";
    private EditText mEditTextWord, mEditTextMean;
    private Button mButtonEdit, mButtonDelete, mButtonSave;
    private IRepository mRepository;
    private Word mWord;
    private UUID mWordId;
    private CallBackDelete mCallBackDelete;
    private CallBackSave mCallBackSave;

    public DetailWordFragment() {

    }


    public static DetailWordFragment newInstance(UUID wordId) {
        DetailWordFragment fragment = new DetailWordFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_WORD_ID, wordId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordId = (UUID) getArguments().getSerializable(ARGS_WORD_ID);
        mRepository = WordDBRepository.getInstance(getContext());
        mWord = mRepository.getWord(mWordId);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_detail_word, null);
        findViews(view);
        initViews();
        setListeners();
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .create();
        return dialog;
    }

    private void findViews(View view) {
        mEditTextWord = view.findViewById(R.id.txt_word);
        mEditTextMean = view.findViewById(R.id.txt_mean);
        mButtonEdit = view.findViewById(R.id.btn_edit);
        mButtonDelete = view.findViewById(R.id.btn_delete);
        mButtonSave = view.findViewById(R.id.btn_save);
    }

    private void initViews() {
        mEditTextWord.setFocusable(false);
        mEditTextMean.setFocusable(false);
        mEditTextWord.setText(mWord.getName());
        mEditTextMean.setText(mWord.getMean());
    }

    private void setListeners() {
        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditTextWord.setFocusableInTouchMode(true);
                mEditTextMean.setFocusableInTouchMode(true);
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWord.setName(mEditTextWord.getText().toString());
                mWord.setMean(mEditTextMean.getText().toString());
                mRepository.updateWord(mWord);
                mCallBackSave.saveClicked();
                dismiss();
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepository.deleteWord(mWord);
                mCallBackDelete.deleteClicked();
                dismiss();
            }
        });
    }

    public interface CallBackDelete {
        void deleteClicked();
    }

    public interface CallBackSave {
        void saveClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallBackDelete) {
            mCallBackDelete = (CallBackDelete) context;
        }
        if (context instanceof CallBackSave) {
            mCallBackSave = (CallBackSave) context;
        }
    }
}