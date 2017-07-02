package com.neo.vocabularyhelper.page.main.vocabulary;

import android.content.Context;

import com.neo.vocabularyhelper.connect.ApiConnect;
import com.neo.vocabularyhelper.response.GetPronunciationResponse;
import com.neo.vocabularyhelper.response.PronunciationData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class VocabularyPresenter implements VocabularyContract.Presenter {
    private final static String TAG = VocabularyPresenter.class.getSimpleName();

    private static VocabularyPresenter mVocabularyPresenter;
    private static ApiConnect mApiConnect;
    private static VocabularyContract.View mView;


    public static VocabularyPresenter getInstance(Context context, VocabularyContract.View view) {
        if (mVocabularyPresenter == null) {
            mVocabularyPresenter = new VocabularyPresenter();
            mApiConnect = ApiConnect.getInstance(context);
            mView = view;
        }
        return mVocabularyPresenter;
    }

    @Override
    public void getPronunciationAPI(String word) {
        mApiConnect.getWordPronunciation(word, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getPronunciationFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<PronunciationData> list = GetPronunciationResponse.newInstance(response.body().string());
                mView.getPronunciationSucceed(list);
            }
        });
    }
}
