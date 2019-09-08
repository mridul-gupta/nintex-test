package com.sample.nintextest.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.sample.nintextest.R;
import com.sample.nintextest.utils.Utils;

public class MainActivity extends FragmentActivity {

    final private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new SearchFragment(), R.id.fragment_container);
    }

    private void replaceFragment(Fragment fragment, int frameId) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commitAllowingStateLoss();
    }

    public void loadFragment(int fragmentId) {
        switch (fragmentId) {
            case Utils.FLIGHT_SEARCH_SCREEN:
                replaceFragment(new SearchFragment(), R.id.fragment_container);
                break;

            case Utils.FLIGHT_RESULT_SCREEN:
                replaceFragment(new ResultFragment(), R.id.fragment_container);
                break;

            default:
                Log.e(TAG, "Non existent fragment");
                break;
        }
    }
}
