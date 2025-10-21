/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import Modelo.Conexion;
import Modelo.Entidad;
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
public class EntidadData {
     private Connection con = null;

    public EntidadData() {
        con = Conexion.getConexion();
    }

    // 🔹 INSERTAR
    public void guardarEntidad(Entidad e) {
        String sql = "INSERT INTO entidad (nombre, descripcion, activo) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDescripcion());
            ps.setBoolean(3, e.isActivo());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                e.setId(rs.getInt(1));
                System.out.println("Entidad guardada con ID: " + e.getId());
            }

            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al guardar entidad: " + ex.getMessage());
        }
    }

    // 🔹 LISTAR TODAS
    public List<Entidad> listarEntidades() {
        List<Entidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM entidad";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Entidad e = new Entidad();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setDescripcion(rs.getString("descripcion"));
                e.setActivo(rs.getBoolean("activo"));
                lista.add(e);
            }

            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al listar entidades: " + ex.getMessage());
        }

        return lista;
    }

    // 🔹 ACTUALIZAR
    public void actualizarEntidad(Entidad e) {
        String sql = "UPDATE entidad SET nombre=?, descripcion=?, activo=? WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDescripcion());
            ps.setBoolean(3, e.isActivo());
            ps.setInt(4, e.getId());

            int modificado = ps.executeUpdate();
            if (modificado > 0)
                System.out.println("Entidad actualizada correctamente.");
            else
                System.out.println("No se encontró la entidad con ID " + e.getId());

            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar entidad: " + ex.getMessage());
        }
    }

    // 🔹 ELIMINAR (lógico)
    public void eliminarEntidad(int id) {
        String sql = "UPDATE entidad SET activo=0 WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int borrado = ps.executeUpdate();

            if (borrado > 0)
                System.out.println("Entidad marcada como inactiva.");
            else
                System.out.println("No se encontró la entidad con ID " + id);

            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar entidad: " + ex.getMessage());
        }
    }
}
