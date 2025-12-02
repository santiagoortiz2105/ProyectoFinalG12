package Modelo;

public class Instalacion {

    private int codInstal;
    private String nombre;
    private String detalledeuso;
    private double precio30m;
    private boolean estado;
    private int cantidadReservas;

    //Constructor vacio 
    public Instalacion() {
        this.estado = true;
    }

    //Constructor con id
    public Instalacion(int codInstal) {
        this.codInstal = codInstal;
    }

    //Constructor
    public Instalacion(String nombre, String detalledeuso, double precio30m, boolean estado) {
        this.nombre = nombre;
        this.detalledeuso = detalledeuso;
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

    public String getdetalledeuso() {
        return detalledeuso;
    }

    public void setdetalledeuso(String detalledeuso) {
        this.detalledeuso = detalledeuso;
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

    public int getCantidadReservas() {
        return cantidadReservas;
    }

    public void setCantidadReservas(int cantidadReservas) {
        this.cantidadReservas = cantidadReservas;
    }

    @Override
    public String toString() {
        return nombre + " ($" + precio30m + ")";
    }

}
