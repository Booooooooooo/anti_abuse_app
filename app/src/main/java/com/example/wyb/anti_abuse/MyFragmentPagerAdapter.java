package com.example.wyb.anti_abuse;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
    private CoreFragment coreFragment;
    private HeartFragment heartFragment;
    private SoundFragment soundFragment;
    private UserFragment userFragment;

    public void setSoundContext(Context context){
        soundFragment.setContext(context);
    }

    public MyFragmentPagerAdapter(FragmentManager fm){
        super(fm);
        coreFragment = new CoreFragment();
        heartFragment = new HeartFragment();
        soundFragment = new SoundFragment();
        userFragment = new UserFragment();
    }

    @Override
    public int getCount(){
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position){
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = coreFragment;
                break;
            case 1:
                fragment = heartFragment;
                break;
            case 2:
                fragment = soundFragment;
                break;
            case 3:
                fragment = userFragment;
                break;
        }
        return fragment;
    }
}
