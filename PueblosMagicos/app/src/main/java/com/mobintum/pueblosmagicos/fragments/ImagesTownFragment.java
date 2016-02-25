package com.mobintum.pueblosmagicos.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobintum.pueblosmagicos.R;
import com.mobintum.pueblosmagicos.adapters.ImageRVAdaper;
import com.mobintum.pueblosmagicos.models.MagicTown;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagesTownFragment extends Fragment implements ImageRVAdaper.OnItemClickListener {

    private RecyclerView rvImagesTown;
    private ImageRVAdaper adaper;
    private List<String> paths = new ArrayList<>();
    private boolean show;
    private String path;
    public ImagesTownFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images_town, container, false);
        if(savedInstanceState!=null){
            this.show = savedInstanceState.getBoolean("show");
            this.path = savedInstanceState.getString("path");
            if(path!=null)
                loadImage(path);
        }else{
            rvImagesTown = (RecyclerView) view.findViewById(R.id.rvImagesTown);

            //setRetainInstance(true);
            for (MagicTown town : MagicTown.getData())
                paths.add(town.getPathMainPhoto());
            LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
            adaper = new ImageRVAdaper(paths, getActivity(), ImagesTownFragment.this);
            rvImagesTown.setLayoutManager(layoutManager);
            rvImagesTown.setAdapter(adaper);


        }


        setRetainInstance(true);
        return view;

    }


    @Override
    public void onClick(String path) {
        this.path = path;
        this.show = true;
        loadImage(path);
    }

    public  void loadImage(String path){
        this.path = path;
        Dialog dialog=new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_image);
        ImageView imageDialog = (ImageView) dialog.findViewById(R.id.imgDialog);
        Picasso.with(getContext()).load(path).into(imageDialog);
        dialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("show", show);
        outState.putString("path",path);
    }
}
