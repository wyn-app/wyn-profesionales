package com.santiagonoailles.matsprofesionales.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by nahuelul on 22/6/17.
 */

public class Profesional {

    public static final int TYPE_REGISTERED = 0;
    public static final int TYPE_NOT_REGISTERED = 1;

    private static final String POSITION_SEPARATOR = "_";
    private String id;
    private String name;
    private String dni;
    private ArrayList<Recomendacion> recomendacions;
    private String pictureUrl;
    private String description;
    private ArrayList<String> professions;
    private boolean fastWork;
    private boolean registered;
    private boolean factura;
    private String position;
    private String email;
    private int ranking;
    private int type;
    private ArrayList<String> phones;

    public Profesional() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public ArrayList<String> getProfessions() {
        return professions;
    }

    public void setProfessions(ArrayList<String> professions) {
        this.professions = professions;
    }

    public ArrayList<Recomendacion> getRecomendacions() {
        return recomendacions;
    }

    public void setRecomendacions(ArrayList<Recomendacion> recomendacions) {
        this.recomendacions = recomendacions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFastWork() {
        return fastWork;
    }

    public void setFastWork(boolean fastWork) {
        this.fastWork = fastWork;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getPosition() {
        return position;
    }

    public void setPositionInLatLng(String position) {
        this.position = position;
    }

    @Exclude
    public LatLng getPositionInLatLng() {
        if (position != null) {
            LatLng result = new LatLng(Double.valueOf(position.split(POSITION_SEPARATOR)[0]), Double.valueOf(position.split(POSITION_SEPARATOR)[1]));
            return result;
        } else {
            return null;
        }
    }

    @Exclude
    public void setPositionInLatLng(LatLng latLng) {
        this.position = String.valueOf(latLng.latitude) + POSITION_SEPARATOR + String.valueOf(latLng.longitude);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFactura() {
        return factura;
    }

    public void setFactura(boolean factura) {
        this.factura = factura;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
