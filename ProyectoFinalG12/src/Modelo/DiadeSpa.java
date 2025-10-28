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
public class DiadeSpa {
    private int codPack;
    private LocalDateTime fechaHora;
    private String preferencias;
    private Cliente cliente;
    private double monto;
    private boolean estado;
    private List<Sesion> sesiones;
    
    
    public DiadeSpa() {
        this.estado = true;
        this.sesiones = new ArrayList<>();
    }

    public DiadeSpa(LocalDateTime fechaHora, String preferencias, Cliente cliente, double monto, boolean estado) {
        this.fechaHora = fechaHora;
        this.preferencias = preferencias;
        this.cliente = cliente;
        this.monto = monto;
        this.estado = estado;
        this.sesiones = new ArrayList<>();
    }

    public int getCodPack() {
        return codPack;
    }

    public void setCodPack(int codPack) {
        this.codPack = codPack;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

    public void agregarSesion(Sesion sesion) {
        sesiones.add(sesion);
    }

    @Override
    public String toString() {
        return "Pack #" + codPack + " - Cliente: " + cliente.getNombreCompleto();
    }
}
