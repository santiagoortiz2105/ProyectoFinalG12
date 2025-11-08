package Persistencia;

import Modelo.Cliente;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteData {
    private Connection con;

    public ClienteData() {
        con = Conexion.getConexion();
    }

    public void guardarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente(dni, nombreCompleto, telefono, edad, afecciones, estado) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombreCompleto());
            ps.setString(3, cliente.getTelefono());
            ps.setInt(4, cliente.getEdad());
            ps.setString(5, cliente.getAfecciones());
            ps.setBoolean(6, true);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cliente.setCodCli(rs.getInt(1));
            }

            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al guardar cliente: " + ex.getMessage());
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("dni"),
                        rs.getString("nombreCompleto"),
                        rs.getString("telefono"),
                        rs.getInt("edad"),
                        rs.getString("afecciones"),
                        rs.getBoolean("estado")
                );
                cliente.setCodCli(rs.getInt("codCli"));
                clientes.add(cliente);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al obtener clientes: " + ex.getMessage());
        }
        return clientes;
    }
    
    public void editarCliente(Cliente cliente) {

        String sql = "UPDATE cliente SET dni=?, nombreCompleto=?, telefono=?, edad=?, afecciones=?, estado=? WHERE codCli=?";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombreCompleto());
            ps.setString(3, cliente.getTelefono());
            ps.setInt(4, cliente.getEdad());
            ps.setString(5, cliente.getAfecciones());
            ps.setBoolean(6, cliente.isEstado());
            ps.setInt(7, cliente.getCodCli());
            int exito = ps.executeUpdate();

            if (exito == 1) {
                System.out.println("Cliente modificado exitosamente.");
                //para cuando haya vistas
                //JOptionPane.showMessageDialog(null, "Cliente modificado exitosamente.");
            } else {
                System.out.println("No se encontró el cliente.");
                //para cuando haya vistas
                //JOptionPane.showMessageDialog(null, "El cliente no existe");
            }

        } catch (SQLException ex) {
            System.out.println("Error al acceder a la tabla Cliente.");
            //para cuando haya vistas
            //JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Cliente. " + ex.getMessage());
        }

    }
    
    public void deshabilitarCliente(int id) {
         String sql = "DELETE FROM cliente WHERE codCli = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int fila = ps.executeUpdate();

        if (fila == 1) {
            System.out.println("El cliente se eliminó exitosamente.");
        } else {
            System.out.println("No se encontró el cliente con ese ID.");
        }

        ps.close();
    } catch (SQLException e) {
        System.out.println("Error al eliminar cliente: " + e.getMessage());
    }
    }
    
    public void habilitarCliente(int id) {

        try {
            String sql = "UPDATE cliente SET estado = 1 WHERE codCli = ? AND estado = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, false);
            int fila = ps.executeUpdate();
            ps.close();
            if (fila == 1) {
                System.out.println("El cliente se habilitó exitosamente.");
                //para cuando haya vistas
                //JOptionPane.showMessageDialog(null, "El cliente se habilitó exitosamente.");
            }

        } catch (SQLException e) {
            System.out.println("Error al acceder a la tabla Cliente.");
            //para cuando haya vistas
            //JOptionPane.showMessageDialog(null, " Error al acceder a la tabla Cliente.");
        }
    }
    
    public Cliente clienteXDni(String dni) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE dni=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setCodCli(rs.getInt("codCli"));
                cliente.setDni(rs.getString("dni"));
                cliente.setNombreCompleto(rs.getString("nombreCompleto"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEdad(rs.getInt("edad"));
                cliente.setAfecciones(rs.getString("afecciones"));
                cliente.setEstado(rs.getBoolean("estado"));
            } else {
                System.out.println("No se encontró el cliente.");
                //para cuando haya vistas
                //JOptionPane.showMessageDialog(null, "No se encontró el cliente.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al acceder a la tabla Cliente");
            //para cuando haya vistas
            //JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Cliente. " + ex.getMessage());
        }

        return cliente;
    }
    
    public Cliente buscarClientePorid(int codCli) {
    Cliente cliente = null;
    String sql = "SELECT * FROM cliente WHERE codCli=?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, codCli);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            cliente = new Cliente();
            cliente.setCodCli(rs.getInt("codCli"));
            cliente.setDni(rs.getString("dni"));
            cliente.setNombreCompleto(rs.getString("nombreCompleto"));
            cliente.setTelefono(rs.getString("telefono"));
            cliente.setEdad(rs.getInt("edad"));
            cliente.setAfecciones(rs.getString("afecciones"));
            cliente.setEstado(rs.getBoolean("estado"));
        } else {
            System.out.println("No se encontró el cliente con codCli: " + codCli);
        }
        ps.close();
    } catch (SQLException ex) {
        System.out.println("Error al acceder a la tabla Cliente: " + ex.getMessage());
    }
    return cliente;
}
      
}
