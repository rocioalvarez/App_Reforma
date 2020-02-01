package com.soma.estadias2017.app_002;

/**
 * Created by estadias2017 on 15/06/17.
 */

public class BTimer {
    int id_beneficiario;
    String nombreBeneficiario;

    public int getId_beneficiario() {
        return id_beneficiario;
    }

    public void setId_beneficiario(int id_beneficiario) {
        this.id_beneficiario = id_beneficiario;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    @Override
    public String toString() {
        return nombreBeneficiario ;
    }

}
