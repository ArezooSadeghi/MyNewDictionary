package com.example.mynewdictionary.repository;

import com.example.mynewdictionary.model.Word;

import java.util.List;
import java.util.UUID;

public interface IRepository {
    List<Word> getWords();

    Word getWord(UUID wordId);

    void insertWord(Word word);

    void updateWord(Word word);

    void deleteWord(Word word);

    List<Word> searchResultEngToPer(String query);

    List<Word> searchResultPerToEng(String query);
}
