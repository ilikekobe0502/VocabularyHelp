package com.neo.vocabularyhelper.page.main.vocabulary;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.neo.vocabularyhelper.R;
import com.neo.vocabularyhelper.sql.VocabularyData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/6/30.
 */

public class VocabularyRecyclerViewAdapter extends RecyclerView.Adapter<VocabularyRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {
    private final static String TAG = VocabularyRecyclerViewAdapter.class.getSimpleName();

    private List<Object> mItems;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClick(View view, String word);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vocablury, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null)
            return;
        VocabularyData item = (VocabularyData) mItems.get(position);
        if (item == null)
            return;

        holder.mTextViewVocabulary.setText(item.getVocabulary());
        holder.mTextViewTranslation.setText(item.getTranslation());
        holder.mTextViewExample.setText(item.getExample());

        holder.mImageButtonPronunciation.setTag(item.getVocabulary());
        holder.mImageButtonPronunciation.setOnClickListener(this);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<Object> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_vocabulary)
        TextView mTextViewVocabulary;
        @BindView(R.id.imageButton_pronunciation)
        ImageButton mImageButtonPronunciation;
        @BindView(R.id.textView_translation)
        TextView mTextViewTranslation;
        @BindView(R.id.textView_example)
        TextView mTextViewExample;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onClick(v, String.valueOf(v.getTag()));
        }
    }

    @Override
    public boolean onLongClick(View v) {
//        if (mListener != null) {
//            mListener.onLongClick(v, v.getTag());
//        }
        return true;
    }
}
