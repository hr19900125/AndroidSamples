package com.hr.toy.design.sqlbrite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.hr.toy.AppApplication;
import com.hr.toy.R;
import com.hr.toy.design.sqlbrite.db.TodoList;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.squareup.sqlbrite3.BriteDatabase;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_NONE;
import static butterknife.ButterKnife.findById;

public class NewListFragment extends DialogFragment {

    public static NewListFragment newInstance() {
        return new NewListFragment();
    }

    private final PublishSubject<String> createClicked = PublishSubject.create();

    @Inject
    BriteDatabase db;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppApplication.getComponent(context).inject(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getActivity();

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_sqlbrite_newlist, null);

        EditText name = findById(view, android.R.id.input);
        Observable.combineLatest(createClicked, RxTextView.textChanges(name), new BiFunction<String, CharSequence, String>() {
            @Override
            public String apply(String ignored, CharSequence text) throws Exception {
                return text.toString();
            }
        }).observeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String name) throws Exception {
                        db.insert(TodoList.TABLE, CONFLICT_NONE, new TodoList.Builder().name(name).build());
                    }
                });

        return new AlertDialog.Builder(context)
                .setTitle(R.string.new_list)
                .setView(view)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createClicked.onNext("clicked");
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();

    }
}
