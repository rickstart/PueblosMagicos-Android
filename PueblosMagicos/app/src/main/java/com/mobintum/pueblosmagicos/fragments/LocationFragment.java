package com.mobintum.pueblosmagicos.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mobintum.pueblosmagicos.R;
import com.mobintum.pueblosmagicos.application.AppController;
import com.mobintum.pueblosmagicos.responses.geocoding.GeocodingResponse;
import com.mobintum.pueblosmagicos.responses.geocoding.Geometry;
import com.mobintum.pueblosmagicos.responses.geocoding.Location;
import com.mobintum.pueblosmagicos.responses.geocoding.Result;
import java.util.List;

import java.util.Map;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements Response.ErrorListener, Response.Listener<String> {

    private GoogleMap gMap;
    private String townName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.townName = "san cristobal de las casas chiapas";
    }

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        gMap = map.getMap();
        String townNameFormat = townName.replaceAll("\\s+","+");
        getLocation(townNameFormat);
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
        try {
            Gson gson = new Gson();
            GeocodingResponse geocodingResponse = gson.fromJson(response,GeocodingResponse.class);
            loadLocation(geocodingResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLocation(GeocodingResponse geo){
        List<Result> resultList=  geo.getResults();
        Result result = resultList.get(0);
        Geometry geometry = result.getGeometry();
        Location location = geometry.getLocation();
        LatLng latLng = new LatLng(location.getLat(),location.getLng());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(result.getFormattedAddress());
        gMap.addMarker(markerOptions);
        gMap.animateCamera(cameraUpdate);
    }
}
