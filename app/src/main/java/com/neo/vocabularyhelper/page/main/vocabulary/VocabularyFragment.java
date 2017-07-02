package com.neo.vocabularyhelper.page.main.vocabulary;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neo.vocabularyhelper.R;
import com.neo.vocabularyhelper.component.MyDialog;
import com.neo.vocabularyhelper.page.BaseFragment;
import com.neo.vocabularyhelper.page.IActivityTools;
import com.neo.vocabularyhelper.page.main.MainActivity;
import com.neo.vocabularyhelper.response.PronunciationData;
import com.neo.vocabularyhelper.sql.VocabularyDB;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VocabularyFragment extends BaseFragment implements VocabularyContract.View, MediaPlayer.OnCompletionListener {
    private static final String TAG = VocabularyFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private VocabularyContract.Presenter mPresenter;

    private MediaPlayer mMediaPlayer;

    private VocabularyRecyclerViewAdapter mAdapter = new VocabularyRecyclerViewAdapter();

    private boolean mIsPLAYING;
    private VocabularyDB mVocabularyDB;

    public VocabularyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VocabularyFragment newInstance() {
        VocabularyFragment fragment = new VocabularyFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);
        ButterKnife.bind(this, view);

        mPresenter = VocabularyPresenter.getInstance(getContext(), this);

        mVocabularyDB = new VocabularyDB(getContext());
        final MyDialog dialog = MyDialog.newInstance(mVocabularyDB);
        mMainActivity.setMenuClickListener(new MainActivity.setMenuListener() {
            @Override
            public void onClick() {

                dialog.setOnClickListener(new MyDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, boolean success, String message) {
                        switch (view.getId()) {
                            case R.id.textView_ok:
                                if (success)
                                    mAdapter.setData(mVocabularyDB.getAll());
                                showMessage(message);
                                break;
                        }
                    }
                });
                dialog.show(getActivity().getFragmentManager(), "dialog");
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClick(new VocabularyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, String word) {
                switch (view.getId()) {
                    case R.id.imageButton_pronunciation:
                        if (!TextUtils.isEmpty(word))
                            mPresenter.getPronunciationAPI(word);
                        else
                            showMessage(getString(R.string.data_error));
                        break;
                    default:
                        dialog.setData(view.getTag())
                                .setTitle(getString(R.string.dialog_edit_vocabulary_title))
                                .setOnClickListener(new MyDialog.OnClickListener() {
                                    @Override
                                    public void onClick(View view, boolean success, String message) {
                                        if (success)
                                            mAdapter.setData(mVocabularyDB.getAll());
                                        showMessage(message);
                                    }
                                });
                        dialog.show(getActivity().getFragmentManager(), "dialog");
                        break;
                }
            }
        });

        mAdapter.setData(mVocabularyDB.getAll());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
//            mLoadingView = (IActivityTools.ILoadingView) context;
            mMainActivity = (IActivityTools.IMainActivity) context;
        } catch (ClassCastException castException) {
            Log.e(TAG, castException.toString());
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getPronunciationSucceed(List<PronunciationData> response) {
        if (response != null && response.size() > 0) {
            String audioUrl = "";
            for (PronunciationData item : response) {
                if (TextUtils.equals(item.getCreatedBy(), PronunciationData.TAG_MACMILLAN)) {
                    audioUrl = item.getFileUrl();
                    break;
                }
            }

            //若Macmillan資料沒有發音，則拿第一筆來源試試看
            if (TextUtils.isEmpty(audioUrl) && !TextUtils.isEmpty(response.get(0).getFileUrl()))
                audioUrl = response.get(0).getFileUrl();

            if (!TextUtils.isEmpty(audioUrl)) {
                if (!mIsPLAYING) {
                    mIsPLAYING = true;
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setOnCompletionListener(this);
                    try {
                        mMediaPlayer.setDataSource(audioUrl);
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (IOException e) {
                        Log.e(TAG, "prepare() failed");
                    }
                } else {
                    mIsPLAYING = false;
                    stopPlaying();
                }
            } else {
                Log.e(TAG, "PronunciationData is null");
                showMessage(getString(R.string.data_error));
            }
        } else {
            Log.e(TAG, "response is null");
            showMessage(getString(R.string.vocabulary_data_empty_error));
        }
    }

    @Override
    public void getPronunciationFailed(String message) {
        Log.e(TAG, message);
        showMessage(getString(R.string.server_error));
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.release();
        stopPlaying();
    }

    /**
     * Stop playing
     */
    private void stopPlaying() {
        if (mMediaPlayer != null) {
            mIsPLAYING = false;
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
