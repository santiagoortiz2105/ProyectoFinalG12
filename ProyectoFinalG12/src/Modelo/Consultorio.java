/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Lulim
 */
public class Consultorio {
    
    private int nroConsultorio; 
    private String usos; 
    private String equipamiento; 
    private boolean apto; 
    
    //Constructor vacio
     public Consultorio() {
        this.apto = true;
    }
    
     //Cosntructor 
    public Consultorio(String usos, String equipamiento, boolean apto) {
        this.usos = usos;
        this.equipamiento = equipamiento;
        this.apto = apto;
    }

    //Getters y Setters 
    public int getNroConsultorio() {
        return nroConsultorio;
    }

    public void setNroConsultorio(int nroConsultorio) {
        this.nroConsultorio = nroConsultorio;
    }

    public String getUsos() {
        return usos;
    }

    public void setUsos(String usos) {
        this.usos = usos;
    }

    public String getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }

    public boolean isApto() {
        return apto;
    }

    public void setApto(boolean apto) {
        this.apto = apto;
    }

    @Override
    public String toString() {
        return "Consultorio " + nroConsultorio + " (" + usos + ")";
    }
}
