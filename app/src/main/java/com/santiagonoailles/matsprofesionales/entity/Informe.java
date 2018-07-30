package com.santiagonoailles.matsprofesionales.entity;

import java.util.Date;

/**
 * Created by nahuelul on 24/6/17.
 */

public class Informe {

    //El cliente pide un informe
    public static final int STATE_PEDIDO = 0;
    //El profesional le pasa una fecha
    public static final int STATE_VALIDADO = 1;
    //El profesional rechaza el informe
    public static final int STATE_CANCELADO_POR_PROFESIONAL = 2;
    //El usuario rechaza el informe
    public static final int STATE_CANCELADO_POR_USUARIO = 3;
    //El cliente confirma la fecha del profesional
    public static final int STATE_CONFIRMADO = 4;
    //El cliente rankea al profesional
    public static final int STATE_RANKEADO = 5;

    private String id;
    private Disponibilidad disponibilidad;
    private String detalle;
    private String direccion;
    private String phone;
    private Date pedidoDate;
    private Date confirmadoDate;
    private String professionalId;
    private String profetionalName;
    private String professionalFileUrl;
    private int profession;
    private String userId;
    private String urlImage;
    private String localidad;
    private String userName;
    private int state;

    public Informe() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getPedidoDate() {
        return pedidoDate;
    }

    public void setPedidoDate(Date pedidoDate) {
        this.pedidoDate = pedidoDate;
    }

    public Date getConfirmadoDate() {
        return confirmadoDate;
    }

    public void setConfirmadoDate(Date confirmadoDate) {
        this.confirmadoDate = confirmadoDate;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getProfessionalFileUrl() {
        return professionalFileUrl;
    }

    public void setProfessionalFileUrl(String professionalFileUrl) {
        this.professionalFileUrl = professionalFileUrl;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfetionalName() {
        return profetionalName;
    }

    public void setProfetionalName(String profetionalName) {
        this.profetionalName = profetionalName;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
