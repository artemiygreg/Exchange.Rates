package com.example.example_current.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.example_current.view.fragment.AllFragment;
import com.example.example_current.view.fragment.FavoriteFragment;

/**
 * Created by Admin on 21.08.15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public static final int PAGE_COUNT = 2;
    private String titles[] = new String[]{"Все", "Избранные"};
    private AllFragment allFragment;
    private FavoriteFragment favoriteFragment;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        allFragment = new AllFragment();
        favoriteFragment = new FavoriteFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return allFragment;
            case 1:
                return favoriteFragment;
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    public void notifyDataSetChange(int position){
        if(position == 0)
            allFragment.updateFragment();
        else
            favoriteFragment.updateFavorite();
    }
}
