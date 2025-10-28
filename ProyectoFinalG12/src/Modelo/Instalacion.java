/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Lulim
 */
public class Instalacion {
    private int codInstal;
    private String nombre;
    private String detalleUso;
    private double precio30m;
    private boolean estado;
    
    //Constructor vacio 
       public Instalacion() {
        this.estado = true;
    }
       
    //Constructor
       
    public Instalacion(String nombre, String detalleUso, double precio30m, boolean estado) {
        this.nombre = nombre;
        this.detalleUso = detalleUso;
        this.precio30m = precio30m;
        this.estado = estado;
    }
    
    //Getters y Setters 

    public int getCodInstal() {
        return codInstal;
    }

    public void setCodInstal(int codInstal) {
        this.codInstal = codInstal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalleUso() {
        return detalleUso;
    }

    public void setDetalleUso(String detalleUso) {
        this.detalleUso = detalleUso;
    }

    public double getPrecio30m() {
        return precio30m;
    }

    public void setPrecio30m(double precio30m) {
        this.precio30m = precio30m;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre + " ($" + precio30m + ")" ;
    }
    
    
    
}
