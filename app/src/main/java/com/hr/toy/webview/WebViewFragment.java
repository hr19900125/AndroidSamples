package com.hr.toy.webview;

import android.content.Intent;

import com.hr.toy.BaseExampleFragment;

/**
 *
 */
public class WebViewFragment extends BaseExampleFragment {

    @Override
    protected String[] initData() {
        return new String[]{"Hello Webview"};
    }

    @Override
    protected void handleClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent();
                intent.setClass(getActivity(), HelloWebViewActivity.class);
                break;
        }
        if(intent != null) startActivity(intent);
    }
}
