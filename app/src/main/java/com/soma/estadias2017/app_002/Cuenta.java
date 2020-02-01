package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 25/03/17.
 */

public class Cuenta {
    String idcuenta;
    String descripcion;

    public String getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(String idcuenta) {
        this.idcuenta = idcuenta;
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
