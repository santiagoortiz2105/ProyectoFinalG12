/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import Modelo.Conexion;
import Modelo.Instalacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Lulim
 */
public class InstalacionData {
    private Connection con;

    public InstalacionData() {
        con = Conexion.getConexion();
    }
    
    
    //Guardar Instalacion
    public void guardarInstalacion(Instalacion i) {
        String sql = "INSERT INTO instalacion(nombre, detalledeuso, precio30m, estado) VALUES (?, ?, ?, ?)";
    try {
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, i.getNombre());
        ps.setString(2, i.getdetalledeuso());
        ps.setDouble(3, i.getPrecio30m());
        ps.setBoolean(4, i.isEstado());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            i.setCodInstal(rs.getInt(1));
        }

        ps.close();
        System.out.println("Instalación guardada correctamente en la base de datos.");

    } catch (SQLException ex) {
        System.out.println("Error al guardar instalación: " + ex.getMessage());
        JOptionPane.showMessageDialog(null, "Error al guardar instalación: " + ex.getMessage());
    }
    }

    
    //Listar Instalacion 
    public List<Instalacion> listarInstalaciones() {
        List<Instalacion> instalaciones = new ArrayList<>();
        String sql = "SELECT * FROM instalacion";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Instalacion i = new Instalacion(
                        rs.getString("nombre"),
                        rs.getString("detalledeuso"),
                        rs.getDouble("precio30m"),
                        rs.getBoolean("estado")
                );
                i.setCodInstal(rs.getInt("codInstal"));
                instalaciones.add(i);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al listar instalaciones: " + ex.getMessage());
        }
        return instalaciones;
    }
    //Editar Instalacion 
    public void editarInstalacion(Instalacion i) {
        String sql = "UPDATE instalacion SET nombre=?, detalledeuso=?, precio30m=?, estado=? WHERE codInstal=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getdetalledeuso());
            ps.setDouble(3, i.getPrecio30m());
            ps.setBoolean(4, i.isEstado());
            ps.setInt(5, i.getCodInstal());

            int exito = ps.executeUpdate();
            ps.close();

            if (exito == 1) {
                System.out.println("Instalación modificada correctamente.");
            } else {
                System.out.println("No se encontró la instalación.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al editar instalación: " + ex.getMessage());
        }
    }
    //Deshabilitar Instalacion 
    public void deshabilitarInstalacion(int id) {
        String sql = "DELETE FROM instalacion WHERE codInstal = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int fila = ps.executeUpdate();

        if (fila == 1) {
            System.out.println("Instalación eliminada correctamente.");
        } else {
            System.out.println("No se encontró una instalación con ese código.");
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al eliminar instalación: " + ex.getMessage());
    }
    }

    //Habilitar Instalacion 
     public void habilitarInstalacion(int id) {
        String sql = "UPDATE instalacion SET estado = 1 WHERE codInstal = ? AND estado = 0";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                System.out.println("Instalación habilitada correctamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al habilitar instalación: " + ex.getMessage());
        }
    }
    //Buscar Instalacion por id
     public Instalacion buscarPorNombre(String nombre) {
          Instalacion i = null;
    String sql = "SELECT * FROM instalacion WHERE nombre = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            i = new Instalacion();
            i.setCodInstal(rs.getInt("codInstal"));
            i.setNombre(rs.getString("nombre"));
            i.setdetalledeuso(rs.getString("detalledeuso")); 
            i.setPrecio30m(rs.getDouble("precio30m"));
            i.setEstado(rs.getBoolean("estado"));
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al buscar instalación: " + ex.getMessage());
    }
    return i;
    }
     
     //Buscar instalacion por codigo 
     public Instalacion buscarPorCodigo(int codInstal) {
    Instalacion i = null;
    String sql = "SELECT * FROM instalacion WHERE codInstal = ?";
    
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, codInstal);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            i = new Instalacion();
            i.setCodInstal(rs.getInt("codInstal"));
            i.setNombre(rs.getString("nombre"));
            i.setdetalledeuso(rs.getString("detalledeuso"));
            i.setPrecio30m(rs.getDouble("precio30m"));
            i.setEstado(rs.getBoolean("estado"));
        }
        rs.close();
        ps.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al buscar instalación por código: " + e.getMessage());
    }
    return i;
}
}
