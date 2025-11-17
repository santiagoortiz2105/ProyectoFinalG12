/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Lulim
 */
public class Tratamiento {
    
    private int codTratam; //Autoincremental
    private String nombre; //varchar
    private String tipo;   //varchar
    private String detalle; //varchar
    private int duracion_min; //int 
    private double costo;     //decimal
    private boolean activo;   //tinyint 
    private int cantidadSesiones; 
    
    //Constructor vacio

    public Tratamiento() {
         this.activo = true;
    }
    
    //Constructor 

    public Tratamiento(String nombre, String tipo, String detalle, int duracion_min, double costo, boolean activo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.detalle = detalle;
        this.duracion_min = duracion_min;
        this.costo = costo;
        this.activo = activo;
    }
    
    //Getters y Setters

    public int getCodTratam() {
        return codTratam;
    }

    public void setCodTratam(int codTratam) {
        this.codTratam = codTratam;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getDuracion_min() {
        return duracion_min;
    }

    public void setDuracion_min(int duracion_min) {
        this.duracion_min = duracion_min;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getCantidadSesiones() {
    return cantidadSesiones;
    }

    public void setCantidadSesiones(int cantidadSesiones) {
    this.cantidadSesiones = cantidadSesiones;
    }
    @Override
    public String toString() {
          return nombre + " (" + tipo + ")";
    }
    
}
