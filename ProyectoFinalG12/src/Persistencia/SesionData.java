/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import Modelo.Conexion;
import Modelo.Sesion;
import Modelo.Tratamiento;
import Modelo.Consultorio;
import Modelo.Masajista;
import Modelo.DiadeSpa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lulim
 */
public class SesionData {
     private Connection con;
    private TratamientoData tratData;
    private ConsultorioData consulData;
    private MasajistaData masajistaData;
    private DiadeSpaData diaSpaData;

    public SesionData() {
        con = Conexion.getConexion();
        tratData = new TratamientoData();
        consulData = new ConsultorioData();
        masajistaData = new MasajistaData();
        diaSpaData = new DiadeSpaData();
    }
    
    // Guardar Sesión
    public void guardarSesion(Sesion s) {
        String sql = "INSERT INTO sesion(fechaHoraInicio, fechaHoraFin, codTratam, nroConsultorio, matricula, codPack, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(s.getFechaHoraInicio()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(s.getFechaHoraFin()));
            ps.setInt(3, s.getTratamiento().getCodTratam());
            ps.setInt(4, s.getConsultorio().getNroConsultorio());
            ps.setInt(5, s.getMasajista().getMatricula());
            ps.setInt(6, s.getDiadeSpa().getCodPack());
            ps.setBoolean(7, s.isEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                s.setCodSesion(rs.getInt(1));
            }
            ps.close();
            System.out.println("Sesión guardada correctamente.");
        } catch (SQLException ex) {
            System.out.println("Error al guardar sesión: " + ex.getMessage());
        }
    }

    // Listar Sesiones
    public List<Sesion> listarSesiones() {
        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT * FROM sesion";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sesion s = new Sesion();
                s.setCodSesion(rs.getInt("codSesion"));
                s.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                s.setFechaHoraFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
                s.setEstado(rs.getBoolean("estado"));

                // Relaciones
                s.setTratamiento(tratData.buscarPorId(rs.getInt("codTratam"))); 
                s.setConsultorio(consulData.buscarConsultorioPorId(rs.getInt("nroConsultorio")));
                s.setMasajista(masajistaData.buscarPorMatricula(rs.getInt("matricula")));
                s.setDiadeSpa(diaSpaData.buscarPorId(rs.getInt("codPack")));

                sesiones.add(s);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al listar sesiones: " + ex.getMessage());
        }
        return sesiones;
    }

    // Editar Sesión
    public void editarSesion(Sesion s) {
        String sql = "UPDATE sesion SET fechaHoraInicio=?, fechaHoraFin=?, codTratam=?, nroConsultorio=?, matricula=?, codPack=?, estado=? WHERE codSesion=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(s.getFechaHoraInicio()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(s.getFechaHoraFin()));
            ps.setInt(3, s.getTratamiento().getCodTratam());
            ps.setInt(4, s.getConsultorio().getNroConsultorio());
            ps.setInt(5, s.getMasajista().getMatricula());
            ps.setInt(6, s.getDiadeSpa().getCodPack());
            ps.setBoolean(7, s.isEstado());
            ps.setInt(8, s.getCodSesion());

            int exito = ps.executeUpdate();
            ps.close();

            if (exito == 1) {
                System.out.println("Sesión modificada correctamente.");
            } else {
                System.out.println("No se encontró la sesión.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al editar sesión: " + ex.getMessage());
        }
    }

    // Deshabilitar Sesión
    public void deshabilitarSesion(int codSesion) {
        String sql = "DELETE FROM sesion WHERE codSesion = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, codSesion);
        int fila = ps.executeUpdate();

        if (fila == 1) {
            System.out.println("Sesión eliminada correctamente.");
        } else {
            System.out.println("No se encontró la sesión con ese código.");
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al eliminar sesión: " + ex.getMessage());
    }
    }

    // Habilitar Sesión
    public void habilitarSesion(int codSesion) {
        String sql = "UPDATE sesion SET estado = 1 WHERE codSesion = ? AND estado = 0";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codSesion);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                System.out.println("Sesión habilitada correctamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al habilitar sesión: " + ex.getMessage());
        }
    }

    // Buscar por ID
    public Sesion buscarPorCodSesion(int codSesion) {
        Sesion s = null;
        String sql = "SELECT * FROM sesion WHERE codSesion = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codSesion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                s = new Sesion();
                s.setCodSesion(rs.getInt("codSesion"));
                s.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                s.setFechaHoraFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
                s.setEstado(rs.getBoolean("estado"));
                
                s.setTratamiento(tratData.buscarPorId(rs.getInt("codTratam")));
                s.setConsultorio(consulData.buscarConsultorioPorId(rs.getInt("nroConsultorio")));
                s.setMasajista(masajistaData.buscarPorMatricula(rs.getInt("matricula")));
                s.setDiadeSpa(diaSpaData.buscarPorId(rs.getInt("codPack")));
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al buscar sesión: " + ex.getMessage());
        }
        return s;
    }
}
