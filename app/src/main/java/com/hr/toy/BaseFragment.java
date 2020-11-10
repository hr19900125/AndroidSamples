package com.hr.toy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 *
 */
public abstract class BaseFragment extends Fragment implements OnBackPressedListener {

    protected OnSelectedFragmentDelegate mOnSelectedFragmentDelegate;

    public interface OnSelectedFragmentDelegate {
        void setupSelectedFragment(BaseFragment fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof OnSelectedFragmentDelegate)) {
            throw new ClassCastException("Hosting activity must implement OnSelectedFragmentDelegate");
        } else {
            mOnSelectedFragmentDelegate = (OnSelectedFragmentDelegate) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mOnSelectedFragmentDelegate.setupSelectedFragment(this);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
