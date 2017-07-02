package com.neo.vocabularyhelper.page.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.neo.vocabularyhelper.R;
import com.neo.vocabularyhelper.page.BaseActivity;
import com.neo.vocabularyhelper.page.IActivityTools;
import com.neo.vocabularyhelper.page.Page;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IActivityTools.ILoadingView, IActivityTools.IMainActivity, Toolbar.OnMenuItemClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private setMenuListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar.setTitle("單字卡");
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);

        replaceFragment(Page.TAG_VOCABULARY, null, false);
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void goneLoadingView() {

    }

    @Override
    public void replaceFragment(int page, Bundle bundle, boolean backStack) {
        Page.switchFragment(this, page, bundle, backStack);
    }

    @Override
    public void addFragment(int page, Bundle bundle, boolean backStack) {
        Page.addFragment(this, page, bundle, backStack);
    }

    @Override
    public void setMenuClickListener(setMenuListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
                if (mListener != null) {
                    mListener.onClick();
                    break;
                }
        }
        return true;
    }

    public interface setMenuListener {
        void onClick();
    }
}
