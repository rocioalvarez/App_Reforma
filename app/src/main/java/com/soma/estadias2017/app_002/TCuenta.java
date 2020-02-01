package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 28/03/17.
 */

public class TCuenta {
    String catalogos;
    String descripcion;

    public String getCatalogos() {
        return catalogos;
    }

    public void setCatalogos(String catalogos) {
        this.catalogos = catalogos;
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
