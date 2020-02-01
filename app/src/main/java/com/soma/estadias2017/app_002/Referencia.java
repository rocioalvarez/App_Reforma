package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 5/05/17.
 */

public class Referencia {
    int id_cliente;
    String referencia;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }


    @Override
    public String toString() {
        return referencia;
    }
}
