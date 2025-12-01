/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lulim
 */
public class Sesion {
    private int codSesion;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Tratamiento tratamiento;
    private Consultorio consultorio;
    private Masajista masajista;
    private DiadeSpa diadeSpa;
    private boolean estado;
    private List<Instalacion> instalaciones; 
    private double montoTotal;
    private double montoInstalaciones;
    
    //Constructor vacio 
      public Sesion() {
         this.estado = true;
         this.instalaciones = new ArrayList<>();
    }
      
    //Constructor
      public Sesion(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Tratamiento tratamiento, Consultorio consultorio, Masajista masajista, DiadeSpa diadeSpa, boolean estado) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.tratamiento = tratamiento;
        this.consultorio = consultorio;
        this.masajista = masajista;
        this.diadeSpa = diadeSpa;
        this.estado = estado;
        this.instalaciones = new ArrayList<>();
    
    }
     
    //Getters y Setters 

    public int getCodSesion() {
        return codSesion;
    }

    public void setCodSesion(int codSesion) {
        this.codSesion = codSesion;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public Masajista getMasajista() {
        return masajista;
    }

    public void setMasajista(Masajista masajista) {
        this.masajista = masajista;
    }

    public DiadeSpa getDiadeSpa() {
        return diadeSpa;
    }

    public void setDiadeSpa(DiadeSpa diadeSpa) {
        this.diadeSpa = diadeSpa;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Instalacion> getInstalaciones() {
        return instalaciones;
    }

    public void setInstalaciones(List<Instalacion> instalaciones) {
        this.instalaciones = instalaciones;
    }
    
    public double getMontoInstalaciones() { return montoInstalaciones; }
    public void setMontoInstalaciones(double m) { this.montoInstalaciones = m; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double monto) { this.montoTotal = monto; }
    
     @Override
    public String toString() {
        return "Sesión #" + codSesion + " - " + tratamiento.getNombre() + " (" + fechaHoraInicio + ")";
    }
    
}
