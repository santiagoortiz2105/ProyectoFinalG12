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
import Modelo.Instalacion;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

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
            
            //aca guardamos las instalaciones asociadas
            guardarInstalacionesDeSesion(s);
        } catch (SQLException ex) {
            System.out.println("Error al guardar sesión: " + ex.getMessage());
        }
    }

    private void guardarInstalacionesDeSesion(Sesion s) {
        if (s.getInstalaciones() == null || s.getInstalaciones().isEmpty()) return;

    String sql = "INSERT INTO sesion_instalacion(codSesion, codInstal) VALUES (?, ?)";

    try (PreparedStatement ps = con.prepareStatement(sql)) {

        for (Instalacion inst : s.getInstalaciones()) {
            ps.setInt(1, s.getCodSesion());
            ps.setInt(2, inst.getCodInstal());
            ps.addBatch();
        }

        ps.executeBatch();
        System.out.println("Instalaciones guardadas para sesión " + s.getCodSesion());

    } catch (SQLException ex) {
        System.out.println("Error al guardar instalaciones: " + ex.getMessage());
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
                
                
                s.setInstalaciones(obtenerInstalacionesDeSesion(s.getCodSesion()));
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
        ps.setTimestamp(1, Timestamp.valueOf(s.getFechaHoraInicio()));
        ps.setTimestamp(2, Timestamp.valueOf(s.getFechaHoraFin()));
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
            borrarInstalacionesDeSesion(s.getCodSesion());
            guardarInstalacionesDeSesion(s);

        } else {
            System.out.println("No se encontró la sesión.");
        }

    } catch (SQLException ex) {
        System.out.println("Error al editar sesión: " + ex.getMessage());
    }
    }
    
    private void borrarInstalacionesDeSesion(int codSesion) {
    String sql = "DELETE FROM sesion_instalacion WHERE codSesion = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, codSesion);
        ps.executeUpdate();
        System.out.println("Instalaciones eliminadas para la sesión " + codSesion);
    } catch (SQLException ex) {
        System.out.println("Error al borrar instalaciones: " + ex.getMessage());
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
                
                s.setInstalaciones(obtenerInstalacionesDeSesion(codSesion));
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al buscar sesión: " + ex.getMessage());
        }
        return s;
    }
    
    public List<Instalacion> obtenerInstalacionesDeSesion(int codSesion) {
     List<Instalacion> lista = new ArrayList<>();

    String sql =
        "SELECT i.* FROM sesion_instalacion si "
        + "JOIN instalacion i ON si.codInstal = i.codInstal "
        + "WHERE si.codSesion = ?";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, codSesion);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Instalacion inst = new Instalacion();
            inst.setCodInstal(rs.getInt("codInstal"));
            inst.setNombre(rs.getString("nombre"));
            inst.setdetalledeuso(rs.getString("detalledeuso"));
            inst.setPrecio30m(rs.getDouble("precio30m"));
            inst.setEstado(rs.getBoolean("estado"));

            lista.add(inst);
        }

        rs.close();
        ps.close();

    } catch (SQLException e) {
        System.out.println("Error al obtener instalaciones: " + e.getMessage());
    }

    return lista;
}
     public boolean estaOcupadoConsultorio(int nroConsultorio, LocalDateTime inicio, LocalDateTime fin) {
      String sql = """
        SELECT COUNT(*) 
        FROM sesion
        WHERE nroConsultorio = ?
        AND estado = 1
        AND fechaHoraInicio < ?
        AND fechaHoraFin > ?
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, nroConsultorio);
        ps.setTimestamp(2, Timestamp.valueOf(fin));
        ps.setTimestamp(3, Timestamp.valueOf(inicio));

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;

    } catch (SQLException ex) {
        System.out.println("Error al verificar consultorio: " + ex.getMessage());
    }

    return false;
    
}
    public boolean estaOcupadoConsultorioExcepto(int codSesion, int nroConsultorio, LocalDateTime inicio, LocalDateTime fin) {
        String sql = """
        SELECT COUNT(*) 
        FROM sesion
        WHERE nroConsultorio = ?
        AND codSesion <> ?
        AND estado = 1
        AND fechaHoraInicio < ?
        AND fechaHoraFin > ?
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, nroConsultorio);
        ps.setInt(2, codSesion);
        ps.setTimestamp(3, Timestamp.valueOf(fin));
        ps.setTimestamp(4, Timestamp.valueOf(inicio));

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;

    } catch (SQLException ex) {
        System.out.println("Error al verificar consultorio (modificar): " + ex.getMessage());
    }

    return false;
} 
     
     public boolean estaOcupadoMasajista(int matricula, LocalDateTime inicio, LocalDateTime fin) {
        String sql = """
        SELECT COUNT(*) 
        FROM sesion
        WHERE matricula = ?
        AND estado = 1
        AND fechaHoraInicio < ?
        AND fechaHoraFin > ?
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, matricula);
        ps.setTimestamp(2, Timestamp.valueOf(fin));
        ps.setTimestamp(3, Timestamp.valueOf(inicio));

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;

    } catch (SQLException ex) {
        System.out.println("Error al verificar disponibilidad del masajista: " + ex.getMessage());
    }

    return false;
}
      public boolean estaOcupadoMasajistaExcepto(int codSesion, int matricula, LocalDateTime inicio, LocalDateTime fin) {
       String sql = """
        SELECT COUNT(*) 
        FROM sesion
        WHERE matricula = ?
        AND codSesion <> ?
        AND fechaHoraInicio < ?
        AND fechaHoraFin > ?
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, matricula);
        ps.setInt(2, codSesion);
        ps.setTimestamp(3, Timestamp.valueOf(fin));
        ps.setTimestamp(4, Timestamp.valueOf(inicio));

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;

    } catch (SQLException ex) {
        System.out.println("Error al verificar masajista (modificar): " + ex.getMessage());
    }

    return false;
}
      
      public boolean estaOcupadaInstalacion(int codInstal, LocalDateTime inicio, LocalDateTime fin) {

    String sql = """
        SELECT COUNT(*) AS total
        FROM sesion s
        JOIN sesion_instalacion si ON si.codSesion = s.codSesion
        WHERE si.codInstal = ?
          AND estado = 1
          AND s.fechaHoraInicio < ?
          AND s.fechaHoraFin > ?;
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, codInstal);
        ps.setTimestamp(2, Timestamp.valueOf(fin));
        ps.setTimestamp(3, Timestamp.valueOf(inicio));

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("total") > 0; // ocupada
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return false;
}
      public boolean estaOcupadaInstalacionExcepto(int codSesion, int codInstal, LocalDateTime inicio, LocalDateTime fin) {
        String sql = """
        SELECT COUNT(*) AS total
        FROM sesion s
        JOIN sesion_instalacion si ON si.codSesion = s.codSesion
        WHERE si.codInstal = ?
        AND s.codSesion <> ?
        AND estado = 1
        AND s.fechaHoraInicio < ?
        AND s.fechaHoraFin > ?
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, codInstal);
        ps.setInt(2, codSesion);
        ps.setTimestamp(3, Timestamp.valueOf(fin));
        ps.setTimestamp(4, Timestamp.valueOf(inicio));

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt("total") > 0;

    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return false;
    
}
      
    
      public List<Sesion> listarSesionesPorPack(int codPack) {
    List<Sesion> lista = new ArrayList<>();
    
    String sql = "SELECT * FROM sesion WHERE codPack = ? AND estado = 1";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, codPack);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Sesion s = new Sesion();
            s.setCodSesion(rs.getInt("codSesion"));
            s.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
            s.setFechaHoraFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
            s.setTratamiento(tratData.buscarPorId(rs.getInt("codTratam")));
            s.setConsultorio(consulData.buscarConsultorioPorId(rs.getInt("nroConsultorio")));
            s.setMasajista(masajistaData.buscarPorMatricula(rs.getInt("matricula")));
            s.setDiadeSpa(diaSpaData.buscarPorId(codPack));

            s.setEstado(rs.getBoolean("estado"));

            lista.add(s);
        }

        rs.close();
        ps.close();

    } catch (SQLException e) {
        System.out.println("Error al listar sesiones por pack: " + e.getMessage());
    }

    return lista;
}

      public List<Sesion> listarSesionesPorDiaSpa(int codPack) {
    List<Sesion> lista = new ArrayList<>();

    String sql = "SELECT * FROM sesion WHERE codPack = ? AND estado = 1";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, codPack);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Sesion s = new Sesion();

            s.setCodSesion(rs.getInt("codSesion"));
            s.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
            s.setFechaHoraFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
            s.setTratamiento(tratData.buscarPorId(rs.getInt("codTratam")));
            s.setConsultorio(consulData.buscarConsultorioPorId(rs.getInt("nroConsultorio")));
            s.setMasajista(masajistaData.buscarPorMatricula(rs.getInt("matricula")));
            s.setDiadeSpa(diaSpaData.buscarPorId(codPack));

            s.setEstado(rs.getBoolean("estado"));

            lista.add(s);
        }

        rs.close();
        ps.close();

    } catch (SQLException e) {
        System.out.println("Error al listar sesiones por Día de Spa: " + e.getMessage());
    }

    return lista;
}
    public double calcularCostoTotalSesion(int codSesion) {
    double total = 0.0;

    try {
        // Sumar instalaciones
        String sqlInst = "SELECT precio30m FROM instalacion "
                + "JOIN sesion_instalacion ON instalacion.codInstal = sesion_instalacion.codInstal "
                + "WHERE sesion_instalacion.codSesion = ?";

        PreparedStatement psInst = con.prepareStatement(sqlInst);
        psInst.setInt(1, codSesion);

        ResultSet rsInst = psInst.executeQuery();

        while (rsInst.next()) {
            total += rsInst.getDouble("precio30m");
        }

        rsInst.close();
        psInst.close();

        // Sumar Tratamiento (usa 'costo', NO 'precio')
        String sqlTrat = "SELECT t.costo FROM tratamiento t "
                + "JOIN sesion s ON s.codTratam = t.codTratam "
                + "WHERE s.codSesion = ?";

        PreparedStatement psTrat = con.prepareStatement(sqlTrat);
        psTrat.setInt(1, codSesion);

        ResultSet rsTrat = psTrat.executeQuery();

        if (rsTrat.next()) {
            total += rsTrat.getDouble("costo");
        }

        rsTrat.close();
        psTrat.close();

    } catch (SQLException e) {
        System.out.println("Error al calcular costo total: " + e.getMessage());
    }

    return total;
}
    
    
    //Masajistas disponibles
    public boolean masajistaDisponible(int matricula, LocalDateTime inicio, LocalDateTime fin) {
    String sql = "SELECT * FROM sesion WHERE matricula = ? AND estado = 1 AND "
            + "( (fechaHoraInicio <= ? AND fechaHoraFin > ?) "
            + "OR  (fechaHoraInicio < ? AND fechaHoraFin >= ?) "
            + "OR  (fechaHoraInicio >= ? AND fechaHoraFin <= ?) )";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, matricula);

        ps.setTimestamp(2, Timestamp.valueOf(inicio));
        ps.setTimestamp(3, Timestamp.valueOf(inicio));

        ps.setTimestamp(4, Timestamp.valueOf(fin));
        ps.setTimestamp(5, Timestamp.valueOf(fin));

        ps.setTimestamp(6, Timestamp.valueOf(inicio));
        ps.setTimestamp(7, Timestamp.valueOf(fin));

        ResultSet rs = ps.executeQuery();

        boolean libre = !rs.next();
        rs.close();
        ps.close();

        return libre;

    } catch (SQLException e) {
        System.out.println("Error verificando masajista: " + e.getMessage());
        return false;
    }
}
    //Dia de spa Ocupado
    public boolean estaOcupadoDiaSpa(int codPack, LocalDateTime inicio, LocalDateTime fin) {
    String sql = "SELECT COUNT(*) FROM sesion " +
                 "WHERE codPack = ? AND " +
                 "((fechaHoraInicio < ? AND fechaHoraFin > ?) OR " +   // se superponen
                 "(fechaHoraInicio >= ? AND fechaHoraInicio < ?))";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, codPack);
        ps.setTimestamp(2, Timestamp.valueOf(fin));
        ps.setTimestamp(3, Timestamp.valueOf(inicio));
        ps.setTimestamp(4, Timestamp.valueOf(inicio));
        ps.setTimestamp(5, Timestamp.valueOf(fin));

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;

    } catch (SQLException e) {
        System.out.println("Error validando día de spa: " + e.getMessage());
    }
    return false;
}
    
    public boolean estaOcupadoDiaSpaExcepto(int codSesion, int codPack, LocalDateTime inicio, LocalDateTime fin) {
    String sql = "SELECT COUNT(*) FROM sesion " +
                 "WHERE codPack = ? AND codSesion <> ? AND " +
                 "((fechaHoraInicio < ? AND fechaHoraFin > ?) OR " +
                 "(fechaHoraInicio >= ? AND fechaHoraInicio < ?))";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, codPack);
        ps.setInt(2, codSesion);
        ps.setTimestamp(3, Timestamp.valueOf(fin));
        ps.setTimestamp(4, Timestamp.valueOf(inicio));
        ps.setTimestamp(5, Timestamp.valueOf(inicio));
        ps.setTimestamp(6, Timestamp.valueOf(fin));

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;

    } catch (SQLException e) {
        System.out.println("Error validando día de spa (excepto): " + e.getMessage());
    }
    return false;
}
//Eliminar Sesion
public void borrarSesion(int codSesion) {
    borrarInstalacionesDeSesion(codSesion);

    String sql = "DELETE FROM sesion WHERE codSesion = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, codSesion);
        int borrado = ps.executeUpdate();

        if (borrado > 0) {
            JOptionPane.showMessageDialog(null, "Sesión eliminada correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la sesión.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar sesión: " + ex.getMessage());
    }
}



}
