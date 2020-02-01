package com.soma.estadias2017.app_002;

/**
 * Created by SOMA-ROCIO on 11/11/2017.
 * Función: modelo de la clase CatalogoRecargasE
 */

public class Recargas {
    String servicio;
    double monto;
    String sku;

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String toString(){
        return servicio;
    }
}
