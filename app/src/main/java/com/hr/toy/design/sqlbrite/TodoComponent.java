package com.hr.toy.design.sqlbrite;

import com.hr.toy.design.sqlbrite.db.TodoModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TodoModule.class)
public interface TodoComponent {

    void inject(ListsFragment fragment);

    void inject(ItemsFragment fragment);

    void inject(NewItemFragment fragment);

    void inject(NewListFragment fragment);

}
