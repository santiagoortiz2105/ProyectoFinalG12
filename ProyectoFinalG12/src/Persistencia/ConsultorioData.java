/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import Modelo.Conexion;
import Modelo.Consultorio;
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
public class ConsultorioData {
    private Connection con;

    public ConsultorioData() {
        con = Conexion.getConexion();
    }
    
     // Guardar consultorio
    public void guardarConsultorio(Consultorio c) {
        String sql = "INSERT INTO consultorio(usos, equipamiento, apto) VALUES (?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getUsos());
            ps.setString(2, c.getEquipamiento());
            ps.setBoolean(3, c.isApto());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setNroConsultorio(rs.getInt(1));
            }
            ps.close();
            System.out.println("Consultorio guardado correctamente.");
        } catch (SQLException ex) {
            System.out.println("Error al guardar consultorio: " + ex.getMessage());
        }
    }

    // Listar consultorios
    public List<Consultorio> listarConsultorios() {
        List<Consultorio> consultorios = new ArrayList<>();
        String sql = "SELECT * FROM consultorio";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consultorio c = new Consultorio(
                        rs.getString("usos"),
                        rs.getString("equipamiento"),
                        rs.getBoolean("apto")
                );
                c.setNroConsultorio(rs.getInt("nroConsultorio"));
                consultorios.add(c);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al obtener consultorios: " + ex.getMessage());
        }
        return consultorios;
    }

    // Editar consultorio
    public void editarConsultorio(Consultorio c) {
        String sql = "UPDATE consultorio SET usos=?, equipamiento=?, apto=? WHERE nroConsultorio=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getUsos());
            ps.setString(2, c.getEquipamiento());
            ps.setBoolean(3, c.isApto());
            ps.setInt(4, c.getNroConsultorio());
            int exito = ps.executeUpdate();

            if (exito == 1) {
                System.out.println("Consultorio modificado exitosamente.");
            } else {
                System.out.println("No se encontró el consultorio.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al editar consultorio: " + ex.getMessage());
        }
    }

    // Deshabilitar consultorio
    public void deshabilitarConsultorio(int id) {
        String sql = "UPDATE consultorio SET apto = 0 WHERE nroConsultorio = ? AND apto = 1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                System.out.println("Consultorio deshabilitado correctamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al deshabilitar consultorio: " + ex.getMessage());
        }
    }

    // Habilitar consultorio
    public void habilitarConsultorio(int id) {
        String sql = "UPDATE consultorio SET apto = 1 WHERE nroConsultorio = ? AND apto = 0";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                System.out.println("Consultorio habilitado correctamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al habilitar consultorio: " + ex.getMessage());
        }
    }

    // Buscar consultorio por ID
    public Consultorio buscarConsultorioPorId(int id) {
        Consultorio c = null;
        String sql = "SELECT * FROM consultorio WHERE nroConsultorio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Consultorio();
                c.setNroConsultorio(rs.getInt("nroConsultorio"));
                c.setUsos(rs.getString("usos"));
                c.setEquipamiento(rs.getString("equipamiento"));
                c.setApto(rs.getBoolean("apto"));
            } else {
                System.out.println("No se encontró el consultorio.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al buscar consultorio: " + ex.getMessage());
        }
        return c;
    }
}
