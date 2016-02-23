package com.mobintum.pueblosmagicos.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mobintum.pueblosmagicos.models.MagicTown;
import java.util.List;

/**
 * Created by Rick on 23/02/16.
 */
public class MagicTownRVAdapter extends RecyclerView.Adapter<MagicTownRVAdapter.ViewHolder> {

    private List<MagicTown> objects;

    public MagicTownRVAdapter(List<MagicTown> objects) {
        this.objects = objects;
    }

    @Override
    public MagicTownRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MagicTownRVAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
