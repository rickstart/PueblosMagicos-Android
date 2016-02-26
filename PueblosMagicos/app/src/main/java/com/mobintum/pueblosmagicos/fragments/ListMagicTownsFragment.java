package com.mobintum.pueblosmagicos.fragments;


import android.app.ProgressDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobintum.pueblosmagicos.R;
import com.mobintum.pueblosmagicos.adapters.MagicTownRVAdapter;
import com.mobintum.pueblosmagicos.application.AppController;
import com.mobintum.pueblosmagicos.models.MagicTown;
import com.mobintum.pueblosmagicos.response.geocoding.GeocodingResponse;
import com.mobintum.pueblosmagicos.response.geocoding.Geometry;
import com.mobintum.pueblosmagicos.response.geocoding.Location;
import com.mobintum.pueblosmagicos.response.geocoding.Result;
import com.mobintum.pueblosmagicos.response.venuefour.Venue;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMagicTownsFragment extends Fragment implements MagicTownRVAdapter.OnItemClickListener {

    public static final String TAG = "ListMagicTownsFragment";
    private RecyclerView rvMagicTowns;
    private MagicTownRVAdapter adapter;
    private Callbacks callback;
    private ProgressDialog pDialog;
    private List<MagicTown> towns = MagicTown.getMagicTowns();
    private int lastUpdate;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new ProgressDialog(getActivity());
        getLocations();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_magic_towns, container, false);
        rvMagicTowns = (RecyclerView) view.findViewById(R.id.rvMagicTowns);
        adapter = new MagicTownRVAdapter(towns,getContext(),this);
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

    public void getLocations(){
        for(int i=0;i<towns.size();i++){
            MagicTown town = towns.get(i);
            Log.e("DEBUG", town.getLatitude()+" "+town.getName());
            if(town.getLatitude()==0.0 || town.getLongitude()==0.0){
                Log.e("DEBUG", "ENETER ");
                if(!pDialog.isShowing())
                    pDialog.show();
                getLocation(town,i);
                this.lastUpdate = i;
            }
        }

    }


    private void getLocation(final MagicTown town, final int index){
        String townName = town.getName()+" "+town.getState();
        townName = townName.replaceAll("\\s+","+");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+townName;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.e("DEBUG", response);
                        try {
                            Gson gson = new Gson();
                            GeocodingResponse geocodingResponse = gson.fromJson(response,GeocodingResponse.class);
                            List<Result> resultList=  geocodingResponse.getResults();
                            Result result = resultList.get(0);
                            Geometry geometry = result.getGeometry();
                            Location location = geometry.getLocation();
                            town.setLatitude(location.getLat());
                            town.setLongitude(location.getLng());
                            MagicTown.updateMagicTown(town);
                            if(index==lastUpdate)
                                pDialog.hide();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DEBUG", error.toString());
                pDialog.hide();

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("key", getResources().getString(R.string.google_key));
                return params;
            }
        };
        AppController.getInstance().getRequestQueue().add(stringRequest);
    }

}
