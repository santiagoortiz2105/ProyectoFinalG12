/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import Modelo.Cliente;
import Modelo.Conexion;
import Modelo.DiadeSpa;
import Modelo.Sesion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Lulim
 */
public class DiadeSpaData {
    private Connection con;
    private ClienteData clienteData;
    private SesionData SesionData;

    public DiadeSpaData() {
        con = Conexion.getConexion();
        clienteData = new ClienteData();
    }
    
    //Guardar DiadeSpa
    public void guardarDiaDeSpa(DiadeSpa d) {
        String sql = "INSERT INTO dia_de_spa (fechaHoraInicio, fechaHoraFin, preferencias, codCli, monto, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(d.getFechaHoraInicio()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(d.getFechaHoraFin()));
            ps.setString(3, d.getPreferencias());
            ps.setInt(4, d.getCliente().getCodCli());
            ps.setDouble(5, d.getMonto());
            ps.setBoolean(6, d.isEstado());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                d.setCodPack(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Día de Spa guardado correctamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar Día de Spa: " + ex.getMessage());
        }
    }
    //Listar DiadeSpa
     public List<DiadeSpa> listarDiasDeSpa() {
        List<DiadeSpa> lista = new ArrayList<>();
        String sql = "SELECT * FROM dia_de_spa";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DiadeSpa dia = new DiadeSpa();
                dia.setCodPack(rs.getInt("codPack"));
                dia.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                dia.setFechaHoraFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
                dia.setPreferencias(rs.getString("preferencias"));
                dia.setMonto(rs.getDouble("monto"));
                dia.setEstado(rs.getBoolean("estado"));
                dia.setCliente(clienteData.buscarClientePorid(rs.getInt("codCli")));
                lista.add(dia);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar Días de Spa: " + ex.getMessage());
        }
        return lista;
    }
    //Editar DiadeSpa
    public void editarDiaDeSpa(DiadeSpa d) {
        String sql = "UPDATE dia_de_spa SET fechaHoraInicio=?, fechaHoraFin=?, preferencias=?, codCli=?, monto=?, estado=? WHERE codPack=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(d.getFechaHoraInicio()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(d.getFechaHoraFin()));
            ps.setString(3, d.getPreferencias());
            ps.setInt(4, d.getCliente().getCodCli());
            ps.setDouble(5, d.getMonto());
            ps.setBoolean(6, d.isEstado());
            ps.setInt(7, d.getCodPack());

            int exito = ps.executeUpdate();
            ps.close();

            if (exito == 1) {
                System.out.println("Día de Spa modificado correctamente.");
            } else {
                System.out.println("No se encontró el Día de Spa.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al editar Día de Spa: " + ex.getMessage());
        }
    }
    //Deshabilitar DiadeSpa
    public void deshabilitarDiaDeSpa(int codPack) {
          String sql = "DELETE FROM dia_de_spa WHERE codPack = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, codPack);
        int fila = ps.executeUpdate();

        if (fila == 1) {
            JOptionPane.showMessageDialog(null, "Día de Spa eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el registro para eliminar.");
        }
        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar Día de Spa: " + ex.getMessage());
    }
    }
    //Habilitar DiadeSpa
    public void habilitarDiaDeSpa(int codPack) {
        String sql = "UPDATE dia_de_spa SET estado = 1 WHERE codPack = ? AND estado = 0";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codPack);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                System.out.println("Día de Spa habilitado correctamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al habilitar Día de Spa: " + ex.getMessage());
        }
    }
    //Buscar DiadeSpa por id
    public DiadeSpa buscarPorId(int codPack) {
        DiadeSpa d = null;
        String sql = "SELECT * FROM dia_de_spa WHERE codPack = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codPack);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                d = new DiadeSpa();
                d.setCodPack(rs.getInt("codPack"));
                d.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                d.setFechaHoraFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
                d.setPreferencias(rs.getString("preferencias"));
                d.setMonto(rs.getDouble("monto"));
                d.setEstado(rs.getBoolean("estado"));
                Cliente c = clienteData.buscarClientePorid(rs.getInt("codCli"));
                d.setCliente(c);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al buscar Día de Spa: " + ex.getMessage());
        }
        return d;
    }
}
