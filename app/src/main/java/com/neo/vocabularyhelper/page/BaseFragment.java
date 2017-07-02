package com.neo.vocabularyhelper.page;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class BaseFragment extends Fragment implements IFragmentTools {
    @Override
    public void showMessage(String message) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
