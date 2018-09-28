package com.hr.toy.design.mvp;

/**
 *
 */
public interface MainPresenter {
    void onResume();

    void onItemClicked(int position);

    void onDestroy();
}
