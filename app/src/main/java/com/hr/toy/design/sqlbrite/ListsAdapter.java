package com.hr.toy.design.sqlbrite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Consumer;

public class ListsAdapter extends BaseAdapter implements Consumer<List<ListsItem>> {

    private LayoutInflater inflater;
    private List<ListsItem> items = Collections.emptyList();

    public ListsAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ListsItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        ListsItem item = getItem(position);
        ((TextView) convertView).setText(item.name() + " (" + item.itemCount() + ")");

        return convertView;
    }

    @Override
    public void accept(List<ListsItem> items) throws Exception {
        this.items = items;
        notifyDataSetChanged();
    }
}
