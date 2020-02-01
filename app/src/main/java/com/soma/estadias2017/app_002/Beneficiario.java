package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 28/03/17.
 */

public class Beneficiario {
    String cuentaspei;
    int idcliente;
    String institucion;
    String nombre;
    int referencia;
    String rfc;

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

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
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
        return nombre;
    }

}
