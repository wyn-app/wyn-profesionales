package com.santiagonoailles.matsprofesionales.entity;

/**
 * Created by nahuelul on 12/8/17.
 */

public class RespuestaPresupuesto {

    private String professionalId;
    private int price;
    private boolean materiales;
    private String detalleProfesional;

    public RespuestaPresupuesto() {
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isMateriales() {
        return materiales;
    }

    public void setMateriales(boolean materiales) {
        this.materiales = materiales;
    }

    public String getDetalleProfesional() {
        return detalleProfesional;
    }

    public void setDetalleProfesional(String detalleProfesional) {
        this.detalleProfesional = detalleProfesional;
    }
}
