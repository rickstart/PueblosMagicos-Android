package com.mobintum.pueblosmagicos.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobintum.pueblosmagicos.R;
import com.mobintum.pueblosmagicos.adapters.MagicTownRVAdapter;
import com.mobintum.pueblosmagicos.models.MagicTown;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMagicTownsFragment extends Fragment implements MagicTownRVAdapter.OnItemClickListener {

    public static final String TAG = "ListMagicTownsFragment";
    private RecyclerView rvMagicTowns;
    private MagicTownRVAdapter adapter;
    private Callbacks callback;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.callback = (Callbacks) getActivity();
        }catch (Exception e){
            Log.e(TAG, "You have to implente the interface with the fragment");
        }
    }

    public ListMagicTownsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_magic_towns, container, false);
        rvMagicTowns = (RecyclerView) view.findViewById(R.id.rvMagicTowns);
        adapter = new MagicTownRVAdapter(MagicTown.getData(),getContext(),this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvMagicTowns.setLayoutManager(linearLayoutManager);
        rvMagicTowns.setAdapter(adapter);
    }

    @Override
    public void onClick(MagicTown magicTown) {
        callback.onTownSelected(magicTown);

    }

    public interface Callbacks{
        public void onTownSelected(MagicTown magicTown);

    }
}
