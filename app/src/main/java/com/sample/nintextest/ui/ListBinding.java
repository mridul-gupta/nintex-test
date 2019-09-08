package com.sample.nintextest.ui;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.nintextest.model.Flight;

import java.util.List;

public class ListBinding {
    @BindingAdapter("adapter_items")
    public static void setAdapter(RecyclerView recyclerView, List<Flight> adapterList) {
        SearchResultRecyclerViewAdapter adapter = (SearchResultRecyclerViewAdapter) recyclerView.getAdapter();
        adapter.refresh(adapterList);
    }
}
