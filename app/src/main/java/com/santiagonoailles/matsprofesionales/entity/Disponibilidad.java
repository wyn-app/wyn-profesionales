package com.santiagonoailles.matsprofesionales.entity;

import java.util.ArrayList;

/**
 * Created by nahuelul on 24/6/17.
 */

public class Disponibilidad {

    private static final String [] nombreDias = new String[]{"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    private ArrayList<Integer> dias;
    private int fromHour;
    private int toHour;

    public Disponibilidad() {
    }

    public Disponibilidad(ArrayList<Integer> dias, int fromHour, int toHour) {
        this.dias = dias;
        this.fromHour = fromHour;
        this.toHour = toHour;
    }

    public ArrayList<Integer> getDias() {
        return dias;
    }

    public void setDias(ArrayList<Integer> dias) {
        this.dias = dias;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getToHour() {
        return toHour;
    }

    public void setToHour(int toHour) {
        this.toHour = toHour;
    }

    public int getFromHour24() {
        return 7 + fromHour;
    }

    public int getToHour24() {
        return 7 + toHour;
    }

    public String getDiasText() {
        StringBuffer diasBuffer = new StringBuffer();
        int count = 0;
        for (Integer i : dias){
            if (i == 1){
                diasBuffer.append(nombreDias[count]+ " ");
            }
            count ++;
        }
        return diasBuffer.toString();
    }

    public boolean compareDays(int dia) {
        if (dia == 1){
            dia = 6;
        }else{
            dia = dia - 2;
        }

        return this.dias.get(dia) == 1;
    }
}
