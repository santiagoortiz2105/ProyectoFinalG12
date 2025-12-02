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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.JOptionPane;

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

    public void editarMasajista(Masajista m) {
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
    public void deshabilitarMasajista(int matricula) {
        String sql = "DELETE FROM masajista WHERE matricula = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, matricula);
            int fila = ps.executeUpdate();
            ps.close();
            if (fila == 1) {
                System.out.println("El masajista fue eliminado completamente.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al eliminar masajista: " + ex.getMessage());
        }
    }

    //Habilitar masajista 
    public void habilitarMasajista(int matricula) {
        String sql = "UPDATE masajista SET estado=1 WHERE matricula=? AND estado=0";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, matricula);
            int fila = ps.executeUpdate();
            ps.close();
            if (fila == 1) {
                System.out.println("El masajista se habilitó exitosamente.");
            }
        } catch (SQLException ex) {
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

    public List<Masajista> listarPorEspecialidad(String especialidad) {
        List<Masajista> lista = new ArrayList<>();
        String sql = "SELECT * FROM masajista WHERE especialidad = ? AND estado = 1";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, especialidad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Masajista m = new Masajista();
                m.setMatricula(rs.getInt("matricula"));
                m.setNombre(rs.getString("nombre"));
                m.setApellido(rs.getString("apellido"));
                m.setTelefono(rs.getString("telefono"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setEstado(rs.getBoolean("estado"));

                lista.add(m);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al buscar por especialidad: " + ex.getMessage());
        }

        return lista;
    }

    public List<Masajista> obtenerMasajistasLibres(LocalDateTime fechaHoraBuscada) {

        LocalDate fecha = fechaHoraBuscada.toLocalDate();
        LocalTime hora = fechaHoraBuscada.toLocalTime();

        // ✔ Antes de 15:00 → TODOS están libres
        if (hora.isBefore(LocalTime.of(15, 0))) {
            return listarMasajistasQueTrabajanEseDia(fecha);
        }

        // ✔ Después de 17:00 → TODOS están libres
        if (hora.isAfter(LocalTime.of(17, 0))) {
            return listarMasajistasQueTrabajanEseDia(fecha);
        }

        // ✔ Entre 15 y 17 → filtrar
        List<Masajista> libres = new ArrayList<>();

        String sql = "SELECT m.* FROM masajista m "
                + "WHERE m.estado = 1 "
                + "AND m.matricula IN ( "
                + "     SELECT s.matricula FROM sesion s "
                + "     WHERE DATE(s.fechaHoraInicio) = ? "
                + ") "
                + "AND m.matricula NOT IN ( "
                + "     SELECT s.matricula FROM sesion s "
                + "     WHERE DATE(s.fechaHoraInicio) = ? "
                + "     AND TIME(s.fechaHoraInicio) >= '15:00:00' "
                + "     AND TIME(s.fechaHoraInicio) <  '17:00:00' "
                + ")";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(fecha));
            ps.setDate(2, java.sql.Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Masajista m = new Masajista();
                m.setMatricula(rs.getInt("matricula"));
                m.setNombre(rs.getString("nombre"));
                m.setApellido(rs.getString("apellido"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setEstado(rs.getBoolean("estado"));
                libres.add(m);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al buscar masajistas libres: " + ex.getMessage());
        }

        return libres;

    }

    public List<Masajista> listarMasajistasActivos() {
        List<Masajista> lista = new ArrayList<>();

        String sql = "SELECT * FROM masajista WHERE estado = 1";

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

                lista.add(m);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al listar masajistas activos: " + ex.getMessage());
        }

        return lista;
    }

    public List<Masajista> listarMasajistasQueTrabajanEseDia(LocalDate fecha) {

        List<Masajista> masajistas = new ArrayList<>();

        String sql
                = "SELECT DISTINCT m.* "
                + "FROM masajista m "
                + "JOIN sesion s ON m.matricula = s.matricula "
                + "WHERE m.estado = 1 "
                + "AND DATE(s.fechaHoraInicio) = ?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(fecha));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Masajista m = new Masajista();
                m.setMatricula(rs.getInt("matricula"));
                m.setNombre(rs.getString("nombre"));
                m.setApellido(rs.getString("apellido"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setEstado(rs.getBoolean("estado"));
                masajistas.add(m);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al listar masajistas que trabajan ese día: " + ex.getMessage());
        }

        return masajistas;
    }

    public List<Masajista> obtenerMasajistasLibresFranja(LocalDateTime inicio, LocalDateTime fin) {

        List<Masajista> libres = new ArrayList<>();

        String sql = """
        SELECT * FROM masajista 
        WHERE estado = 1
        AND matricula NOT IN (
            SELECT matricula FROM sesion
            WHERE (fechaHoraInicio < ? AND fechaHoraFin > ?)
        )
    """;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(fin));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(inicio));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Masajista m = new Masajista();
                m.setMatricula(rs.getInt("matricula"));
                m.setNombre(rs.getString("nombre"));
                m.setApellido(rs.getString("apellido"));
                m.setTelefono(rs.getString("telefono"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setEstado(rs.getBoolean("estado"));
                libres.add(m);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al obtener masajistas libres por franja: " + ex.getMessage());
        }

        return libres;
    }

    public void eliminarMasajista(int matricula) {
        // 1) Verificar si tiene sesiones asociadas
        String verificarSql = "SELECT COUNT(*) FROM sesion WHERE matricula = ?";

        try (PreparedStatement psVerificar = con.prepareStatement(verificarSql)) {
            psVerificar.setInt(1, matricula);
            ResultSet rs = psVerificar.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null,
                        "No se puede eliminar el masajista porque tiene sesiones asociadas.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al verificar sesiones del masajista: " + ex.getMessage());
            return;
        }

        // 2) Eliminar si no tiene dependencias
        String sql = "DELETE FROM masajista WHERE matricula = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, matricula);
            int fila = ps.executeUpdate();

            if (fila > 0) {
                JOptionPane.showMessageDialog(null,
                        "Masajista eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se encontró el masajista.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al eliminar masajista: " + ex.getMessage());
        }
    }

}
