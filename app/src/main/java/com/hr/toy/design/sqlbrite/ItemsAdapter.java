package com.hr.toy.design.sqlbrite;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.hr.toy.design.sqlbrite.db.TodoItem;

import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Consumer;

public class ItemsAdapter extends BaseAdapter implements Consumer<List<TodoItem>> {

    private final LayoutInflater inflater;

    private List<TodoItem> items = Collections.emptyList();

    public ItemsAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TodoItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).id();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        }

        TodoItem item = getItem(position);
        CheckedTextView textView = (CheckedTextView) convertView;
        textView.setChecked(item.complete());

        CharSequence description = item.description();
        if (item.complete()) {
            SpannableString spannable = new SpannableString(description);
            spannable.setSpan(new StrikethroughSpan(), 0, description.length(), 0);
            description = spannable;
        }

        textView.setText(description);

        return convertView;
    }

    @Override
    public void accept(List<TodoItem> items) throws Exception {
        this.items = items;
        notifyDataSetChanged();
    }
}
