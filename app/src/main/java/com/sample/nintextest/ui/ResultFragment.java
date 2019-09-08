package com.sample.nintextest.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.nintextest.R;
import com.sample.nintextest.ViewModelFactory;
import com.sample.nintextest.databinding.FragmentResultBinding;

public class ResultFragment extends Fragment {

    FragmentActivity fragmentActivity;
    FragmentResultBinding mDataBinding;
    private SearchViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;
        initViewModels(fragmentActivity); //Obtain ViewModel for Fragment
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_result, container, false);

        mDataBinding = FragmentResultBinding.inflate(inflater, container, false);
        mDataBinding.setViewmodel(mViewModel);

        final RecyclerView recyclerView = mDataBinding.rvFlights;

        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(requireContext(), mViewModel.mFlightlist);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViewModels(FragmentActivity activity) {
        final ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        mViewModel = ViewModelProviders.of(activity, factory).get(SearchViewModel.class);
    }
}
