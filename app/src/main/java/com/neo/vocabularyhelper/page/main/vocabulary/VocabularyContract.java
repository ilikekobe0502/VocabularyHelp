package com.neo.vocabularyhelper.page.main.vocabulary;

import com.neo.vocabularyhelper.response.PronunciationData;

import java.util.List;

/**
 * Created by neo_mac on 2017/7/1.
 */

public interface VocabularyContract {
    interface View {
        void getPronunciationSucceed(List<PronunciationData> response);

        void getPronunciationFailed(String message);
    }

    interface Presenter {
        void getPronunciationAPI(String word);
    }
}
