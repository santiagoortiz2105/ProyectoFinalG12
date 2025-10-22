package Modelo;

public class Cliente {
    private int codCli;             //Int AutoIncremental Clave Primaria
    private String dni;              // Varchar Unique
    private String nombreCompleto;   // Varchar
    private String telefono;         // Varchar
    private int edad;                // Int
    private String afecciones;       // Text
    private boolean estado;          // Boolean 
    
    //Constructor vacio 
    public Cliente() {
    }
 
    //Cosntructor
    public Cliente(String dni, String nombreCompleto, String telefono, int edad, String afecciones, boolean estado) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.edad = edad;
        this.afecciones = afecciones;
        this.estado = estado;
    }

    //Getters y Setters 
    public int getCodCli() {
        return codCli;
    }

    public void setCodCli(int codCli) {
        this.codCli = codCli;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getAfecciones() {
        return afecciones;
    }

    public void setAfecciones(String afecciones) {
        this.afecciones = afecciones;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
    return codCli + " - " + nombreCompleto + " (" + dni + ")";
}
    
}
