package com.sample.nintextest.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.nintextest.R;
import com.sample.nintextest.databinding.SearchResultItemBinding;
import com.sample.nintextest.model.Flight;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.SearchResultViewHolder> {
    private final String TAG = SearchFragment.class.getSimpleName();

    private List<Flight> flightList;
    private final Context context;

    SearchResultRecyclerViewAdapter(Context context, List<Flight> flightList) {
        this.context = context;
        this.flightList = flightList;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchResultViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.search_result_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        final Flight flight = flightList.get(position);
        holder.bind(flight);
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    void refresh(List<Flight> flightList) {
        Log.d("TAG", "Refresh dashboard " + flightList);
        this.flightList = flightList;
        notifyDataSetChanged();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {

        final SearchResultItemBinding mViewBinding;
        Flight mFlight;


        SearchResultViewHolder(SearchResultItemBinding viewBinding) {
            super(viewBinding.getRoot());
            mViewBinding = viewBinding;
            viewBinding.getRoot().setOnClickListener(view -> Toast.makeText(context, "Checkout not implemented", Toast.LENGTH_SHORT).show());
        }

        void bind(Flight flight) {
            mFlight = flight;
            final SearchResultItemBinding viewBinding = mViewBinding;
            updateUi();
            viewBinding.executePendingBindings();
        }

        private void updateUi() {

            Locale locale = context.getResources().getConfiguration().getLocales().get(0);

            Picasso.Builder builder = new Picasso.Builder(context);
            Picasso.get().setLoggingEnabled(true);
            Picasso.get().setIndicatorsEnabled(true);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(mFlight.getAirlineLogoAddress())
                    .error(R.drawable.ic_launcher_background)
                    .into(mViewBinding.ivAirlineLogo, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "Error fetching image " + mFlight);
                        }
                    });

            mViewBinding.tvAirlineName.setText(mFlight.getAirlineName());
            String[] outStr = mFlight.getOutboundFlightsDuration().split(":");
            mViewBinding.tvOutboundDuration.setText(outStr[0] + "h " + outStr[1] + "m");
            String[] inStr = mFlight.getInboundFlightsDuration().split(":");
            mViewBinding.tvInboundDuration.setText(inStr[0] + "h " + inStr[1] + "m");
            mViewBinding.tvTotalAmount.setText(String.format(locale, "$%.0f", mFlight.getTotalAmount()));
        }
    }
}
