package com.sample.nintextest.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.IdlingResource;

import com.sample.nintextest.R;
import com.sample.nintextest.test.EspressoIdlingResource;
import com.sample.nintextest.utils.Utils;

public class MainActivity extends FragmentActivity {

    final private String TAG = MainActivity.class.getSimpleName();
    // The Idling Resource which will be null in production.
    @Nullable
    private EspressoIdlingResource mIdlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new SearchFragment());
    }

    private void replaceFragment(Fragment fragment) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commitAllowingStateLoss();
    }

    public void loadFragment(int fragmentId) {
        switch (fragmentId) {
            case Utils.FLIGHT_SEARCH_SCREEN:
                replaceFragment(new SearchFragment());
                break;

            case Utils.FLIGHT_RESULT_SCREEN:
                replaceFragment(new ResultFragment());
                break;

            default:
                Log.e(TAG, "Non existent fragment");
                break;
        }
    }

    /**
     * Only called from test, creates and returns a new EspressoIdlingResource.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new EspressoIdlingResource();
        }
        return mIdlingResource;
    }
}
