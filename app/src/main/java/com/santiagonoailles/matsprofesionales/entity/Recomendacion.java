package com.santiagonoailles.matsprofesionales.entity;

/**
 * Created by nahuelul on 11/7/17.
 */

public class Recomendacion {

    private String recomendacion;
    private int startsCount;

    public Recomendacion() {
    }

    public Recomendacion(String recomendacion, int startsCount) {
        this.recomendacion = recomendacion;
        this.startsCount = startsCount;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public int getStartsCount() {
        return startsCount;
    }

    public void setStartsCount(int startsCount) {
        this.startsCount = startsCount;
    }
}
