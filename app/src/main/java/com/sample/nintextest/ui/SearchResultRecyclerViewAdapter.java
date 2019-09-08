package com.sample.nintextest.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.nintextest.R;
import com.sample.nintextest.model.Flight;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.SearchResultViewHolder> {

    private List<Flight> flightList;
    private Context context;

    public SearchResultRecyclerViewAdapter(Context context, List<Flight> flightList) {
        this.context = context;
        this.flightList = flightList;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_result_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(flightList.get(position).getAirlineLogoAddress())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.airlineLogo);

        holder.airlineName.setText(flightList.get(position).getAirlineName());
        holder.outDuration.setText(flightList.get(position).getAirlineName());
        holder.inDuration.setText(flightList.get(position).getAirlineName());
        holder.totalAmount.setText(String.format(locale, "%.2f", flightList.get(position).getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public void refresh(List<Flight> flightList) {
        Log.d("TAG", "Refresh dashboard " + flightList);
        this.flightList = flightList;
        notifyDataSetChanged();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        ImageView airlineLogo;
        TextView airlineName;
        TextView outDuration;
        TextView inDuration;
        TextView totalAmount;

        SearchResultViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            airlineLogo = mView.findViewById(R.id.iv_airline_logo);
            airlineName = mView.findViewById(R.id.tv_airline_name);
            outDuration = mView.findViewById(R.id.tv_outbound_duration);
            inDuration = mView.findViewById(R.id.tv_inbound_duration);
            totalAmount = mView.findViewById(R.id.tv_total_amount);
        }
    }

}
