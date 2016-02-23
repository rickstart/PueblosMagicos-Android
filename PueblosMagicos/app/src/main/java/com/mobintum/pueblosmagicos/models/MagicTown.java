package com.mobintum.pueblosmagicos.models;

import java.util.List;
/**
 * Created by Rick on 23/02/16.
 */
public class MagicTown {
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String pathMainPhoto;
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
}
