package com.soma.estadias2017.app_002;

import android.app.Application;

import java.io.Serializable;

/**
 * Created by estadias2017 on 30/03/17.
 */

public class CreditoD {
    int idcredito;
    int total;
    int capital;
    int interes;
    int moratorio;
    int iva;

    public int getIdcredito() {
        return idcredito;
    }

    public void setIdcredito(int idcredito) {
        this.idcredito = idcredito;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public int getInteres() {
        return interes;
    }

    public void setInteres(int interes) {
        this.interes = interes;
    }

    public int getMoratorio() {
        return moratorio;
    }

    public void setMoratorio(int moratorio) {
        this.moratorio = moratorio;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }
}
