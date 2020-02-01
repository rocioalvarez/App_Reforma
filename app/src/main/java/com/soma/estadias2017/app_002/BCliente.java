package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 5/05/17.
 */

public class BCliente {
    String cuentaspei;
    int idcliente;
    String nombre;
    int referencia;
    String RFC;

    public String getCuentaspei() {
        return cuentaspei;
    }

    public void setCuentaspei(String cuentaspei) {
        this.cuentaspei = cuentaspei;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    @Override
    public String toString() {
        return cuentaspei ;
    }
}
