package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 15/05/17.
 */

public class Cvrastreo {
    int id_cliente;
    String clave;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return clave;
    }
}
