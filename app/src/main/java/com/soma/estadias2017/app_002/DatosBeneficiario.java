package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 30/03/17.
 */

public class DatosBeneficiario {
    String nombreBeneficiario;
    String rfc;
    String cuentaspei;
    String institucion;
    int idcliente;
    int referencia;

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

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

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return nombreBeneficiario ;
    }
}
