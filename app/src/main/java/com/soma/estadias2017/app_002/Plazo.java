package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 7/04/17.
 */

public class Plazo {
    int idcliente;
    String plazo;
    String fecha;
    String fechafinal;
    String taza;
    String descripcion;

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(String fechafinal) {
        this.fechafinal = fechafinal;
    }

    public String getTaza() {
        return taza;
    }

    public void setTaza(String taza) {
        this.taza = taza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion ;
    }
}


