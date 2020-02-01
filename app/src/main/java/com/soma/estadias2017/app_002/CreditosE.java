package com.soma.estadias2017.app_002;

import java.io.Serializable;

/**
 * Created by estadias2017 on 27/03/17.
 *
 * Funcion: Poblar Spinner "Credito a depositar" - submodulo Abono a Creditos
 */

public class CreditosE{

    String referencia;
    static int id_credito;

    public static int getId_credito() {
        return id_credito;
    }

    public void setId_credito(int id_credito) {
        this.id_credito = id_credito;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return referencia ;
    }


}

