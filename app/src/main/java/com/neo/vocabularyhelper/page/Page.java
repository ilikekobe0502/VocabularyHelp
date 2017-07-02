package com.neo.vocabularyhelper.page;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.neo.vocabularyhelper.R;
import com.neo.vocabularyhelper.page.main.MainFragment;
import com.neo.vocabularyhelper.page.main.vocabulary.VocabularyFragment;


/**
 * Created by neo_mac on 2017/6/20.
 */

public class Page {
    // TODO: Example your page TAG
    public static final int TAG_MAIN = 1000;
    public static final int TAG_VOCABULARY = 1001;
    public static final int TAG_DICTIONARY = 1002;

    /**
     * Switch Fragment
     *
     * @param activity
     * @param page
     * @param bundle
     * @param backStack
     */
    public static void switchFragment(AppCompatActivity activity, int page, Bundle bundle, boolean backStack) {
        String fragmentName = getFragment(page).getClass().getSimpleName();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        android.support.v4.app.Fragment fragment = getFragment(page);
        if (bundle != null)
            fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.layout_container, fragment, fragmentName);
        if (backStack)
            fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    /**
     * Add Fragment
     *
     * @param activity
     * @param page
     * @param bundle
     * @param backStack
     */
    public static void addFragment(AppCompatActivity activity, int page, Bundle bundle, boolean backStack) {
        String fragmentName = getFragment(page).getClass().getSimpleName();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        android.support.v4.app.Fragment fragment = getFragment(page);
        if (bundle != null)
            fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.layout_container, fragment, fragmentName);
        if (backStack)
            fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    private static android.support.v4.app.Fragment getFragment(int page) {
        switch (page) {
            // TODO Example to page
            case TAG_MAIN:
                return new MainFragment().newInstance();
            case TAG_VOCABULARY:
                return new VocabularyFragment().newInstance();
        }

        return null;
    }
}
