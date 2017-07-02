package com.neo.vocabularyhelper.connect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by neo_mac on 2017/6/6.
 */

public class ApiConnect extends StateCode {
    private final static String TAG = ApiConnect.class.getSimpleName();

    private final static String TAG_HOST = "http://api.wordnik.com:80/v4/word.json/";
    private final static MediaType TAG_JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient mClient;
    private static ApiConnect mApiConnect;
    private static Context mContext;

    private ApiConnect() {
    }

    /**
     * API connect instance
     *
     * @param context
     * @return
     */
    public static ApiConnect getInstance(Context context) {

        mClient = new OkHttpClient.Builder().build();

        if (mApiConnect == null) {
            mContext = context;
            mApiConnect = new ApiConnect();
        }
        return mApiConnect;
    }


    /**
     * Get Api method
     *  @param url
     * @param callback*/
    private static void getApi(HttpUrl url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        mClient.newCall(request).enqueue(callback);
        Log.d(TAG, request.url().toString());
    }

    /**
     * Post Api method
     *
     * @param path
     * @param body
     * @param callback
     */
    private static void postApi(String path, RequestBody body, Callback callback) {
        RequestBody requestBody;
        if (body == null) {
            requestBody = new RequestBody() {
                @Nullable
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {

                }
            };
        } else
            requestBody = body;

        StringBuilder url = new StringBuilder();
//        url.append(TAG_HTTP).append(path);
        Request request = new Request.Builder()
                .url(url.toString())
                .post(requestBody)
                .build();
        mClient.newCall(request).enqueue(callback);
        Log.d(TAG, request.url().toString());
    }

    public static void getWordPronunciation(String word, Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST+word.toLowerCase()+"/audio")
                .newBuilder()
                .addQueryParameter("useCanonical", "false")
                .addQueryParameter("limit", "50")
                .addQueryParameter("api_key", "a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5")
                .build();
        getApi(url, callback);
    }
}
