/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import Modelo.Conexion;
import Modelo.Masajista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lulim
 */

public class MasajistaData {
     
    private Connection con; 

    public MasajistaData() {
        con = Conexion.getConexion(); 
    }
    
    
  //Guardar masajista
 public void guardarMasajista(Masajista m) {
        String sql = "INSERT INTO masajista(nombre, apellido, telefono, especialidad, estado) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getApellido());
            ps.setString(3, m.getTelefono());
            ps.setString(4, m.getEspecialidad());
            ps.setBoolean(5, m.isEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                m.setMatricula(rs.getInt(1));
            }
            ps.close();
            System.out.println("Masajista guardado correctamente.");
        } catch (SQLException ex) {
            System.out.println("Error al guardar masajista: " + ex.getMessage());
        }
    }
 
   //Listar a todos los masajistas
   public List<Masajista> listarMasajistas() {
    List<Masajista> masajistas = new ArrayList<>();
    String sql = "SELECT * FROM masajista";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Masajista m = new Masajista();
            m.setMatricula(rs.getInt("matricula"));
            m.setNombre(rs.getString("nombre"));
            m.setApellido(rs.getString("apellido"));
            m.setTelefono(rs.getString("telefono"));
            m.setEspecialidad(rs.getString("especialidad"));
            m.setEstado(rs.getBoolean("estado"));
            masajistas.add(m);
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al listar masajistas: " + ex.getMessage());
    }
    return masajistas;
}
   //Editar masajista
   public void editarMasajista(Masajista m){
    String sql = "UPDATE masajista SET nombre=?, apellido=?, telefono=?, especialidad=?, estado=? WHERE matricula=?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, m.getNombre());
        ps.setString(2, m.getApellido());
        ps.setString(3, m.getTelefono());
        ps.setString(4, m.getEspecialidad());
        ps.setBoolean(5, m.isEstado());
        ps.setInt(6, m.getMatricula());
        int exito = ps.executeUpdate();

        if (exito == 1) {
            System.out.println("Masajista modificado exitosamente.");
        } else {
            System.out.println("No se encontró el masajista.");
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al modificar masajista: " + ex.getMessage());
    }
}   

   //Deshabilitar masajista
    public void deshabilitarMasajista(int matricula){
         String sql = "UPDATE masajista SET estado=0 WHERE matricula=? AND estado=1"; 
         try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, matricula);
        int fila = ps.executeUpdate();
        ps.close();
        if (fila == 1) {
            System.out.println("El masajista se deshabilitó exitosamente.");
        }
    } catch (SQLException ex) {
        System.out.println("Error al deshabilitar masajista: " + ex.getMessage());
    }
    }
   
    //Habilitar masajista 
    public void habilitarMasajista(int matricula){
        String sql ="UPDATE masajista SET estado=1 WHERE matricula=? AND estado=0"; 
        try{
          PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, matricula);
        int fila = ps.executeUpdate();
        ps.close();
        if (fila == 1) {
            System.out.println("El masajista se habilitó exitosamente.");
        }  
        }catch (SQLException ex){
            System.out.println("Erro al habilitar masajista: " + ex.getMessage());
        }
    }
    
    //Buscar masajista por matricula 
    public Masajista buscarPorMatricula(int matricula) {
        Masajista m = null;
    String sql = "SELECT * FROM masajista WHERE matricula=?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, matricula);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            m = new Masajista();
            m.setMatricula(rs.getInt("matricula"));
            m.setNombre(rs.getString("nombre"));
            m.setApellido(rs.getString("apellido"));
            m.setTelefono(rs.getString("telefono"));
            m.setEspecialidad(rs.getString("especialidad"));
            m.setEstado(rs.getBoolean("estado"));
        } else {
            System.out.println("No se encontró el masajista.");
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al buscar masajista: " + ex.getMessage());
    }
    return m;
    }
    

   }
   
 
 
 
 


  

