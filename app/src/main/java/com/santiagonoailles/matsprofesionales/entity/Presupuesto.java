package com.santiagonoailles.matsprofesionales.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nahuelul on 31/7/17.
 */

public class Presupuesto {

    private String id;
    private String detalleUsuario;
    private Date solicitado;
    private String urlImage;
    private String userId;
    private ArrayList<RespuestaPresupuesto> respuestas;
    private long posRespuesta;


    public Presupuesto() {
    }

    public String getDetalleUsuario() {
        return detalleUsuario;
    }

    public void setDetalleUsuario(String detalleUsuario) {
        this.detalleUsuario = detalleUsuario;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<RespuestaPresupuesto> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<RespuestaPresupuesto> respuestas) {
        this.respuestas = respuestas;
    }

    public Date getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(Date solicitado) {
        this.solicitado = solicitado;
    }

    public long getPosRespuesta() {
        return posRespuesta;
    }

    public void setPosRespuesta(long posRespuesta) {
        this.posRespuesta = posRespuesta;
    }
}
