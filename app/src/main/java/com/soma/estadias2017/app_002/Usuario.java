package com.soma.estadias2017.app_002;

/**
 * Created by SOMA-ROCIO on 17/08/2017.
 */

public class Usuario {

    private String codigo;
    private String nombres;
    private String apellidos;

    public Usuario (String codigo, String nombres, String apellidos){
        setCodigo(codigo);
        setNombres(nombres);
        setApellidos(apellidos);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
