package com.t0006.lesson3;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dimatomp on 27.09.14.
 */
public abstract class StdListAdapter<T> implements ListAdapter {
    private final Fragment fragment;
    protected boolean doneLoading = true;
    Collection<DataSetObserver> observers = new LinkedList<>();
    final Runnable changeNotifier = new Runnable() {
        @Override
        public void run() {
            for (DataSetObserver observer : observers)
                observer.onChanged();
        }
    };
    List<T> items = new ArrayList<>();

    public StdListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    protected LayoutInflater getLayoutInflater() {
        return fragment.getActivity().getLayoutInflater();
    }

    public void notifyDataSetChanged() {
        if (!fragment.isDetached() && fragment.getActivity() != null)
            fragment.getActivity().runOnUiThread(changeNotifier);
    }

    public void reset() {
        doneLoading = false;
        items.clear();
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void onFinishedLoading() {
        doneLoading = true;
        notifyDataSetChanged();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        observers.remove(observer);
    }

    protected View createLastItem(ViewGroup parent) {
        return fragment.getActivity().getLayoutInflater().inflate(
                R.layout.ind_progress_bar, parent, false);
    }

    @Override
    public int getCount() {
        return items.size() + (doneLoading ? 0 : 1);
    }

    @Override
    public Object getItem(int position) {
        return position < items.size() ? items.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return position < items.size() ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty() && doneLoading;
    }
}
