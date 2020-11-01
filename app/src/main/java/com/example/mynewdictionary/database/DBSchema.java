package com.example.mynewdictionary.database;

public class DBSchema {
    public static final String NAME = "word.db";
    public static final int VERSION = 1;

    public static final class WordTable {
        public static final String NAME = "word_table";

        public static final class Cols {
            public static final String ID = "id";
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String MEAN = "mean";
        }
    }
}
