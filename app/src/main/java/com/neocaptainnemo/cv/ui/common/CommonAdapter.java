package com.neocaptainnemo.cv.ui.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.databinding.ItemSectionBinding;
import com.neocaptainnemo.cv.model.CommonSection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {

    private Context context;

    private List<CommonSection> data = new ArrayList<>();

    CommonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSectionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_section, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CommonSection section = data.get(position);

        holder.binding.title.setText(section.title);
        holder.binding.description.setText(Html.fromHtml(section.description));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    void clear() {
        data.clear();
    }

    void add(Collection<CommonSection> dataToAdd) {
        data.addAll(dataToAdd);
    }

    void add(CommonSection item) {
        data.add(item);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemSectionBinding binding;

        public ViewHolder(ItemSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
