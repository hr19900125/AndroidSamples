package com.hr.toy.design.login;

/**
 *
 */
public interface LoginPresenter {

    void validateCredentials(String username, String password);

    void onDestroy();

}
