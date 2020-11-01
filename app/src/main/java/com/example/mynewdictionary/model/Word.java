package com.example.mynewdictionary.model;

import java.io.Serializable;
import java.util.UUID;

public class Word implements Serializable, Comparable {
    private String mName;
    private String mMean;
    private UUID mId;

    public Word() {
        this(UUID.randomUUID());
    }

    public Word(UUID id) {
        mId = id;
    }

    public Word(String name, String mean) {
        mId = UUID.randomUUID();
        mName = name;
        mMean = mean;
    }

    public Word(String name, String mean, UUID id) {
        mName = name;
        mMean = mean;
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setMean(String mean) {
        mMean = mean;
    }

    public String getName() {
        return mName;
    }

    public String getMean() {
        return mMean;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public int compareTo(Object o) {
        Word word = (Word) o;
        if ((word.getName().equals(this.mName)) && (word.getMean().equals(this.mMean))) {
            return 0;
        } else {
            return 1;
        }
    }
}