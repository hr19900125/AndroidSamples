package com.hr.toy.design.sqlbrite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hr.toy.AppApplication;
import com.hr.toy.R;
import com.squareup.sqlbrite3.BriteDatabase;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static android.support.v4.view.MenuItemCompat.SHOW_AS_ACTION_IF_ROOM;
import static android.support.v4.view.MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT;

public class ListsFragment extends Fragment {

    interface Listener {
        void onListClicked(long id);

        void onNewListClicked();
    }

    public static ListsFragment newInstance() {
        ListsFragment fragment = new ListsFragment();
        return fragment;
    }

    @Inject
    BriteDatabase db;

    @BindView(android.R.id.list)
    ListView listView;
    @BindView(android.R.id.empty)
    View emptyView;

    private Listener listener;
    private ListsAdapter adapter;
    private Disposable disposable;

    @Override
    public void onAttach(Context context) {
        if (!(context instanceof Listener)) {
            throw new IllegalStateException("Activity must implement fragment Listener.");
        }
        super.onAttach(context);
        AppApplication.getComponent(getActivity()).inject(this);
        setHasOptionsMenu(true);

        listener = (Listener) context;
        adapter = new ListsAdapter(context);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(R.string.new_list)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        listener.onNewListClicked();
                        return true;
                    }
                });
        MenuItemCompat.setShowAsAction(item, SHOW_AS_ACTION_IF_ROOM | SHOW_AS_ACTION_WITH_TEXT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sqlbrite_lists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
    }

    @OnItemClick(android.R.id.list)
    void listClicked(long listId) {
        listener.onListClicked(listId);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("To-Do");

        disposable = db.createQuery(ListsItem.TABLES, ListsItem.QUERY)
                .mapToList(ListsItem.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        disposable.dispose();
    }
}
