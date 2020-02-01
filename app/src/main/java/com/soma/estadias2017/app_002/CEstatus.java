package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 7/06/17.
 */

public class CEstatus {
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
