package com.hr.toy.design.sqlbrite;

import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.hr.toy.design.sqlbrite.db.Db;
import com.hr.toy.design.sqlbrite.db.TodoItem;
import com.hr.toy.design.sqlbrite.db.TodoList;

import java.util.Arrays;
import java.util.Collection;

import io.reactivex.functions.Function;

@AutoValue
public abstract class ListsItem implements Parcelable{

    private static String ALIAS_LIST = "list";
    private static String ALIAS_ITEM = "item";

    private static String LIST_ID = ALIAS_LIST + "." + TodoList.ID;
    private static String LIST_NAME = ALIAS_LIST + "." + TodoList.NAME;
    private static String ITEM_COUNT = "item_count";
    private static String ITEM_ID = ALIAS_ITEM + "." + TodoItem.ID;
    private static String ITEM_LIST_ID = ALIAS_ITEM + "." + TodoItem.LIST_ID;

    public static Collection<String> TABLES = Arrays.asList(TodoList.TABLE, TodoItem.TABLE);

    public static String QUERY = ""
            + "SELECT " + LIST_ID + ", " + LIST_NAME + ", COUNT(" + ITEM_ID + ") as " + ITEM_COUNT
            + " FROM " + TodoList.TABLE + " AS " + ALIAS_LIST
            + " LEFT OUTER JOIN " + TodoItem.TABLE + " AS " + ALIAS_ITEM + " ON " + LIST_ID + " = " + ITEM_LIST_ID
            + " GROUP BY " + LIST_ID;

    abstract long id();
    abstract String name();
    abstract int itemCount();

    static Function<Cursor, ListsItem> MAPPER = new Function<Cursor, ListsItem>() {
        @Override
        public ListsItem apply(Cursor cursor) {
            long id = Db.getLong(cursor, TodoList.ID);
            String name = Db.getString(cursor, TodoList.NAME);
            int itemCount = Db.getInt(cursor, ITEM_COUNT);
            return new AutoValue_ListsItem(id, name, itemCount);
        }
    };

}
