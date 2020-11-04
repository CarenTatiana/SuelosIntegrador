package com.example.suelos;

public class Cultivo {
    private String id;
    private String nombre;
    private float precio;
    private float costo;
    private float redimiento;
    private float minimo;
    private float maximo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getRedimiento() {
        return redimiento;
    }

    public void setRedimiento(float redimiento) {
        this.redimiento = redimiento;
    }

    public float getMinimo() {
        return minimo;
    }

    public void setMinimo(float minimo) {
        this.minimo = minimo;
    }

    public float getMaximo() {
        return maximo;
    }

    public void setMaximo(float maximo) {
        this.maximo = maximo;
    }

    @Override
    public String toString() {
        return
                nombre ;


    }
}
