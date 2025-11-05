/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import Modelo.Conexion;
import Modelo.Tratamiento;
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
public class TratamientoData {
     private Connection con;

    public TratamientoData() {
        con = Conexion.getConexion();
    }
   
    //Guardar tratamiento 
    public void guardarTratamiento(Tratamiento t) {
        String sql = "INSERT INTO tratamiento(nombre, tipo, detalle, duracion_min, costo, activo) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getTipo());
            ps.setString(3, t.getDetalle());
            ps.setInt(4, t.getDuracion_min());
            ps.setDouble(5, t.getCosto());
            ps.setBoolean(6, t.isActivo());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                t.setCodTratam(rs.getInt(1));
            }
            ps.close();
            System.out.println("Tratamiento guardado correctamente.");
        } catch (SQLException ex) {
            System.out.println("Error al guardar tratamiento: " + ex.getMessage());
        }
    }
    //Listar tratamiento
    public List<Tratamiento> listarTratamientos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
    String sql = "SELECT * FROM tratamiento WHERE activo = 1"; 

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Tratamiento t = new Tratamiento(
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getString("detalle"),
                    rs.getInt("duracion_min"),
                    rs.getDouble("costo"),
                    rs.getBoolean("activo")
            );
            t.setCodTratam(rs.getInt("codTratam"));
            tratamientos.add(t);
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al listar tratamientos: " + ex.getMessage());
    }
    return tratamientos;
    }
    //Editar tratamiento
    public void editarTratamiento(Tratamiento t) {
        String sql = "UPDATE tratamiento SET nombre=?, tipo=?, detalle=?, duracion_min=?, costo=?, activo=? WHERE codTratam=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getTipo());
            ps.setString(3, t.getDetalle());
            ps.setInt(4, t.getDuracion_min());
            ps.setDouble(5, t.getCosto());
            ps.setBoolean(6, t.isActivo());
            ps.setInt(7, t.getCodTratam());

            int exito = ps.executeUpdate();
            ps.close();

            if (exito == 1) {
                System.out.println("Tratamiento modificado correctamente.");
            } else {
                System.out.println("No se encontró el tratamiento.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al editar tratamiento: " + ex.getMessage());
        }
    }

    //Deshabilitar tratamiento
    public void deshabilitarTratamiento(int id) {
        String sql = "DELETE FROM tratamiento WHERE codTratam = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int fila = ps.executeUpdate();

        if (fila == 1) {
            System.out.println("Tratamiento eliminado correctamente.");
        } else {
            System.out.println("No se encontró el tratamiento con ese ID.");
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al eliminar tratamiento: " + ex.getMessage());
    }
    }
    //Habilitar tratamiento 
    public void habilitarTratamiento(int id) {
        String sql = "UPDATE tratamiento SET activo = 1 WHERE codTratam = ? AND activo = 0";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                System.out.println("Tratamiento habilitado correctamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al habilitar tratamiento: " + ex.getMessage());
        }
    }

    //Buscar tratamiento por id 
      public Tratamiento buscarPorId(int id) {
    Tratamiento t = null;
    String sql = "SELECT * FROM tratamiento WHERE codTratam = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            t = new Tratamiento();
            t.setCodTratam(rs.getInt("codTratam"));
            t.setNombre(rs.getString("nombre"));
            t.setTipo(rs.getString("tipo"));
            t.setDetalle(rs.getString("detalle"));
            t.setDuracion_min(rs.getInt("duracion_min"));
            t.setCosto(rs.getDouble("costo"));
            t.setActivo(rs.getBoolean("activo"));
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al buscar tratamiento por ID: " + ex.getMessage());
    }
    return t;
}
    
   
}
