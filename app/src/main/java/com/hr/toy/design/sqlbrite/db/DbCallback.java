package com.hr.toy.design.sqlbrite.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL;

public class DbCallback extends SupportSQLiteOpenHelper.Callback {

    private static final int VERSION = 1;

    private static final String CREATE_LIST = ""
            + "CREATE TABLE " + TodoList.TABLE + "("
            + TodoList.ID + " INTEGER NOT NULL PRIMARY KEY,"
            + TodoList.NAME + " TEXT NOT NULL,"
            + TodoList.ARCHIVED + " INTEGER NOT NULL DEFAULT 0"
            + ")";

    private static final String CREATE_ITEM = ""
            + "CREATE TABLE " + TodoItem.TABLE + "("
            + TodoItem.ID + " INTEGER NOT NULL PRIMARY KEY,"
            + TodoItem.LIST_ID + " INTEGER NOT NULL REFERENCES " + TodoList.TABLE + "(" + TodoList.ID + "),"
            + TodoItem.DESCRIPTION + " TEXT NOT NULL,"
            + TodoItem.COMPLETE + " INTEGER NOT NULL DEFAULT 0"
            + ")";

    private static final String CREATE_ITEM_LIST_ID_INDEX =
            "CREATE INDEX item_list_id ON " + TodoItem.TABLE + " (" + TodoItem.LIST_ID + ")";


    /**
     * Creates a new Callback to get database lifecycle events.
     */
    public DbCallback() {
        super(VERSION);
    }

    @Override
    public void onCreate(SupportSQLiteDatabase db) {
        db.execSQL(CREATE_LIST);
        db.execSQL(CREATE_ITEM);
        db.execSQL(CREATE_ITEM_LIST_ID_INDEX);

        long groceryListId = db.insert(TodoList.TABLE, CONFLICT_FAIL, new TodoList.Builder()
                .name("Grocery List")
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(groceryListId)
                .description("Beer")
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(groceryListId)
                .description("Point Break on DVD")
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(groceryListId)
                .description("Bad Boys 2 on DVD")
                .build());

        long holidayPresentsListId = db.insert(TodoList.TABLE, CONFLICT_FAIL, new TodoList.Builder()
                .name("Holiday Presents")
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(holidayPresentsListId)
                .description("Pogo Stick for Jake W.")
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(holidayPresentsListId)
                .description("Jack-in-the-box for Alec S.")
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(holidayPresentsListId)
                .description("Pogs for Matt P.")
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(holidayPresentsListId)
                .description("Cola for Jesse W.")
                .build());

        long workListId = db.insert(TodoList.TABLE, CONFLICT_FAIL, new TodoList.Builder()
                .name("Work Items")
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(workListId)
                .description("Finish SqlBrite library")
                .complete(true)
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(workListId)
                .description("Finish SqlBrite sample app")
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder()
                .listId(workListId)
                .description("Publish SqlBrite to GitHub")
                .build());

        long birthdayPresentsListId = db.insert(TodoList.TABLE, CONFLICT_FAIL, new TodoList.Builder()
                .name("Birthday Presents")
                .archived(true)
                .build());
        db.insert(TodoItem.TABLE, CONFLICT_FAIL, new TodoItem.Builder().listId(birthdayPresentsListId)
                .description("New car")
                .complete(true)
                .build());
    }

    @Override
    public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
