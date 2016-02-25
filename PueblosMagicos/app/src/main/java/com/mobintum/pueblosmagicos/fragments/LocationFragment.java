package com.mobintum.pueblosmagicos.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.mobintum.pueblosmagicos.R;
import com.mobintum.pueblosmagicos.application.AppController;

import java.util.Map;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements Response.ErrorListener, Response.Listener<String> {

    private GoogleMap gMap;



    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        gMap = map.getMap();
        getLocation("san+sristobal+de+las+casas+chiapas");
        return view;
    }

    private void getLocation(final String townName){
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+townName;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this, this){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> params = new HashMap<String,String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("key", getResources().getString(R.string.google_key));
                params.put("address",townName);
                return params;
            }
        };

        AppController.getInstance().getRequestQueue().add(stringRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("DEBUG", error.toString());

    }

    @Override
    public void onResponse(String response) {
        Log.e("DEBUG", response);

    }
}
