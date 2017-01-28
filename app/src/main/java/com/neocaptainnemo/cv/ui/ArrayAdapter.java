package com.neocaptainnemo.cv.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ArrayAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected List<T> data = new ArrayList<>();
    protected Context context;

    protected ArrayAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }

    public void add(Collection<T> dataToAdd) {
        data.addAll(dataToAdd);
    }

    public void add(T item) {
        data.add(item);
    }
}
