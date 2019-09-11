package com.sample.nintextest.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.nintextest.R;
import com.sample.nintextest.ViewModelFactory;
import com.sample.nintextest.databinding.FragmentResultBinding;
import com.sample.nintextest.utils.Utils;

public class ResultFragment extends Fragment {

    private FragmentActivity fragmentActivity;
    private FragmentResultBinding mDataBinding;
    private SearchViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                onBackPressed();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onAttach(@NonNull Context context) {
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

        final Toolbar toolbar = mDataBinding.getRoot().findViewById(R.id.toolbar);
        ((TextView)toolbar.findViewById(R.id.toolbar_title)).setText(String.format(getString(R.string.result_title), mViewModel.fromCity, mViewModel.toCity));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        final RecyclerView recyclerView = mDataBinding.rvFlights;

        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(requireContext(), mViewModel.mFlightList);

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

    private void onBackPressed() {
        mViewModel.responseStatus.setValue(Utils.Status.IDLE);
        ((MainActivity) fragmentActivity).loadFragment(Utils.FLIGHT_SEARCH_SCREEN);
    }
}
