package com.sample.nintextest;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sample.nintextest.ui.SearchViewModel;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Application mApplication;
    private static volatile ViewModelFactory INSTANCE;

    public ViewModelFactory(Application application) {
        this.mApplication = application;
    }

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }


    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}