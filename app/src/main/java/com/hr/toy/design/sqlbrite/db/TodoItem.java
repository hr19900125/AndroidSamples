package com.hr.toy.design.sqlbrite.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import io.reactivex.functions.Function;

@AutoValue
public abstract class TodoItem implements Parcelable {

    public static final String TABLE = "todo_item";

    public static final String ID = "_id";
    public static final String LIST_ID = "todo_list_id";
    public static final String DESCRIPTION = "description";
    public static final String COMPLETE = "complete";

    public abstract long id();

    public abstract long listId();

    public abstract String description();

    public abstract boolean complete();

    public static final Function<Cursor, TodoItem> MAPPER = new Function<Cursor, TodoItem>() {
        @Override
        public TodoItem apply(Cursor cursor) {
            long id = Db.getLong(cursor, ID);
            long listId = Db.getLong(cursor, LIST_ID);
            String description = Db.getString(cursor, DESCRIPTION);
            boolean complete = Db.getBoolean(cursor, COMPLETE);
            return new AutoValue_TodoItem(id, listId, description, complete);
        }
    };

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(ID, id);
            return this;
        }

        public Builder listId(long listId) {
            values.put(LIST_ID, listId);
            return this;
        }

        public Builder description(String description) {
            values.put(DESCRIPTION, description);
            return this;
        }

        public Builder complete(boolean complete) {
            values.put(COMPLETE, complete ? Db.BOOLEAN_TRUE : Db.BOOLEAN_FALSE);
            return this;
        }

        public ContentValues build() {
            return values; // TODO defensive copy?
        }
    }

}
