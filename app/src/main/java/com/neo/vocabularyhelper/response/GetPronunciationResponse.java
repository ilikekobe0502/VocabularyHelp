package com.neo.vocabularyhelper.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/7/1.
 */

public class GetPronunciationResponse {
    private static List<PronunciationData> data;

    public static List<PronunciationData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<PronunciationData>>(){}.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
