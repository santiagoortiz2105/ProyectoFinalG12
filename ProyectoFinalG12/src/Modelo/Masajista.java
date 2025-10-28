/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Lulim
 */
public class Masajista {
    
    private int matricula;    //Autoincremental
    private String nombre;    //varchar
    private String apellido;  //varchar
    private String telefono;  //varchar
    private String especialidad; //Enum
    private boolean estado;      //tinyint
    
    //Constructor vacio

    public Masajista() {
        this.estado = true;
    }

    //Constructor
    public Masajista(String nombre, String apellido, String telefono, String especialidad, boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.estado = estado;
    }
    
   //Getters y Setters 

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + especialidad + ")";
    }
}
