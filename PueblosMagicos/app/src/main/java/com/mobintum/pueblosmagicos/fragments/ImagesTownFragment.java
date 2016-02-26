package com.mobintum.pueblosmagicos.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobintum.pueblosmagicos.R;
import com.mobintum.pueblosmagicos.adapters.ImageRVAdaper;
import com.mobintum.pueblosmagicos.application.AppController;
import com.mobintum.pueblosmagicos.models.MagicTown;
import com.mobintum.pueblosmagicos.response.geocoding.GeocodingResponse;
import com.mobintum.pueblosmagicos.response.venuefour.BestPhoto;
import com.mobintum.pueblosmagicos.response.venuefour.Group;
import com.mobintum.pueblosmagicos.response.venuefour.Item;
import com.mobintum.pueblosmagicos.response.venuefour.Venue;
import com.mobintum.pueblosmagicos.util.CustomImageVIew;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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
            //for (MagicTown town : MagicTown.getData())
            //    paths.add(town.getPathMainPhoto());
            LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            adaper = new ImageRVAdaper(paths, getActivity(), ImagesTownFragment.this);
            rvImagesTown.setLayoutManager(layoutManager);
            rvImagesTown.setAdapter(adaper);


        }

        getVenues(18.6782919,-88.3923946);

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
        CustomImageVIew imageDialog = (CustomImageVIew) dialog.findViewById(R.id.imgDialog);
        Picasso.with(getContext()).load(path).into(imageDialog);
        dialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("show", show);
        outState.putString("path",path);
    }


    public void getVenues(double lat, double lon){

        String url = "https://api.foursquare.com/v2/venues/search?client_id=KFBD1D243LNTYSNNUWJ1X3ZD4V5JXJ04IB0OEDV11JR1OROX&client_secret=DMTPETRTOC3BRZEK2VIWZV3A1MPIHV4XO2IHMNH4Q4MM5YNQ&v=20130815&ll="+lat+","+lon;
        StringRequest requestVenues = new StringRequest(Request.Method.GET, url,
                 new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.e("DEBUG IMAGE", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Venue>>(){}.getType();
                            jsonObject = jsonObject.getJSONObject("response");
                            List<Venue> venues = (List<Venue>) gson.fromJson(jsonObject.get("venues").toString(),listType);
                            for(Venue venue :venues){
                                getPhotos(venue.getId());
                                Log.e("DEBUG VENUE", venue.getName());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("DEBUG", error.toString());

                    }
                }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();

                        return params;
                    }

                    @Override
                    public Map<String,String> getHeaders() throws AuthFailureError{
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        return params;
                    }
                };
        AppController.getInstance().getRequestQueue().add(requestVenues);
    }

    public void getPhotos(String venue_id){

        String url = "https://api.foursquare.com/v2/venues/"+venue_id+"?client_id=KFBD1D243LNTYSNNUWJ1X3ZD4V5JXJ04IB0OEDV11JR1OROX&client_secret=DMTPETRTOC3BRZEK2VIWZV3A1MPIHV4XO2IHMNH4Q4MM5YNQ&v=20160225";
        final StringRequest requestVenues = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.e("DEBUG IMAGE", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Gson gson = new Gson();
                            Venue venue = gson.fromJson(jsonObject.getJSONObject("response").getJSONObject("venue").toString(), Venue.class);
                            if(venue.getPhotos().getCount()>0){
                                Group group =venue.getPhotos().getGroups().get(0);
                                //Item item = group.getItems().get(0);
                                for (Item item : group.getItems()){
                                    String path =  item.getPrefix()+"400x400"+item.getSuffix();
                                    paths.add(path);
                                    Log.e("DEBUG PHOTO", path);
                                }


                                adaper.notifyDataSetChanged();
                            }
                            /*if(venue.getBestPhoto()!=null){
                                BestPhoto item = venue.getBestPhoto();
                                String path =  item.getPrefix()+"400x400"+item.getSuffix();
                                Log.e("DEBUG PHOTO", path);
                                paths.add(path);
                                adaper.notifyDataSetChanged();
                            }*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DEBUG", error.toString());

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> params = new HashMap<String,String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().getRequestQueue().add(requestVenues);
    }
}
