package com.soma.estadias2017.app_002;

/**
 * Created by SOMA-ROCIO on 06/03/2018.
 */

public class CuentaServ {

    String fecha;
    int folio;
    String url;
    int cadena;
    int establecimiento;
    int terminal;
    String cajero;
    String pass;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCadena() {
        return cadena;
    }

    public void setCadena(int cadena) {
        this.cadena = cadena;
    }

    public int getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(int establecimiento) {
        this.establecimiento = establecimiento;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString(){
        return fecha;
    }
}
