package com.mobintum.pueblosmagicos.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.ListView;

import com.mobintum.pueblosmagicos.database.DatabaseAdapter;
import com.mobintum.pueblosmagicos.database.DatabaseHelper;

import java.util.List;
import java.util.ArrayList;
/**
 * Created by Rick on 23/02/16.
 */
public class MagicTown {

    public static final String TABLE_NAME = "MagicTown";
    public static final String ID_MAGIC_TOWN = "idMagicTown";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String STATE = "state";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String PATH_MAIN_PHOTO = "pathMainPhoto";


    private int idMagicTown;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String pathMainPhoto;
    private String state;
    private List<String> links;
    private List<String> photos;


    public MagicTown(String name, String description, Double latitude, Double longitude, String pathMainPhoto, List<String> links, List<String> photos) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pathMainPhoto = pathMainPhoto;
        this.links = links;
        this.photos = photos;
    }

    public MagicTown(String name, String pathMainPhoto) {
        this.pathMainPhoto = pathMainPhoto;
        this.name = name;
    }

    public MagicTown(String name, String state , String pathMainPhoto) {
        this.pathMainPhoto = pathMainPhoto;
        this.name = name;
        this.state = state;
    }

    public MagicTown(int idMagicTown, String name, String description, Double latitude, Double longitude, String pathMainPhoto, String state) {
        this.idMagicTown = idMagicTown;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pathMainPhoto = pathMainPhoto;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPathMainPhoto() {
        return pathMainPhoto;
    }

    public void setPathMainPhoto(String pathMainPhoto) {
        this.pathMainPhoto = pathMainPhoto;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIdMagicTown() {
        return idMagicTown;
    }

    public void setIdMagicTown(int idMagicTown) {
        this.idMagicTown = idMagicTown;
    }

    public static List<MagicTown> getMagicTowns(){
        List<MagicTown> objects = new ArrayList<>();
        Cursor cursor = DatabaseAdapter.getDB().query(TABLE_NAME,null,null,null,null,null,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            int idMagicTown = cursor.getInt(cursor.getColumnIndexOrThrow(ID_MAGIC_TOWN));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
            String state = cursor.getString(cursor.getColumnIndexOrThrow(STATE));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LONGITUDE));
            String pathMainPhoto = cursor.getString(cursor.getColumnIndexOrThrow(PATH_MAIN_PHOTO));
            objects.add(new MagicTown(idMagicTown,name,description,latitude,longitude,pathMainPhoto,state));
        }
        return objects;
    }

    public static void updateMagicTown(MagicTown town){
        ContentValues cv = new ContentValues();
        cv.put(NAME, town.getName());
        cv.put(DESCRIPTION, town.getDescription());
        cv.put(STATE, town.getState());
        cv.put(LATITUDE,town.getLatitude());
        cv.put(LONGITUDE,town.getLatitude());
        cv.put(PATH_MAIN_PHOTO, town.getPathMainPhoto());
        DatabaseAdapter.getDB().update(TABLE_NAME,cv,ID_MAGIC_TOWN+"="+town.getIdMagicTown(),null);
    }
}
