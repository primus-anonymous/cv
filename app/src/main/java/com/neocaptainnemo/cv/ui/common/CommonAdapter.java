package com.neocaptainnemo.cv.ui.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.databinding.ItemSectionBinding;
import com.neocaptainnemo.cv.model.CommonSection;
import com.neocaptainnemo.cv.ui.ArrayAdapter;

class CommonAdapter extends ArrayAdapter<CommonSection, CommonAdapter.ViewHolder> {

    CommonAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSectionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_section, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setViewModel(new CommonViewModel(data.get(position)));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemSectionBinding binding;

        ViewHolder(ItemSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
