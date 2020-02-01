package com.soma.estadias2017.app_002;

/**
 * Created by SOMA-ROCIO on 13/11/2017.
 * Función: Modelo de la clase CatalogoEstados
 */

public class Estados {
    String descripcion;
    double monto;
    String servicio;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
