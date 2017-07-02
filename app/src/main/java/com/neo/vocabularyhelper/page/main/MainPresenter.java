package com.neo.vocabularyhelper.page.main;

import android.content.Context;

import com.neo.vocabularyhelper.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class MainPresenter {

    private static MainPresenter mLoginPresenter;
    private static ApiConnect mApiConnect;
    private static Context mContext;


    public static MainPresenter getInstance(Context context) {
        if (mLoginPresenter == null) {
            mContext = context;
            mLoginPresenter = new MainPresenter();
            mApiConnect = ApiConnect.getInstance(context);
        }
        return mLoginPresenter;
    }
}
