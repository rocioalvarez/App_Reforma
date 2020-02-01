package com.soma.estadias2017.app_002;

/**
 * Created by SOMA-ROCIO on 09/10/2017.
 * Funcion: Modelo de la clase CatalogoRfcCliente
 */

public class RfcCliente {
    int idcliente;
    String nombre;
    int referencia;
    String rfc;

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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @Override
    public String toString() {
        return nombre ;
    }
}
