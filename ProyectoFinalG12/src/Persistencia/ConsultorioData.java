package Persistencia;

import Modelo.Conexion;
import Modelo.Consultorio;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultorioData {

    private Connection con;

    public ConsultorioData() {
        con = Conexion.getConexion();
    }

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

    public void deshabilitarConsultorio(int id) {
        String sql = "DELETE FROM consultorio WHERE nroConsultorio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();
            if (fila == 1) {
                System.out.println("Consultorio eliminado correctamente.");
            } else {
                System.out.println("No se encontró el consultorio.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar consultorio: " + ex.getMessage());
        }
    }

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

    public List<Consultorio> getConsultoriosLibres(LocalDateTime inicio, LocalDateTime fin) {
        List<Consultorio> lista = new ArrayList<>();

        String sql = """
        SELECT *
        FROM consultorio c
        WHERE c.apto = 1
          AND c.nroConsultorio NOT IN (
                SELECT s.nroConsultorio
                FROM sesion s
                WHERE (s.fechaHoraInicio < ?)
                  AND (s.fechaHoraFin > ?)
          )
    """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(fin));
            ps.setTimestamp(2, Timestamp.valueOf(inicio));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Consultorio c = new Consultorio();
                c.setNroConsultorio(rs.getInt("nroConsultorio"));
                c.setUsos(rs.getString("usos"));
                c.setEquipamiento(rs.getString("equipamiento"));
                c.setApto(rs.getBoolean("apto"));

                lista.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener consultorios libres: " + ex.getMessage());
        }

        return lista;
    }

    public List<Consultorio> listarConsultoriosActivos() {
        List<Consultorio> consultorios = new ArrayList<>();

        String sql = "SELECT * FROM consultorio WHERE apto = 1";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consultorio c = new Consultorio();

                c.setNroConsultorio(rs.getInt("nroConsultorio"));
                c.setUsos(rs.getString("usos"));
                c.setEquipamiento(rs.getString("equipamiento"));
                c.setApto(rs.getBoolean("apto"));

                consultorios.add(c);
            }

            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al listar consultorios activos: " + ex.getMessage());
        }

        return consultorios;
    }

}
