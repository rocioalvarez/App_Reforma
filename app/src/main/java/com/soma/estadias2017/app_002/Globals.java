package com.soma.estadias2017.app_002;

import android.app.Application;

/**
 * Created by estadias2017 on 14/03/17.
 */

public class Globals extends Application{
    private static int idusuario=0;
    private static  String cuenta="";

    public static int getIdusuario() {
        return idusuario;
    }

    public static void setIdusuario(int idusuario) {
        Globals.idusuario = idusuario;
    }

    public static String getCuenta() {
        return cuenta;
    }

    public static void setCuenta(String cuenta) {
        Globals.cuenta = cuenta;
    }
}
