package com.hr.toy.design.dagger;

import dagger.Component;

@Component(modules = {DbModule.class})
public interface DbComponent {
    void inject(DaggerActivity activity);
}
