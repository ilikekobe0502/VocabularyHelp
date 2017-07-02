package com.neo.vocabularyhelper.component;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.neo.vocabularyhelper.R;
import com.neo.vocabularyhelper.sql.VocabularyDB;
import com.neo.vocabularyhelper.sql.VocabularyData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/7/1.
 */

public class MyDialog extends DialogFragment {
    private final static String TAG = MyDialog.class.getSimpleName();

    @BindView(R.id.editText_vocabulary)
    EditText mEditTextVocabulary;
    @BindView(R.id.editText_translation)
    EditText mEditTextTranslation;
    @BindView(R.id.editText_example)
    EditText mEditTextExample;
    @BindView(R.id.textView_cancel)
    TextView mTextViewCancel;
    @BindView(R.id.textView_ok)
    TextView mTextViewOk;
    @BindView(R.id.textView_title)
    TextView mTextViewTitle;
    @BindView(R.id.textView_archive)
    TextView mTextViewArchive;
    @BindView(R.id.textView_delete)
    TextView mTextViewDelete;


    private static MyDialog mMyDialog;
    private static VocabularyDB mVocabularyDB;

    private String mID;
    private String mVocabulary;
    private String mTranslation;
    private String mExample;
    private String mTitle;

    private OnClickListener mMyClickListener;

    public interface OnClickListener {
        void onClick(View view, boolean success, String message);
    }

    public static MyDialog newInstance(VocabularyDB vocabularyDB) {
        if (mMyDialog == null) {
            mMyDialog = new MyDialog();
            mVocabularyDB = vocabularyDB;
        }
        return mMyDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.from(inflater.getContext()).inflate(R.layout.fragment_my_dialog, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContent();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mTitle = "";
        mID = "";
        mTranslation = "";
        mExample = "";
        mVocabulary = "";
        mEditTextVocabulary.setText("");
        mEditTextTranslation.setText("");
        mEditTextExample.setText("");
        super.onDismiss(dialog);
    }

    @OnClick({R.id.textView_cancel, R.id.textView_ok, R.id.textView_archive, R.id.textView_delete})
    public void onClick(View view) {
        if (mMyClickListener != null) {
            VocabularyData vocabulary = new VocabularyData();
            if (!TextUtils.isEmpty(mID))
                vocabulary.setId(mID);
            vocabulary.setVocabulary(!TextUtils.isEmpty(mEditTextVocabulary.getText().toString()) ? mEditTextVocabulary.getText().toString() : "");
            vocabulary.setTranslation(!TextUtils.isEmpty(mEditTextTranslation.getText().toString()) ? mEditTextTranslation.getText().toString() : "");
            vocabulary.setExample(!TextUtils.isEmpty(mEditTextExample.getText().toString()) ? mEditTextExample.getText().toString() : "");

            boolean success = false;
            String message = "";

            switch (view.getId()) {
                case R.id.textView_cancel:
                    break;
                case R.id.textView_ok:
                    //更新
                    if (!TextUtils.isEmpty(mID)) {
                        if (mVocabularyDB.update(vocabulary)) {
                            message = getString(R.string.vocabulary_edit_success);
                            success = true;
                        }else {
                            Log.e(TAG, "Data update failed");
                            message = getString(R.string.vocabulary_edit_failed);
                            success = false;
                        }
                    } else {//新增
                        if (mVocabularyDB.insert(vocabulary) != VocabularyDB.TAG_INSERT_FAILED) {
                            success = true;
                            message = getString(R.string.vocabulary_insert_success);
                        }else {
                            Log.e(TAG, "Data insert failed");
                            message = getString(R.string.vocabulary_edit_failed);
                            success = false;
                        }
                    }
                    mMyClickListener.onClick(view, success,message);
                    break;
                case R.id.textView_archive://封存
                    mMyClickListener.onClick(view, success,message);
                    break;
                case R.id.textView_delete://刪除
                    if (!TextUtils.isEmpty(mID)) {
                        if (mVocabularyDB.delete(mID)) {
                            message = getString(R.string.vocabulary_delete_success);
                            success = true;
                        } else {
                            Log.e(TAG, "Data delete failed");
                            message = getString(R.string.vocabulary_delete_failed);
                            success = false;
                        }
                    }
                    mMyClickListener.onClick(view, success,message);
                    break;
            }
        }
        this.dismiss();
    }

    public MyDialog setOnClickListener(OnClickListener listener) {
        mMyClickListener = listener;
        return this;
    }

    private void setContent() {
        mTextViewTitle.setText(!TextUtils.isEmpty(mTitle) ? mTitle : getString(R.string.dialog_add_vocabulary_title));
        mEditTextVocabulary.setText(mVocabulary);
        mEditTextTranslation.setText(mTranslation);
        mEditTextExample.setText(mExample);
    }

    public MyDialog setData(Object object) {
        VocabularyData data = (VocabularyData) object;
        if (data != null) {
            mID = !TextUtils.isEmpty(data.getId()) ? data.getId() : "";
            mVocabulary = !TextUtils.isEmpty(data.getVocabulary()) ? data.getVocabulary() : "";
            mTranslation = !TextUtils.isEmpty(data.getTranslation()) ? data.getTranslation() : "";
            mExample = !TextUtils.isEmpty(data.getExample()) ? data.getExample() : "";
        }
        return this;
    }

    public MyDialog setTitle(String title) {
        mTitle = title;
        return this;
    }
}
