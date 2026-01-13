package com.example.sql;

public class Asistente {

    private int codigo;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroControl;
    private String email;

    private int activo;

    public Asistente(int codigo, String nombre, String apellidoPaterno, String apellidoMaterno, String numeroControl, String email, int activo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroControl = numeroControl;
        this.email = email;
        this.activo = activo;
    }

    public Asistente() {
        this(0, "", "", "", "", "", 1);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Código: " + codigo +
                ", Nombre: " + nombre +
                ", Apellido Paterno: " + apellidoPaterno +
                ", Apellido Materno: " + apellidoMaterno +
                ", Número de Control: " + numeroControl +
                ", Email: " + email;
    }
}
